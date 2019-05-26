import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockHogweed extends Block {
	Image image;

	BlockHogweed() {
		super(new File("Artwork/gianthogweed_small.png"));
		destroyable = true;
		solid = true;
	}

	public void interact(Entity e) {}

	public void destroy(World world, int x, int y, int z) {
		if(world.getBlock(x - 1, y, z) == null)
			world.setBlock(x - 1, y, z, new BlockPoison());
		if(world.getBlock(x + 1, y, z) == null)
			world.setBlock(x + 1, y, z, new BlockPoison());
		if(world.getBlock(x, y - 1, z) == null)
			world.setBlock(x, y - 1, z, new BlockPoison());
		if(world.getBlock(x, y + 1, z) == null)
			world.setBlock(x, y + 1, z, new BlockPoison());
	}

	public boolean isTransparent() {
		return true;
	}
}
