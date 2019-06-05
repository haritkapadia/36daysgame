/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemWood extends Item {
	ItemWood() {
		super("Artwork/wood_small.png", "Wood");
	}

	public boolean use(Entity e, World w, double x, double y, double z) {
		return e.placeBlock((int)x, (int)y, (int)z, BlockKey.TREE);
	}
}
