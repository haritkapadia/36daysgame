import java.awt.Point;
import java.util.*;

public class World {
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

	public Chunk getChunk(int x, int y) {
		return chunks.get(new Point(x, y));
	}

	public Chunk getChunk(Point p) {
		return chunks.get(p);
	}

	public long getSeed() {
		return seed;
	}

	public void putChunk(Point p) {
		chunks.put(p, new Chunk(this, p));
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}
}
