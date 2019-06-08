/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemCattail extends Item {
	ItemCattail() {
		super("Artwork/cattail_small.png", "Cattail");
	}

	public boolean use(Entity e, World w, double x, double y, double z) {
		return e.placeBlock((int)x, (int)y, (int)z, BlockKey.BED);
	}
}
