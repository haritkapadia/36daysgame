import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockTree extends Block {
	Image image;

	BlockTree() {
		super(new File("Artwork/tree_small.png"));
		destroyable = false;
		solid = false;
	}

	public void interact(Entity e) {}

	public boolean isTransparent() {
		return true;
	}
}
