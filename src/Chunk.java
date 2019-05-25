import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
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
	public static final int TREE_CAP = 10;
	public static final int PUDDLE_CAP = 5;
	Block[][][] blocks = new Block[CHUNK_SIDE_LENGTH][CHUNK_SIDE_LENGTH][2];
	Point location;
	BufferedImage chunkImage;
	World world;
	long seed;

	Chunk(World world, Point location) {
		this.world = world;
		this.location = location;
		seed = world.getSeed() + (((long)location.getY() << 32) | (long)location.getX());
		generateChunk();
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
		Random g = new Random(seed);
		for(int i = g.nextInt(TREE_CAP); i > 0; i--)
			blocks[g.nextInt(CHUNK_SIDE_LENGTH)][g.nextInt(CHUNK_SIDE_LENGTH)][1] = new BlockTree();
		for(int i = g.nextInt(PUDDLE_CAP); i > 0; i--)
			blocks[g.nextInt(CHUNK_SIDE_LENGTH)][g.nextInt(CHUNK_SIDE_LENGTH)][0] = new BlockWater();

		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
				if(blocks[i][j][0] == null)
					blocks[i][j][0] = new BlockGround();
	}

	// 90% sure this works
	public BufferedImage getChunkImage() {
		if(chunkImage == null) {
			chunkImage = new BufferedImage(32 * CHUNK_SIDE_LENGTH, 32 * CHUNK_SIDE_LENGTH, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = chunkImage.createGraphics();
			for(int i = 0; i < blocks.length; i++) {
				for(int j = 0; j < blocks[i].length; j++) {
					int k = blocks[i][j].length - 1;
					while(k > 0 && (blocks[i][j][k] == null || blocks[i][j][k].isTransparent()))
						k -= 1;
					for(; k < blocks[i][j].length; k++) {
						if(blocks[i][j][k] != null) {
							AffineTransform t = AffineTransform.getTranslateInstance(i * 32, (CHUNK_SIDE_LENGTH - j - 1) * 32);
							g.drawImage(blocks[i][j][k].getImage(), t, null);
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
