public class BlockPoison extends Block implements Interactable {
	BlockPoison() {
		super("Artwork/poison_small.png");
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isSolid() {
		return false;
	}

	public void onInteract(Entity e, World world, int x, int y, int z) {
		synchronized(this) {
			notifyAll();
		}
	}
}
