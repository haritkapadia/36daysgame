public class BlockWater extends Block {
	BlockWater() {
		super("Artwork/water_small.png");
	}

	public boolean isTransparent() {
		return false;
	}

	public boolean isSolid() {
		return true;
	}
}
