import java.awt.Point;
import javafx.geometry.Point2D;
import java.util.*;

public class World {
	public static final int TREE_CAP = 10;
	public static final int HOGWEED_CAP = 10;
	public static final int PUDDLE_CAP = 5;
	Map<Point, Chunk> chunks;
	List<Entity> entities;
	Player player;
	long time;
	long seed;

	World() {
		chunks = new HashMap<Point, Chunk>();
		entities = new LinkedList<Entity>();
		seed = 128;
	}

	public void generateChunk(Point p) {
		Chunk c = new Chunk(this);
		BlockKey[][][] blocks = c.getBlocks();
		Random g = new Random(seed + (((long)p.getY() << 32) | (long)p.getX()));
		for(int i = g.nextInt(TREE_CAP); i > 0; i--)
			blocks[g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][1] = BlockKey.TREE;
		for(int i = g.nextInt(HOGWEED_CAP); i > 0; i--)
			blocks[g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][1] = BlockKey.HOGWEED;
		for(int i = g.nextInt(PUDDLE_CAP); i > 0; i--)
			blocks[g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][g.nextInt(Chunk.CHUNK_SIDE_LENGTH)][0] = BlockKey.WATER;

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

	public static Point blockCoordinate(Point2D p) {
		return new Point((int)Math.floor(p.getX()), (int)Math.floor(p.getY()));
	}
}
