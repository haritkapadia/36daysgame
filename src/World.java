import java.awt.Point;
import java.util.*;

public class World {
	Map<Point, Chunk> chunks;
	Set<Entity> entities;
	Player player;
	long time;

	World() {
		entities = new HashSet<Entity>();
		entities.add(player);
	}
}
