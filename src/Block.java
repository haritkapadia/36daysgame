/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/26
 */

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * An abstract class representing a block, the basic unit of the world.
 *
 * @author Harit Kapadia, Jack Farley
 */
public abstract class Block implements Drawable {
	Image image;
	boolean solid;

	Block(File file, boolean solid) {
		this.solid = solid;
		try {
			image = ImageIO.read(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Image getImage() {
		return image;
	}

	public boolean isSolid() {
		return solid;
	}
}
