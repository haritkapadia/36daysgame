import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockGround extends Block {
	Image image;

	BlockGround() {
		super(new File("Artwork/grass_small.png"));
	}

	public void interact(Entity e) {}

	public boolean isTransparent() {
		return false;
	}
}
