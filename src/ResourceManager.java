public final class ResourceManager {
	private static Block[] blocks = {
		null,
		new BlockGround(),
		new BlockWater(),
		new BlockPoison(),
		new BlockTree(),
		new BlockHogweed()
	};

	public static Block getBlock(int i) {
		return blocks[i];
	}
}
