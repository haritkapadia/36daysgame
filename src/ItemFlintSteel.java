/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.awt.Point;
import javafx.geometry.Point2D;

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
                Point p = World.blockCoordinate(new Point2D(x, y));
                BlockKey b = w.getBlock((int)p.getX(), (int)p.getY(), 1);
                if(b != null && b == BlockKey.WOOD) {
                        w.setBlockUnsafe((int)p.getX(), (int)p.getY(), 1, null);
                        w.startFire(x, y);
                        synchronized(this) {
                                notifyAll();
                        }
                        return true;
                }
                return false;
        }
}
