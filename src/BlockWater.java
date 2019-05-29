import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockWater extends Block {
	BlockWater() {
		super(new File("Artwork/water_small.png"), true);
	}

	public boolean isTransparent() {
		return false;
	}

	public boolean isSolid() {
		return true;
	}
}
