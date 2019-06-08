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
		put(BlockKey.FLINT, 2);
		put(BlockKey.BEDSTRAW, World.FREQ);
		put(BlockKey.ELDERBERRY, (int)(World.FREQ*0.8));
		put(BlockKey.FIDDLEHEADS, World.FREQ/2);
		put(BlockKey.INDIANPIPE, World.FREQ/2);
		put(BlockKey.MOONSEED, World.FREQ/10);
		put(BlockKey.MOREL, World.FREQ/3);
		put(BlockKey.MUSHROOM, World.FREQ);
		put(BlockKey.STRAWBERRIES, (int)(World.FREQ*0.9));
		put (BlockKey.ANT, 30*World.FREQ);
		put (BlockKey.WORM, 20*World.FREQ);
		put (BlockKey.GRASSHOPPER, 20*World.FREQ);
	}};
	Map<Point, Chunk> chunks;
	List<Entity> entities;
	Stopwatch s;
	long seed;
	Player player;
	boolean won;
	Path worldPath;

	World(long seed, Path worldPath) {
		this.seed = seed;
		this.worldPath = worldPath;
		chunks = new HashMap<Point, Chunk>();
		entities = new LinkedList<Entity>();
		try {
			if(!Files.isDirectory(worldPath))
				worldPath.toFile().mkdirs();
			won = Paths.get(worldPath.toString(), "won").toFile().exists();
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		try {
			try {
				s = new Stopwatch(Long.parseLong(new String(Files.readAllBytes(Paths.get(worldPath.toString(), "offset")))));
			}
			catch (NoSuchFileException e) {
				s = new Stopwatch(0);
			}

			File[] _entities = Paths.get(worldPath.toString(), "entities").toFile().listFiles();
			if(_entities == null)
				_entities = new File[0];

			for(File f : _entities) {
				Entity _e = Entity.readEntity(this, f);
				if(_e.getID().equals("player"))
					player = (Player)_e;
				entities.add(_e);
				f.delete();
			}
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void write() {
		try {
			Files.write(Paths.get(worldPath.toString(), "offset"), ("" + s.getElapsed()).getBytes("UTF-8"));
			if(!Files.isDirectory(Paths.get(worldPath.toString(), "entities")))
				Paths.get(worldPath.toString(), "entities").toFile().mkdirs();
			for(int i = 0; i < entities.size(); i++)
				entities.get(i).writeEntity(Paths.get(worldPath.toString(), "entities", i + "").toFile());
			String name = new String(Files.readAllBytes(worldPath.getParent().resolve("name")), Charset.forName("UTF-8"));
			Files.write(Paths.get(worldPath.toString(), "score"), (name + "\t" + (s.getElapsed() / 1000000)).getBytes("UTF-8"));
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		writeChunks();
	}

	public void startFire(double x, double y) {
		entities.add(new EntityFire(this, (int)x, (int)y));
	}

	 public void generateChunk(Point p) {
		 Chunk c = new Chunk();
		 BlockKey[][][] blocks = c.getBlocks();
		 Random g = new Random((int)((p.getX() + p.getY()) * (int)(p.getX() + p.getY() + 1) / 2 + (int)p.getY()));

		 // Point2D[][] rNodes = new Point2D[3][3];
		 // boolean[][] tNodes = new boolean[3][3];
		 // // Point2D riverNode = new Point2D(p.getX() + 32 * g.nextDouble(), p.getY() + 32 * g.nextDouble());
		 // // boolean terminatingNode = g.nextBoolean();
		 // for(int i = 0; i < 3; i++) {
		 //	 for(int j = 0; j < 3; j++) {
		 //		 int x = (int)p.getX() + i - 1;
		 //		 int y = (int)p.getY() + i - 1;
		 //		 Random r = new Random((x + y) * (x + y + 1) / 2 + y);
		 //		 rNodes[i][j] = new Point2D(x + 32 * r.nextDouble(), y + 32 * r.nextDouble());
		 //		 tNodes[i][j] = r.nextDouble() > 0.4; // 60% chance to be a terminating chunk
		 //	 }
		 // }
		 // if(!tNodes[1][1]) {
		 //	 if(!tNodes[0][1]) {
		 //		 for(double y = rNodes[1][1].getX(); y > rNodes[0][1].getX(); y--) {

		 //		 }
		 //	 }
		 // }

		 for(BlockKey block : CAPS.keySet()){
			 if(CAPS.get(block) > 0){
				 for(int i = g.nextInt(CAPS.get(block)); i > 0; i--) {
					 blocks[g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][1] = block;
				 }
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
			if(Files.exists(Paths.get(worldPath.toString(), ((((long)x) << 32) | ((long)y & 0xffffffffL)) + ".chunk")))
				putChunk(new Point(x, y), Chunk.readChunk(Paths.get(worldPath.toString(), ((((long)x) << 32) | ((long)y & 0xffffffffL)) + ".chunk").toFile()));
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
			e.getValue().writeChunk(Paths.get(worldPath.toString(), ((((long)e.getKey().getX()) << 32) | ((long)e.getKey().getY() & 0xffffffffL)) + ".chunk").toFile());
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
		if(c.getBlock(i, j, k) == null || ResourceManager.getBlock(c.getBlock(i, j, k)) instanceof InsectBlock) {
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

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public Stopwatch getStopwatch() {
		return s;
	}

	// https://www.desmos.com/calculator/e8oqtfiukv
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
		// return -0.5 * y + 0.8;
		return 0.5 * y + 0.5;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean getWon() {
		return won;
	}

	public void win() {
		try {
			Paths.get(worldPath.toString(), "won").toFile().createNewFile();
			won = true;
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}

	}

	public static Point blockCoordinate(Point2D p) {
		return new Point((int)Math.floor(p.getX()), (int)Math.floor(p.getY()));
	}
}
