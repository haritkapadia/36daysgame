package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * The superclass for all of the items that the player can eat
 * 
 * Variables:
 * 
 * RH      -Stands for "relative hunger" and is the unit by which food items make the player less hungry
 * RD      -Stands for "relative damage" and is the unit by which food items kill the player
 * hungerRestoration     -The amount of RH units the food makes the player less hungry
 * healthDeduction       -The amount of RD units the food damages the player
 */
public class FoodItem extends Item {
        final int RH = 1;
        final int RD = 1;
        private int hungerRestoration;
        private int healthDeduction;
        
        /**
         * The class constructor, initializes the variables
         * @param file is the name of the image file
         * @param name is the name of the item
         * @param hunger is the amount of RH units the food adds to the hunger bar
         * @param health is the amount of RD units the food takes away from the health bar
         */
        FoodItem(String file, String name, int hunger, int health) {
                super(file, "???");
                hungerRestoration = hunger;
                healthDeduction = health;
        }
        
        /**
         * Makes the player eat the item
         * @param e is the Entity which eats the item
         * @param w is the world that the entity and item are in
         * @param x is the x coordinate of the player's mouse
         * @param y is the y coordinate of the player's mouse
         * @param z is the z coordinate of the player's mouse
         */
        public boolean use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(healthDeduction);
                e.eatFood(hungerRestoration);
                return true;
        }
}
