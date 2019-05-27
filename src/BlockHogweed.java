import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockHogweed extends Block implements Destroyable {
	BlockHogweed() {
		super(new File("Artwork/gianthogweed_small.png"));
	}

	public void onDestroy(World world, int x, int y, int z) {
		if(world.getBlock(x - 1, y, z) == 0)
			world.setBlock(x - 1, y, z, 3);
		if(world.getBlock(x + 1, y, z) == 0)
			world.setBlock(x + 1, y, z, 3);
		if(world.getBlock(x, y - 1, z) == 0)
			world.setBlock(x, y - 1, z, 3);
		if(world.getBlock(x, y + 1, z) == 0)
			world.setBlock(x, y + 1, z, 3);
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isSolid() {
		return false;
	}
}
