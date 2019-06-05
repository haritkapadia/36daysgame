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
import java.io.*;

public class Chunk implements Serializable {
	public static final int CHUNK_SIDE_LENGTH = 32;
	BlockKey[][][] blocks = new BlockKey[CHUNK_SIDE_LENGTH][CHUNK_SIDE_LENGTH][2];

	public BlockKey[][][] getBlocks() {
		return blocks;
	}

	public BlockKey getBlock(int x, int y, int z) {
		return blocks[x][y][z];
	}

	public void setBlock(int x, int y, int z, BlockKey block) {
		blocks[x][y][z] = block;
	}

	public static Point globalToChunkPoint(Point2D p) {
		return new Point((int)Math.floor(p.getX()/CHUNK_SIDE_LENGTH), (int)Math.floor(p.getY()/CHUNK_SIDE_LENGTH));
	}

	public static Point2D chunkToGlobalPoint(Point p) {
		return new Point2D(CHUNK_SIDE_LENGTH * p.getX(), CHUNK_SIDE_LENGTH * p.getY());
	}

	public static void writeChunk(long worldSeed, Chunk c, long id) {
		try {
			FileOutputStream fos = new FileOutputStream("world" + worldSeed + "/" + id + ".chunk");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(c);
			oos.close();
			fos.close();
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static Chunk readChunk(long worldSeed, long id) {
		Chunk c = null;
		try {
			FileInputStream fos = new FileInputStream("world" + worldSeed + "/" + id + ".chunk");
			ObjectInputStream oos = new ObjectInputStream(fos);
			c = (Chunk)oos.readObject();
			oos.close();
			fos.close();
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		return c;
	}
}
