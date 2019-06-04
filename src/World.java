import java.awt.Point;
import javafx.geometry.Point2D;
import java.util.*;

public class World {
	public static final HashMap <BlockKey, Integer> CAPS = new HashMap<BlockKey, Integer>(){{
		put(BlockKey.POISON, new Integer(0));
		put(BlockKey.WOOD, new Integer(0));
		put(BlockKey.GROUND, new Integer(0));
		put (BlockKey.TREE, new Integer(100));
		put(BlockKey.HOGWEED, new Integer(10));
		put(BlockKey.WATER, new Integer(5));
		put(BlockKey.KINGBOLETE, new Integer(3));
		put(BlockKey.NORTHERNBLUEFLAG, 2);
		put(BlockKey.FLYAGARIC, 4);
		put(BlockKey.CHANTERELLE, 2);
		put(BlockKey.DESTROYINGANGEL, 2);
		put(BlockKey.FLINT, 1);
		put(BlockKey.BEDSTRAW, 5);
		put(BlockKey.ELDERBERRY, 3);
		put(BlockKey.FIDDLEHEADS, 2);
		put(BlockKey.INDIANPIPE, 2);
		put(BlockKey.MOONSEED, 2);
		put(BlockKey.MOREL, 2);
		put(BlockKey.MUSHROOM, 6);
		put(BlockKey.STRAWBERRIES, 5);
	}};
	Map<Point, Chunk> chunks;
	List<Entity> entities;
	Player player;
	Stopwatch s;
	long seed;

	World() {
		chunks = new HashMap<Point, Chunk>();
		entities = new LinkedList<Entity>();
		seed = 128;
		s = new Stopwatch();
	}

	public void generateChunk(Point p) {
		Chunk c = new Chunk(this);
		BlockKey[][][] blocks = c.getBlocks();
		Random g = new Random(seed + (((long)p.getY() << 32) | (long)p.getX()));

		for(BlockKey block : BlockKey.values()){
			if(CAPS.get(block) >0){
				for(int i = g.nextInt(CAPS.get(block)); i > 0; i--)
					blocks[g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][1] = block;
			}
		}

		for(int i = 0; i < blocks.length; i++)
			for(int j = 0; j < blocks[i].length; j++)
			if(blocks[i][j][0] == null)
			blocks[i][j][0] = BlockKey.GROUND;
		putChunk(p, c);
	}

	public Chunk getChunk(int x, int y) {
		return chunks.get(new Point(x, y));
	}

	public Chunk getChunk(Point p) {
		return chunks.get(p);
	}

	public BlockKey getBlock(int x, int y, int z) {
		Point p = Chunk.globalToChunkPoint(new Point2D(x, y));
		if(getChunk(p) == null)
			generateChunk(p);
		Chunk c = getChunk(p);
		int i = (Chunk.CHUNK_SIDE_LENGTH + x % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int j = (Chunk.CHUNK_SIDE_LENGTH + y % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int k = (Chunk.CHUNK_SIDE_LENGTH + z % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		return c.getBlock(i, j, k);
	}

	public void setBlock(int x, int y, int z, BlockKey block) {
		Chunk c = getChunk(Chunk.globalToChunkPoint(new Point2D(x, y)));
		int i = (Chunk.CHUNK_SIDE_LENGTH + x % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int j = (Chunk.CHUNK_SIDE_LENGTH + y % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		int k = (Chunk.CHUNK_SIDE_LENGTH + z % Chunk.CHUNK_SIDE_LENGTH) % Chunk.CHUNK_SIDE_LENGTH;
		c.setBlock(i, j, k, block);
		// c.updateChunkImage(i, j);
	}

	public void destroyBlock(int x, int y, int z) {
		BlockKey b = getBlock(x, y, z);
		if(b != null && ResourceManager.getBlock(b) instanceof Destroyable) {
			((Destroyable)ResourceManager.getBlock(b)).onDestroy(this, x, y, z);
			setBlock(x, y, z, null);
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
		return -0.5 * y + 0.8;
	}

	public static Point blockCoordinate(Point2D p) {
		return new Point((int)Math.floor(p.getX()), (int)Math.floor(p.getY()));
	}
}
