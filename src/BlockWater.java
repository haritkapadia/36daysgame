import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockWater extends Block {
	Image image;

	BlockWater() {
		super(new File("Artwork/water_small.png"));
	}

	public void interact(Entity e) {}

	public boolean isTransparent() {
		return false;
	}
}
