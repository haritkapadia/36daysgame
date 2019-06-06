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
        
        Block(String file) {
                try {
                        image = new Image(file);
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }
        
        public Image getImage() {
                return image;
        }
        
        public abstract boolean isSolid();
}
