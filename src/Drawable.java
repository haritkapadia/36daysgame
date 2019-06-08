/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import javafx.scene.image.Image;

/**
 * An interface implemented by all things that can be drawn
 */
public interface Drawable {
        
        /**
         * @returns The image of the object, used to display it on screen
         */
        public Image getImage();
        
        /**
         * @returns Whether or not the object has a transparent background
         */
        public boolean isTransparent();
}
