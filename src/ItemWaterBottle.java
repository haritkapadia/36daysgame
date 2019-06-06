/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Water bottle item, used to store water
 * 
 */
public class ItemWaterBottle extends Item {
        private int amountRemaining;
        private final int MAX_AMOUNT;
        /**
         * Class constructor, calls the Item constructor using the water bottle image file
         */
        ItemWaterBottle() {
                super("Artwork/emptywaterbottle.png", "Water Bottle");
                amountRemaining = 0;
                MAX_AMOUNT = 10;
        }
        
        /**
         * @returns false since the water bottle can be used multiple times
         */
        public boolean isConsumable() {
                return false;
        }
        
        /**
         * Fills the water bottle to the maximum amount it can hold
         */
        public void fillWaterbottle(){
                amountRemaining = MAX_AMOUNT;
        }
        
        /**
         * Updates the water bottle image
         */
        public void updateWaterBottle(){
                if (amountRemaining > 0){
                        setImage("Artwork/waterbottle.png");
                }else if (amountRemaining == 0)
                        setImage("Artwork/emptywaterbottle.png");
        }
        
        /**
         * Makes the entity drink from the water bottle
         * @returns true if the item was used succesfully
         * 
         * @param e is the entity that uses the item
         * @param w is the World in which the entity is located
         * @param x is the x coordinate of the mouse
         * @param y is the y coordinate of the mouse
         * @param z is the z coordinate of the entity
         */
        public boolean use(Entity e, World w, double x, double y, double z) {
                if (w.getBlock((int)x, (int)y, (int)z)!= null && w.getBlock((int)x,(int)y,(int)z).equals(BlockKey.WATER)){
                        amountRemaining = MAX_AMOUNT;
                        w.setBlockUnsafe((int)x, (int)y, (int)z, null);
                        synchronized(this) {
                                notifyAll();
                        }
                }else if(amountRemaining>0){
                        amountRemaining--;
                }
                updateWaterBottle();
                return true;
        }
}
