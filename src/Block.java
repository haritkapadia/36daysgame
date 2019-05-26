import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class Block implements Drawable {
	Image image;
	protected boolean destroyable;
	protected boolean solid;

	Block(File file) {
		try {
			image = ImageIO.read(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
		destroyable = false;
		solid = true;
	}

	public Image getImage() {
		return image;
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public boolean isSolid() {
		return destroyable;
	}

	public abstract void interact(Entity e);
	public void destroy(World world, int x, int y, int z) {}
}
