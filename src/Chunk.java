/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

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
	BlockKey[][][] blocks = new BlockKey[CHUNK_SIDE_LENGTH][CHUNK_SIDE_LENGTH][2];
	BufferedImage chunkImage;
	World world;
	long seed;

	Chunk(World world) {
		this.world = world;
	}

	public BlockKey[][][] getBlocks() {
		return blocks;
	}

	public BlockKey getBlock(int x, int y, int z) {
		return blocks[x][y][z];
	}

	public void setBlock(int x, int y, int z, BlockKey block) {
		blocks[x][y][z] = block;
	}

	/*
	public BufferedImage getChunkImage() {
		if(chunkImage == null) {
			chunkImage = new BufferedImage(32 * CHUNK_SIDE_LENGTH, 32 * CHUNK_SIDE_LENGTH, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = chunkImage.createGraphics();
			for(int i = 0; i < blocks.length; i++) {
				for(int j = 0; j < blocks[i].length; j++) {
					int k = blocks[i][j].length - 1;
					while(k > 0 && (blocks[i][j][k] == null || ResourceManager.getBlock(blocks[i][j][k]).isTransparent()))
						k -= 1;
					for(; k < blocks[i][j].length; k++) {
						if(blocks[i][j][k] != null) {
							AffineTransform t = AffineTransform.getTranslateInstance(i * 32, (CHUNK_SIDE_LENGTH - j - 1) * 32);
							g.drawImage(ResourceManager.getBlock(blocks[i][j][k]).getImage(), t, null);
						}
					}
				}
			}
		}
		return chunkImage;
	}

	public void updateChunkImage(int i, int j) {
		Graphics2D g = chunkImage.createGraphics();
		int k = blocks[i][j].length - 1;
		while(k > 0 && (blocks[i][j][k] == null || ResourceManager.getBlock(blocks[i][j][k]).isTransparent()))
			k -= 1;
		for(; k < blocks[i][j].length; k++) {
			if(blocks[i][j][k] != null) {
				AffineTransform t = AffineTransform.getTranslateInstance(i * 32, (CHUNK_SIDE_LENGTH - j - 1) * 32);
				g.drawImage(ResourceManager.getBlock(blocks[i][j][k]).getImage(), t, null);
			}
		}
	}
	*/

	// This works
	public static Point globalToChunkPoint(Point2D p) {
		return new Point((int)Math.floor(p.getX()/CHUNK_SIDE_LENGTH), (int)Math.floor(p.getY()/CHUNK_SIDE_LENGTH));
	}

	public static Point2D chunkToGlobalPoint(Point p) {
		return new Point2D(CHUNK_SIDE_LENGTH * p.getX(), CHUNK_SIDE_LENGTH * p.getY());
	}
}
