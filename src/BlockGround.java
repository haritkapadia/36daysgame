import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockGround extends Block {
	BlockGround() {
		super(new File("Artwork/grass_small.png"), true);
	}

	public boolean isTransparent() {
		return false;
	}
}
