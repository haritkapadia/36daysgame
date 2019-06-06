/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Default block, used in world generation
 */
public class BlockGround extends Block {
        
        /**Constructor*/
        BlockGround() {
                super("Artwork/grass_small.png");
        }
        
        /** 
          * @returns True if the background of the image is transparent 
          */
        public boolean isTransparent() {
                return false;
        }
        
        /**
         * @returns True if the block can be walked on
         */
        public boolean isSolid() {
                return true;
        }
}
