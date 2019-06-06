/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates a flint item which can be used to make fires
 */
public class ItemFlint extends Item {
        /**
         * Class constructor, calls the item constructor using the flint image file
         */
        ItemFlint() {
                super("Artwork/flint_small.png", "Flint");
        }
        
        /**
         * Overrides the abstract method use in the Item class
         * 
         * @param e is the Entity that is using the item
         * @param w is the World where the entity is
         * @param x is the x-coordinate of the mouse
         * @param y is the y-coordinate of the mouse
         * @param z is the z coordinate of the mouse
         * 
         */
        public boolean use(Entity e, World w, double x, double y, double z) {
                if (w.getBlock((int)x, (int)y, (int)z)!= null && w.getBlock((int)x,(int)y,(int)z).equals(BlockKey.KNIFE)){
                        w.setBlockUnsafe((int)x, (int)y, (int)z, null);
                        w.setBlock((int)x,(int)y,(int)z, BlockKey.FLINTSTEEL);
                        synchronized(this) {
                                notifyAll();
                        }
                        return true;
                }
                return false;
        }
}
