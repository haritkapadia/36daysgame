import java.awt.Point;
import java.nio.*;
import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.geometry.*;

public class Chunk {
	public static final int CHUNK_SIDE_LENGTH = 32;
	Block[][][] blocks = new Block[CHUNK_SIDE_LENGTH][CHUNK_SIDE_LENGTH][2];
	Point location;
	WritableImage chunkImage;

	Chunk(int x, int y) {
		location = new Point(x, y);
	}

	Chunk(String test) {
		location = new Point(0, 0);
		blocks[0][0][0] = new BlockGround();
		blocks[2][3][0] = new BlockGround();
		blocks[4][5][0] = new BlockGround();
	}

	public Block[][][] getBlocks() {
		return blocks;
	}

	public void generateChunk() {

	}

	// 90% sure this works
	public Image getChunkImage() {
		if(chunkImage == null) {
			chunkImage = new WritableImage(32 * CHUNK_SIDE_LENGTH, 32 * CHUNK_SIDE_LENGTH);
			for(int i = 0; i < blocks.length; i++) {
				for(int j = 0; j < blocks[i].length; j++) {
					int k = blocks[i][j].length - 1;
					while(k > 0 && (blocks[i][j][k] == null || blocks[i][j][k].isTransparent()))
						k -= 1;
					for(; k < blocks[i][j].length; k++) {
						if(blocks[i][j][k] != null) {
							PixelReader src = blocks[i][j][k].getImage().getPixelReader();
							chunkImage.getPixelWriter().setPixels(i * 32, (CHUNK_SIDE_LENGTH - j - 1) * 32, 32, 32, src, 0, 0);
						}
					}
				}
			}
		}
		return chunkImage;
	}

	// This works
	public static Point globalToChunkPoint(Point2D p) {
		return new Point((int)Math.floor(p.getX()/CHUNK_SIDE_LENGTH), (int)Math.floor(p.getY()/CHUNK_SIDE_LENGTH));
	}

	public static Point2D chunkToGlobalPoint(Point p) {
		return new Point2D(CHUNK_SIDE_LENGTH * p.getX(), CHUNK_SIDE_LENGTH * p.getY());
	}
}
