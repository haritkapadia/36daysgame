public class BlockTree extends Block {
	BlockTree() {
		super("Artwork/tree_small.png");
	}

	public boolean isTransparent() {
		return true;
	}

	public boolean isSolid() {
		return true;
	}
}
