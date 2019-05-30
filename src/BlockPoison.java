import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class BlockPoison extends Block implements Interactable {
	BlockPoison() {
		super(new File("Artwork/poison_small.png"));
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isSolid() {
		return false;
	}

	public void onInteract(Entity e, World world, int x, int y, int z) {
		synchronized(this) {
			notifyAll();
		}
	}
}
