package daysgame;

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

/**
 * This class generates a chunk, which is 32 by 32 blocks
 *
 * Variables:
 * CHUNK_SIDE_LENGTH     -The side length of the chunk
 * blocks                -An array of BlockKey objects used to store each block in the chunk
 *
 */
public class Chunk implements Serializable {
        public static final int CHUNK_SIDE_LENGTH = 32;
        private BlockKey[][][] blocks = new BlockKey[CHUNK_SIDE_LENGTH][CHUNK_SIDE_LENGTH][2];
        
        /**
         * @returns the array of BlockKey objects which stores the identity of every block in the chunk
         */
        public BlockKey[][][] getBlocks() {
                return blocks;
        }
        
        /**
         * @returns the block at a given x, y, and z coordinate
         */
        public BlockKey getBlock(int x, int y, int z) {
                return blocks[x][y][z];
        }
        
        /**
         * Sets the block at a given x, y, and z coordinate to a new value
         * @param x is the x coordinate
         * @param y is the y coordinate
         * @param z is the z coordinate
         * @param block is the new value for the block
         */
        public void setBlock(int x, int y, int z, BlockKey block) {
                blocks[x][y][z] = block;
        }
        
        /**
         * @returns the location of a block relative to the chunk boundaries
         * @param p the location of the block
         */
        public static Point globalToChunkPoint(Point2D p) {
                return new Point((int)Math.floor(p.getX()/CHUNK_SIDE_LENGTH), (int)Math.floor(p.getY()/CHUNK_SIDE_LENGTH));
        }
        
        /**
         * @returns the location of the block relative to the chunk converted to the absolute location of the block
         * @param the location of the block
         */
        public static Point2D chunkToGlobalPoint(Point p) {
                return new Point2D(CHUNK_SIDE_LENGTH * p.getX(), CHUNK_SIDE_LENGTH * p.getY());
        }
        
        /**
         * Writes the chunk to a file
         * @param worldSeed the identity of the world
         * @param c the Chunk that is to be written to a file
         * @long id the number that represents the chunk
         */
        public void writeChunk(File file) {
                try {
                        FileOutputStream fos = new FileOutputStream(file);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(this);
                        oos.close();
                        fos.close();
                }
                catch (Throwable e) {
                        System.out.println("Error " + e.getMessage());
                        e.printStackTrace();
                }
        }
        
        /**
         * Reads the chunk from a file
         * @returns the chunk that the file stored
         * @param worldSeed stores the identity of the world
         * @param id stores the idenitify of the chunk
         */
        public static Chunk readChunk(File file) {
                Chunk c = null;
                try {
                        FileInputStream fos = new FileInputStream(file);
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
