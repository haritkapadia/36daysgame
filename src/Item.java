/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import javafx.scene.image.Image;

public abstract class Item implements Drawable {
	Image image;
	String name;

	Item(String file, String name) {
		this.name = name;
		try {
			image = new Image(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Image getImage() {
		return image;
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isConsumable() {
		return true;
	}

	public String getName() {
		return name;
	}

	public boolean use(Entity e, World w, double x, double y, double z) {
		return true;
	}
}
