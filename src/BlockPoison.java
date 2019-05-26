import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockPoison extends Block {
	Image image;

	BlockPoison() {
		super(new File("Artwork/poison_small.png"));
	}

	public void interact(Entity e) {}

	public boolean isTransparent() {
		return true;
	}
}
