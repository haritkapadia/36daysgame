import java.awt.Point;
import java.util.*;

public class World {
	Map<Point, Chunk> chunks;
	Set<Entity> entities;
	Player player;
	long time;

	World() {
		chunks = new HashMap<Point, Chunk>();
		entities = new HashSet<Entity>();
		entities.add(player);
		chunks.put(new Point(0, 0), new Chunk("test"));
	}

	public Chunk getChunk(int x, int y) {
		return chunks.get(new Point(x, y));
	}
}
