import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockTree extends Block {
	BlockTree() {
		super(new File("Artwork/tree_small.png"), false);
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isSolid() {
		return true;
	}
}
