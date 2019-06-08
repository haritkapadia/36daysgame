/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates a water purification tablet item which can be used to make fires
 * 
 * Variables:
 * 
 * amountRemaining      -Stores the number of tablets left
 * consumable           -True if the item is one use only, false if otherwise
 */
public class ItemWaterPurificationTablets extends Item {
        private int amountRemaining;
        private boolean consumable;
        
        /**
         * Class constructor, calls the super constructor
         */
        ItemWaterPurificationTablets() {
                super("Artwork/pillbottle.png", "Water Purification Tablets");
                amountRemaining = 15;
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
                if (w.getBlock((int)x, (int)y, (int)z)!= null && w.getBlock((int)x,(int)y,(int)z).equals(BlockKey.WATERBOTTLE) && amountRemaining > 0){
                        ((ItemWaterBottle)ResourceManager.getItem(ItemKey.WATERBOTTLE)).purifyWater();
                        amountRemaining--;
                        
                        if(amountRemaining==1){
                                consumable=true;
                        }
                        System.out.println(amountRemaining);
                        return true;
                }
                return false;
        }
        
        /**
         * @returns False since this item can be used multiple times
         */
        @Override
        public boolean isConsumable(){
                return consumable;
        }
}
