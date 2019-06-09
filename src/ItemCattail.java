/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates a cattail item which can be made into a bed
 */
public class ItemCattail extends Item {
        /**
         * Class constructor, calls the super constructor
         */
        ItemCattail() {
                super("Artwork/cattail_small.png", "Cattail");
        }
        
        /**
         * Places a bed block
         * @returns True if successful
         * @param e The entity using the item
         * @param w The World where the entity is
         * @param x The x coordinate of the mouse
         * @param y The y coordinate of the mouse
         * @param z The z coordinate of the player
         */
        public boolean use(Entity e, World w, double x, double y, double z) {
                if (e.placeBlock((int)x, (int)y, (int)z, BlockKey.BED)==true){
                        synchronized(this) {
                                notifyAll();
                        }
                }
                        return true;
        }
}
