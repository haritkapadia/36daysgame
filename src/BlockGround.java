import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockGround extends Block {
	BlockGround() {
		super(new File("Artwork/grass_small.png"));
	}

	public boolean isTransparent() {
		return false;
	}

	public boolean isSolid() {
		return true;
	}
}
