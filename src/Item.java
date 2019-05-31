import javafx.scene.image.Image;

public abstract class Item implements Drawable {
	Image image;

	Item(String file) {
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

	public abstract void use(Entity e, World w, double x, double y, double z);
}
