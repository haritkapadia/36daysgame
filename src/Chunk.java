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

	public Image getChunkImage() {
		WritableImage outImage = new WritableImage(32 * CHUNK_SIDE_LENGTH, 32 * CHUNK_SIDE_LENGTH);
		PixelWriter out = outImage.getPixelWriter();
		for(int i = 0; i < blocks.length; i++) {
			for(int j = 0; j < blocks[i].length; j++) {
				for(int k = 0; k < blocks[i][j].length; k++) {
					Image srcImage = blocks[i][j][k].getImage();
					PixelReader src = srcImage.getPixelReader();
					if(blocks[i][j][k] != null) {
						out.setPixels(i * 32, (32 * CHUNK_SIDE_LENGTH - j) * 32, 32, 32, src, 0, 1);
					}
				}
			}
		}

		return outImage;
	}

	public static Point globalToChunkPoint(Point2D p) {
		return new Point((int)Math.floor(p.getX()), (int)Math.floor(p.getY()));
	}

	public static Point2D chunkToGlobalPoint(Point p) {
		return new Point2D(CHUNK_SIDE_LENGTH * p.getX(), CHUNK_SIDE_LENGTH * p.getY());
	}
}
