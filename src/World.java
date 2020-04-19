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
import org.j3d.texture.procedural.PerlinNoiseGenerator;

/**
 * This class is used to create a game world object and stores the identity of every block in the game
 *
 * Variables:
 *
 * FREQ     -A constant that stores the frequency of the bedstraw blocks, all other block frequencies are based off of this
 * CAPS     -Stores the maximum number of each block that can generate in a chunk
 * chunk    -A Map that stores each chunk with the coordinates of the chunk
 * entities -A List of all the entities (fires, player) in the world
 * s        -A Stopwatch object used to calculate in game time
 * seed     -A number used to generate the world
 * player   -Stores the Player object that the user controls
 * won      -Stores whether or not the game has been won yet
 * worldPath -Stores the file path of the world save files
 */
public class World {
	final static int FREQ = 10;
	public static final HashMap <BlockKey, Integer> CAPS = new HashMap<BlockKey, Integer>(){{
			put(BlockKey.TREE, new Integer(100));
			put(BlockKey.HOGWEED, World.FREQ/3);
			// put(BlockKey.WATER, 10);
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
			// put(BlockKey.CATTAIL, World.FREQ);
			put(BlockKey.ANT, 30*World.FREQ);
			put(BlockKey.WORM, 20*World.FREQ);
			put(BlockKey.GRASSHOPPER, 20*World.FREQ);
		}};
	Map<Point, Chunk> chunks;
	List<Entity> entities;
	Stopwatch s;
	long seed;
	Player player;
	boolean won;
	Path worldPath;

	/**
	 * Class constructor, creates a pane with some buttons and labels
	 * @param scene The Scene object that everything is displayed on
	 * @param node The node that contains this pane
	 * @param worldPath The file path that contains the world save files
	 */
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
				s = new Stopwatch(Long.parseLong(new String(Files.readAllBytes(worldPath.resolve("offset")))));
				System.out.println("offset: " + worldPath.resolve("offset").toString());
			}
			catch (NoSuchFileException e) {
				s = new Stopwatch(0);
                System.out.println("Made new world");
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

	/**
	 * Saves the world to a file
	 */
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

	/**
	 * Saves the world to a file
	 */
	public void startFire(double x, double y) {
		entities.add(new EntityFire(this, (int)x, (int)y));
	}

	/**
	 * Randomly generates a chunk of the world (32 blocks by 32 blocks)
	 *
	 * @param p The coordinates of the Chunk
	 *
	 * Variables:
	 *
	 * c       -A new Chunk object
	 * blocks  -An array that stores the identity of each block in the chunk
	 * g       -A random number used in chunk generation
	 *
	 */
	public void generateChunk(Point p) {
		Chunk c = new Chunk();
		BlockKey[][][] blocks = c.getBlocks();
		PerlinNoiseGenerator png = new PerlinNoiseGenerator((int)seed);
		Random g = new Random(seed + (int)p.getX() * 1037l + (int)p.getY());


		for(int y = 0; y < Chunk.CHUNK_SIDE_LENGTH; y++) {
			for(int x = 0; x < Chunk.CHUNK_SIDE_LENGTH; x++) {
				float nx = x + Chunk.CHUNK_SIDE_LENGTH * (int)p.getX() + 0.5f;
				float ny = y + Chunk.CHUNK_SIDE_LENGTH * (int)p.getY() + 0.5f;
				float e = 8 * png.noise2(1f / 8 * nx, 1f / 8 * ny);
				float value = (float)Math.pow(e, 6.25);
				if(1 <= value && value < 6)
					blocks[y][x][1] = BlockKey.CATTAIL;
				else if(6 <= value)
					blocks[y][x][1] = BlockKey.WATER;
			}
		}


		for(BlockKey block : CAPS.keySet()){
			if(CAPS.get(block) > 0){
				for(int i = g.nextInt(CAPS.get(block)); i > 0; i--) {
					int row = g.nextInt(Chunk.CHUNK_SIDE_LENGTH);
					int col = g.nextInt(Chunk.CHUNK_SIDE_LENGTH);
					if(blocks[row][col][1] == null)
						blocks[row][col][1] = block;
				}
			}
		}

		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
				if(blocks[i][j][0] == null)
					blocks[i][j][0] = BlockKey.GROUND;
		putChunk(p, c);
	}

	/**
	 * @returns The chunk at a given x and y coordinate
	 * @param x The x coordinate of the chunk
	 * @param y The y coordinate of the chunk
	 */
	public Chunk getChunk(int x, int y) {
		if(chunks.get(new Point(x, y)) == null) {
			if(Files.exists(Paths.get(worldPath.toString(), ((((long)x) << 32) | ((long)y & 0xffffffffL)) + ".chunk")))
				putChunk(new Point(x, y), Chunk.readChunk(Paths.get(worldPath.toString(), ((((long)x) << 32) | ((long)y & 0xffffffffL)) + ".chunk").toFile()));
			else
				generateChunk(new Point(x, y));
		}
		return chunks.get(new Point(x, y));
	}

	/**
	 * @returns the chunk at a given point
	 * @param p the coordinates of the chunk as a point object
	 */
	public Chunk getChunk(Point p) {
		return getChunk((int)p.getX(), (int)p.getY());
	}

	/**
	 * Writes each chunk to a file
	 */
	public void writeChunks() {
		for(Map.Entry<Point, Chunk> e : chunks.entrySet())
			e.getValue().writeChunk(Paths.get(worldPath.toString(), ((((long)e.getKey().getX()) << 32) | ((long)e.getKey().getY() & 0xffffffffL)) + ".chunk").toFile());
	}

	/**
	 * Returns the block at a given x, y, and z coordinate
	 *
	 * Variables:
	 *
	 * i, j, k     -The coordinates of the block in reference to the chunk
	 *
	 * @param x The x coordinate of the block
	 * @param y The y coordinate of the block
	 * @param z The z coordinate of the block
	 */
	public BlockKey getBlock(int x, int y, int z) {
		Point p = Chunk.globalToChunkPoint(new Point2D(x, y));
		Chunk c = getChunk(p);
		int i = (Chunk.CHUNK_SIDE_LENGTH + x % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int j = (Chunk.CHUNK_SIDE_LENGTH + y % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int k = (Chunk.CHUNK_SIDE_LENGTH + z % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		return c.getBlock(i, j, k);
	}

	/**
	 * Changes the block to a new block if that block is null
	 *
	 * Variables:
	 *
	 * i, j, k     -The coordinates of the block in reference to the chunk
	 *
	 * @param x The x coordinate of the original block
	 * @parm y The y coordinate of the original block
	 * @param z The z coordinate of the original block
	 * @param block The BlockKey of the new block
	 */
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

	/**
	 * Sets the block to a new block regardless of whether it's null or not
	 * @param x the x coordinate of the block
	 * @param y the y coordinate of the block
	 * @param z the z coordinate of the block
	 *
	 * Variables:
	 *
	 * i, j, k     -the coordinates of the block in reference to the chunk
	 */
	public void setBlockUnsafe(int x, int y, int z, BlockKey block) {
		Chunk c = getChunk(Chunk.globalToChunkPoint(new Point2D(x, y)));
		int i = (Chunk.CHUNK_SIDE_LENGTH + x % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int j = (Chunk.CHUNK_SIDE_LENGTH + y % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int k = (Chunk.CHUNK_SIDE_LENGTH + z % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		c.setBlock(i, j, k, block);
	}

	/**
	 * Changes the block to null
	 *
	 * Variables:
	 *
	 * b     -The identity of the block
	 *
	 * @param x The x coordinate of the block
	 * @param y The y coordinate of the block
	 * @param z The z coordinate of the block
	 */
	public void destroyBlock(int x, int y, int z) {
		BlockKey b = getBlock(x, y, z);
		if(b != null && ResourceManager.getBlock(b) instanceof Destroyable) {
			setBlockUnsafe(x, y, z, null);
			((Destroyable)ResourceManager.getBlock(b)).onDestroy(this, x, y, z);
		}
	}

	/**
	 * This method is called when the user right clicks a block
	 * @param e The player object
	 * @param x The x coordinate of the block
	 * @param y The y coordinate of the block
	 * @param z The z coordinate of the block
	 */
	public void interactBlock(Entity e, int x, int y, int z) {
		BlockKey b = getBlock(x, y, z);
		if(b != null && ResourceManager.getBlock(b) instanceof Interactable) {
			((Interactable)ResourceManager.getBlock(b)).onInteract(e, this, x, y, z);
		}
	}

	/**
	 * @return The world's seed number
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * Places a chunk into the world's chunk map
	 * @param p The coordinates of the chunk
	 * @param c The chunk that is to be placed
	 */
	public void putChunk(Point p, Chunk c) {
		chunks.put(p, c);
	}

	/**
	 * @return The list of entities
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * Adds an entity to the list of entities
	 * @param e The entity that is to be added
	 */
	public void addEntity(Entity e) {
		entities.add(e);
	}

	/**
	 * Removes an entity from the list of entities
	 * @param e The entity that is to be removed
	 */
	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	/**
	 * @return The game Stopwatch object
	 */
	public Stopwatch getStopwatch() {
		return s;
	}

	// https://www.desmos.com/calculator/e8oqtfiukv
	/**
	 * @return The light level of the world
	 */
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
		return -0.5 * y + 0.8;
		// return 0.5 * y + 0.5;
	}

	/**
	 * @return the player object
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player variable reference a new player object
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @returns Whether or not the game has been won
	 */
	public boolean getWon() {
		return won;
	}

	/**
	 * Code that is executed once the game has been won
	 */
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

	/**
	 * Returns the block coordinates of the block at world coordinates
	 *
	 * @param p The world coordinate
	 * @return The block coordinate
	 */
	public static Point blockCoordinate(Point2D p) {
		return new Point((int)Math.floor(p.getX()), (int)Math.floor(p.getY()));
	}
}
