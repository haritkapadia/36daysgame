/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.awt.Point;
import javafx.geometry.Point2D;
import java.util.*;

public class World {
	final static int FREQ = 10;
	public static final HashMap <BlockKey, Integer> CAPS = new HashMap<BlockKey, Integer>(){{
		put(BlockKey.POISON, new Integer(0));
		put(BlockKey.WOOD, new Integer(0));
		put(BlockKey.GROUND, new Integer(0));
		put(BlockKey.TREE, new Integer(100));
		put(BlockKey.HOGWEED, World.FREQ/3);
		put(BlockKey.WATER, 10);
		put(BlockKey.KINGBOLETE, World.FREQ/10);
		put(BlockKey.NORTHERNBLUEFLAG, World.FREQ/2);
		put(BlockKey.FLYAGARIC, World.FREQ/5);
		put(BlockKey.CHANTERELLE, World.FREQ/10);
		put(BlockKey.DESTROYINGANGEL, World.FREQ/10);
		put(BlockKey.FLINT, 1);
		put(BlockKey.BEDSTRAW, World.FREQ);
		put(BlockKey.ELDERBERRY, (int)(World.FREQ*0.8));
		put(BlockKey.FIDDLEHEADS, World.FREQ/2);
		put(BlockKey.INDIANPIPE, World.FREQ/2);
		put(BlockKey.MOONSEED, World.FREQ/10);
		put(BlockKey.MOREL, World.FREQ/3);
		put(BlockKey.MUSHROOM, World.FREQ);
		put(BlockKey.STRAWBERRIES, (int)(World.FREQ*0.9));
	}};
	Map<Point, Chunk> chunks;
	List<Entity> entities;
	Stopwatch s;
	long seed;
	Player player;

	World() {
		chunks = new HashMap<Point, Chunk>();
		entities = new LinkedList<Entity>();
		seed = 128;
		s = new Stopwatch(0);
		try {
			if(!Files.isDirectory(Paths.get("world" + seed)))
				new File("world" + seed + "/").mkdirs();
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	World(long seed) {
		this.seed = seed;
		chunks = new HashMap<Point, Chunk>();
		entities = new LinkedList<Entity>();
		try {
			if(!Files.isDirectory(Paths.get("world" + seed)))
				new File("world" + seed + "/").mkdirs();
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		try {
			try {
				s = new Stopwatch(Long.parseLong(new String(Files.readAllBytes(Paths.get("world" + seed + "/offset")))));
			}
			catch (NoSuchFileException e) {
				s = new Stopwatch(0);
			}

			File[] _entities = new File("world" + seed + "/entities").listFiles();
			System.out.println("Entities: " + _entities.length);

			for(File f : _entities) {
				String _e = new String(Files.readAllBytes(f.toPath()));
				Entity __e = new Entity(this, _e);
				System.out.println("__e.getID " + __e.getID());

				if(__e.getID() != null && __e.getID().equals("player.save")) {
					player = new Player(this, _e);
					entities.add(player);
					System.out.println("Added player");

				} else {
					entities.add(__e);
				}
			}
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void write() {
		try {
			Files.write(Paths.get("world" + seed + "/offset"), ("" + s.getElapsed()).getBytes("UTF-8"));
			if(!Files.isDirectory(Paths.get("world" + seed + "/entities")))
				new File("world" + seed + "/entities").mkdirs();
			for(Entity e : entities)
				Files.write(Paths.get("world" + seed + "/entities/" + e.getID()), Arrays.asList(new String[]{e.getAsString()}), Charset.forName("UTF-8"));
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		writeChunks();

	}

	public void generateChunk(Point p) {
		Chunk c = new Chunk();
		BlockKey[][][] blocks = c.getBlocks();
		Random g = new Random((int)((p.getX() + p.getY()) * (p.getX() + p.getY() + 1) / 2 + p.getY()));

		for(BlockKey block : CAPS.keySet()){
			if(CAPS.get(block) > 0){
				for(int i = g.nextInt(CAPS.get(block)); i > 0; i--)
					blocks[g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][1] = block;
			}
		}

		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
			if(blocks[i][j][0] == null)
				blocks[i][j][0] = BlockKey.GROUND;
		// Chunk.writeChunk(c, (((long)p.getX()) << 32) | ((long)p.getY() & 0xffffffffL));
		putChunk(p, c);
	}

	public Chunk getChunk(int x, int y) {
		if(chunks.get(new Point(x, y)) == null) {
			if(Files.exists(Paths.get("world" + seed + "/" + ((((long)x) << 32) | ((long)y & 0xffffffffL)) + ".chunk")))
				putChunk(new Point(x, y), Chunk.readChunk(seed, (((long)x) << 32) | ((long)y & 0xffffffffL)));
			else
				generateChunk(new Point(x, y));
		}
		return chunks.get(new Point(x, y));
	}

	public Chunk getChunk(Point p) {
		return getChunk((int)p.getX(), (int)p.getY());
	}

	public void writeChunks() {
		for(Map.Entry<Point, Chunk> e : chunks.entrySet())
			Chunk.writeChunk(seed, e.getValue(), (((long)e.getKey().getX()) << 32) | ((long)e.getKey().getY() & 0xffffffffL));
	}

	public BlockKey getBlock(int x, int y, int z) {
		Point p = Chunk.globalToChunkPoint(new Point2D(x, y));
		// if(getChunk(p) == null)
		// generateChunk(p);
		Chunk c = getChunk(p);
		int i = (Chunk.CHUNK_SIDE_LENGTH + x % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int j = (Chunk.CHUNK_SIDE_LENGTH + y % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int k = (Chunk.CHUNK_SIDE_LENGTH + z % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		return c.getBlock(i, j, k);
	}

	public boolean setBlock(int x, int y, int z, BlockKey block) {
		Chunk c = getChunk(Chunk.globalToChunkPoint(new Point2D(x, y)));
		int i = (Chunk.CHUNK_SIDE_LENGTH + x % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int j = (Chunk.CHUNK_SIDE_LENGTH + y % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int k = (Chunk.CHUNK_SIDE_LENGTH + z % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		if(c.getBlock(i, j, k) == null) {
			c.setBlock(i, j, k, block);
			return true;
		}
		return false;
	}

	public void setBlockUnsafe(int x, int y, int z, BlockKey block) {
		Chunk c = getChunk(Chunk.globalToChunkPoint(new Point2D(x, y)));
		int i = (Chunk.CHUNK_SIDE_LENGTH + x % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int j = (Chunk.CHUNK_SIDE_LENGTH + y % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int k = (Chunk.CHUNK_SIDE_LENGTH + z % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		c.setBlock(i, j, k, block);
	}

	public void destroyBlock(int x, int y, int z) {
		BlockKey b = getBlock(x, y, z);
		if(b != null && ResourceManager.getBlock(b) instanceof Destroyable) {
			((Destroyable)ResourceManager.getBlock(b)).onDestroy(this, x, y, z);
			setBlockUnsafe(x, y, z, null);
		}
	}

	public void interactBlock(Entity e, int x, int y, int z) {
		BlockKey b = getBlock(x, y, z);
		if(b != null && ResourceManager.getBlock(b) instanceof Interactable) {
			((Interactable)ResourceManager.getBlock(b)).onInteract(e, this, x, y, z);
		}
	}

	public long getSeed() {
		return seed;
	}

	public void putChunk(Point p, Chunk c) {
		chunks.put(p, c);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public Stopwatch getStopwatch() {
		return s;
	}

	// https://www.desmos.com/calculator/iajixvfkqt
	public double getLightLevel() {
		double x = (s.getElapsed() / 1e9) % 600;
		double y = 0;
		if(x >= 580)
			y = Math.sin(Math.PI / 40 * x);
		else if(x >= 320)
			y = 1;
		else if(x >= 280)
			y = 0.7 * Math.sin(Math.PI / 40 * (x + 20)) + 0.3;
		else if(x >= 20)
			y = -0.4;
		else if(x >= 0)
			y = -0.4 * Math.sin(Math.PI / 40 * x);
		return -0.5 * y + 0.8;//0.5 * y + 0.5;
	}

	public Player getPlayer() {
		return player;
	}

	public static Point blockCoordinate(Point2D p) {
		return new Point((int)Math.floor(p.getX()), (int)Math.floor(p.getY()));
	}
}
