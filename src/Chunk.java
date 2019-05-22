import java.awt.Point;

public class Chunk {
	public static final int CHUNK_SIDE_LENGTH = 32;
	Block[][][] blocks = new Block[CHUNK_SIDE_LENGTH][CHUNK_SIDE_LENGTH][2];
	Point location;

	Chunk(int x, int y) {
		location = new Point(x, y);
	}
}
