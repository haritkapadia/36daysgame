/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemKnife extends Item {
	ItemKnife() {
		super("Artwork/knife_small.png", "Butter Knife (Harmless)");
	}

	@Override
	public boolean isConsumable() {
		return false;
	}

	public boolean use(Entity e, World w, double x, double y, double z) {
		e.takeDamage(e, 1);
		return true;
	}
}
