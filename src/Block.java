/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/26
 */

import javafx.scene.image.Image;

/**
 * An abstract class representing a block, the basic unit of the world.
 * 
 * Variables:
 * 
 * image     -Stores the image file for the block
 * 
 * 
 * @author Harit Kapadia, Jack Farley
 */
public abstract class Block implements Drawable {
        Image image;
        
        /**
         * Class constructor, initializes the image variable
         * 
         * @param file stores the image file
         */
        Block(String file) {
                try {
                        image = new Image(file);
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }
        
        /**
         * @returns the block's image
         */
        public Image getImage() {
                return image;
        }
        
        /**
         * @returns true if the block can't be walked on
         */
        public abstract boolean isSolid();
}
