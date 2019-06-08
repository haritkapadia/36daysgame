/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates a water block
 */
public class BlockWater extends Block {
        
        /**Constructor. calls the superclass constructor*/
        BlockWater() {
                super("Artwork/water_small.png");
        }
        
        /**
         * @returns False since the block does not have a transparent background
         */
        public boolean isTransparent() {
                return false;
        }
        
        /**
         * @returns True since the player can't walk through this block
         */
        public boolean isSolid() {
                return true;
        }
}
