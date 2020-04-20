package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates a tree block
 */
public class BlockBed extends Block implements Interactable {

	/**
	 * Class constructor, calls the block constructor
	 */
	BlockBed() {
		super("img/bed_small.png");
	}

	/**
	 * @returns true if the block has a transparent background
	 */
	public boolean isTransparent() {
		return true;
	}

	/**
	 * Overrides the onDestroy method of the Destroyable class
	 * Splits the tree into 4 wood objects
	 * @param world is a reference to the game world object
	 * @param x is the tree's x coordinate
	 * @param y is the tree's y coordinate
	 * @param z is the tree's z coordinate
	 */
	public void onInteract(Entity e, World world, int x, int y, int z) {
		int count = 0;
		for(int i = x - 1; i <= x + 1; i++)
			for(int j = y - 1; j <= y + 1; j++)
				if(world.getBlock(i, j, z) == BlockKey.PALISADE)
					count++;
		System.out.println("palisade count: " + count);

		if(count >= 7) {
			world.getStopwatch().getElapsed();
			world.getStopwatch().setSpeed(300);
			e.setInvincible(true);
			try {
				Thread.sleep(1000);
			}
			catch (Throwable f) {
				System.out.println("Error " + f.getMessage());
				f.printStackTrace();
			}
			world.getStopwatch().getElapsed();
			world.getStopwatch().setSpeed(1);
			world.getStopwatch().getElapsed();
			for(int i = 0; i < 30; i++)
				e.exist();
			e.setInvincible(false);
		}
	}

	/**
	 * @returns how close the player must be to interact with the block
	 */
	public double getInteractRadius() {
		return 1;
	}

	/**
	 * @returns true if the object can't be walked through
	 */
	public boolean isSolid() {
		return false;
	}
}
