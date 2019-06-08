/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/26
 */

import javafx.scene.image.Image;

/**
 * Creates a fire block
 */
public class BlockFire extends Block {
        /**
         * Class constructor, calls the Block constructor using the fire image
         */
        BlockFire(){
                super("Artwork/fire_small.png");
        }
        
        /**
         * Returns true if the fire can't be walked on
         */
        public boolean isSolid(){
                return true;
        }
        
        /**
         * Returns true if the object has a transparent background
         */
        public boolean isTransparent(){
                return true;
        }
}
