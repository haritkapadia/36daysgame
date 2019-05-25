import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class Block implements Drawable {
	Image image;

	Block(File file) {
		try {
			image = ImageIO.read(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Image getImage() {
		return image;
	}

	public abstract void interact(Entity e);
}
