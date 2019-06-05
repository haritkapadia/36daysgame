/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemFlint extends Item {
	ItemFlint() {
		super("Artwork/flint_small.png", "Flint");
	}

	public boolean use(Entity e, World w, double x, double y, double z) {
		e.takeDamage(1);
		return true;
	}
}
