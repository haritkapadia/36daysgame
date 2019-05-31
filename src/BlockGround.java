public class BlockGround extends Block {
	BlockGround() {
		super("Artwork/grass_small.png");
	}

	public boolean isTransparent() {
		return false;
	}

	public boolean isSolid() {
		return true;
	}
}
