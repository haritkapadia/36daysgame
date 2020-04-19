import java.util.Random;
/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates a generic mushroom item which can be eaten but may be poisonous
 */
public class ItemMushroom extends FoodItem {
        
        /**
         * The class constructor, calls the super constructor
         */
        ItemMushroom() {
                super("img/mushroom_small.png", "Mushroom", 0,0);
        }
        
        /**
         * Overrides the use method from the FoodItem class so that the mushroom is sometimes poisonous and sometimes not depending on the world seed
         * 
         * @param e The player eating the mushroom
         * @param w The world where the player is
         * @param x The x coordinate of the player
         * @param y The y coordinate of the player
         * @param z The z coordinate of the player
         */
        @Override
        public boolean use(Entity e, World w, double x, double y, double z){
                if(new Random(w.getSeed()).nextBoolean())
                        e.takeDamage(2);
                e.eatFood(1);
                return true;
        }
}
