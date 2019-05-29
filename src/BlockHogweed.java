import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockHogweed extends Block implements Destroyable {
	BlockHogweed() {
		super(new File("Artwork/gianthogweed_small.png"));
	}

	public void onDestroy(World world, int x, int y, int z) {
		if(world.getBlock(x - 1, y, z) == null)
			world.setBlock(x - 1, y, z, BlockKey.POISON);
		if(world.getBlock(x + 1, y, z) == null)
			world.setBlock(x + 1, y, z, BlockKey.POISON);
		if(world.getBlock(x, y - 1, z) == null)
			world.setBlock(x, y - 1, z, BlockKey.POISON);
		if(world.getBlock(x, y + 1, z) == null)
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
