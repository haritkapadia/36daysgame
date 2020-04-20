package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import javafx.scene.image.Image;

/**
 * Superclass for all insect blocks (ant, worm, grasshopper)
 * 
 * Variables:
 * 
 * image  -Stores a blank png file
 */
public class InsectBlock extends PortableBlock{
        private Image image;
        
        /**
         * Class constructor, calls the super class and initializes the image variables
         */
        public InsectBlock(String file, ItemKey equivalentItem){
                super (file, equivalentItem);
                try {
                        image = new Image(file);
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }
        
        /**
         * @returns The insect image
         */
        public Image getImage() {
                return image;
        }
}
