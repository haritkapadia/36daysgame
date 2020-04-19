/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * An interface implemented by the blocks that can be destroyed by the player
 */
public interface Destroyable {
        /**
         * This method is called when the block is destroyed
         */
        public void onDestroy(World world, int x, int y, int z);
}
