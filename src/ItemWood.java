/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates a wood item which can be used to make shelters
 */
public class ItemWood extends Item {
        
        /**
         * The class constructor, calls the super constructor
         */
        ItemWood() {
                super("Artwork/wood_small.png", "Wood");
        }
        
        /**
         * Places a block where the user's mouse is
         * 
         * @returns True if the placement was succesful
         * 
         * @param e The player
         * @param w The world where the player is
         * @param x The x coordinate of the user's mouse
         * @param y The y coordinate of the user's mouse
         * @param z The z coordinate of the player
         */
        public boolean use(Entity e, World w, double x, double y, double z) {
                return e.placeBlock((int)x, (int)y, (int)z, BlockKey.TREE);
        }
}
