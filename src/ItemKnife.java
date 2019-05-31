public class ItemKnife extends Item {
	ItemKnife() {
		super("Artwork/knife_small.png", "Butter Knife (Harmless)");
	}

	public void use(Entity e, World w, double x, double y, double z) {
		e.takeDamage(e, 1);
	}
}
