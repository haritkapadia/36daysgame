import java.util.Random;
/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemMushroom extends FoodItem {
        ItemMushroom() {
                super("Artwork/mushroom_small.png", "Mushroom", 0,0);
        }
        
        @Override
        public void use(Entity e, World w, double x, double y, double z){
                if(new Random(w.getSeed()).nextBoolean())
                        e.takeDamage(2);
                e.eatFood(1);
        }
}
