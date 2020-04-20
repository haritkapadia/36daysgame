package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import javafx.scene.image.Image;
/**
 * Superclass for all the items in the game, contains useful methods and abstract classes that all items must implement
 * 
 * Variables
 * 
 * image     -Stores the item image
 * name      -Stores what the item is called
 */
public abstract class Item implements Drawable {
        private Image image;
        private String name;
        
        /**
         * Class constructor, initializes all variables
         * @param file is the file name
         * @param name is the item name
         */
        Item(String file, String name) {
                this.name = name;
                try {
                    image = new Image(Item.class.getResource("/" + file).toExternalForm());
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }
        
        /**
         * @returns the item's image
         */
        public Image getImage() {
                return image;
        }
        
        /**
         * Sets the item's image file to a new image
         * @param file is the file name of the new image
         */
        public void setImage(String file){
                try{
                        image = new Image(file);
                }catch(Exception e){
                        e.printStackTrace();
                }
        }
        
        /**
         * @returns true if the item has a transparent background
         */
        public boolean isTransparent() {
                return true;
        }
        
        /**
         * @returns true if the item is consummable (the item disappears after one use)
         */
        public boolean isConsumable() {
                return true;
        }
        
        /**
         * @returns the name of the item
         */
        public String getName() {
                return name;
        }
        
        /**
         * Abstract method, all items must be usable
         */
        public abstract boolean use(Entity e, World w, double x, double y, double z);
}
