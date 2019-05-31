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

	public String getName() {
		return name;
	}

	public abstract void use(Entity e, World w, double x, double y, double z);
}
