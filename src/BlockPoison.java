import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockPoison extends Block {
	BlockPoison() {
		super(new File("Artwork/poison_small.png"));
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isSolid() {
		return false;
	}
}
