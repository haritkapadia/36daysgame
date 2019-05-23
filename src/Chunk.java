import java.awt.Point;

public class Chunk {
	public static final int CHUNK_SIDE_LENGTH = 32;
	Block[][][] blocks = new Block[CHUNK_SIDE_LENGTH][CHUNK_SIDE_LENGTH][2];
	Point location;

	Chunk(int x, int y) {
		location = new Point(x, y);
	}

	Chunk(String test) {
		location = new Point(0, 0);
		blocks[0][0][0] = new BlockGround();
		blocks[2][3][0] = new BlockGround();
		blocks[4][5][0] = new BlockGround();
	}

	public Block[][][] getBlocks() {
		return blocks;
	}

	// UH might not work xd dab dab lol
	public static Point globalToChunkPoint(Point2D p) {
		if(p.getY() < 0)
			return new Point((int)p.getX() / CHUNK_SIDE_LENGTH, (int)p.getY() / CHUNK_SIDE_LENGTH - 1);
		else
			return new Point((int)p.getX() / CHUNK_SIDE_LENGTH, (int)p.getY() / CHUNK_SIDE_LENGTH);
	}

	public static Point globalToChunkPoint(Point p) {
		if(p.getY() < 0)
			return new Point(p.getX() / CHUNK_SIDE_LENGTH, p.getY() / CHUNK_SIDE_LENGTH - 1);
		else
			return new Point(p.getX() / CHUNK_SIDE_LENGTH, p.getY() / CHUNK_SIDE_LENGTH);
	}
}
