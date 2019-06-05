/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class BlockHogweed extends Block implements Destroyable {
	BlockHogweed() {
		super("Artwork/gianthogweed_small.png");
	}

	public void onDestroy(World world, int x, int y, int z) {
		world.setBlock(x - 1, y, z, BlockKey.POISON);
		world.setBlock(x + 1, y, z, BlockKey.POISON);
		world.setBlock(x, y - 1, z, BlockKey.POISON);
		world.setBlock(x, y + 1, z, BlockKey.POISON);
		synchronized(this) {
			notifyAll();
		}
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isSolid() {
		return false;
	}
}
