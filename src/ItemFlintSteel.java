/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * This item is used in the game to light fires
 */
public class ItemFlintSteel extends Item {
        
        /**
         * Calls the Item constructor with the name of the item and the file name for the flint and steel image
         */
        ItemFlintSteel() {
                super("Artwork/flintsteel_small.png", "Flint and Steel");
        }
        
        /**
         * @returns false since the item can be used multiple times
         */
        public boolean isConsumable() {
                return false;
        }
        
        /**
         * @returns true if the item was succesfully used
         * @param e is the entity that uses the item
         * @param w is the World in which the entity is located
         * @param x is the x coordinate of the mouse
         * @param y is the y coordinate of the mouse
         * @param z is the z coordinate of the entity
         */
        public boolean use(Entity e, World w, double x, double y, double z) {
                if(w.getBlock((int)x,(int)y,(int)z).equals(BlockKey.WOOD) && !((int)e.getX() == (int)x && (int)e.getY() == (int)y)){
                        w.setBlockUnsafe((int)x, (int)y, (int)z, null);
                        w.setBlock((int)x,(int)y,(int)z,BlockKey.FIRE);
                        w.fires.add(new Fire(e, w,(int)x,(int)y,(int)z, w.fires.size()));
                        synchronized(this) {
                                notifyAll();
                        }
                        return true;
                }
                return false
        }
}
