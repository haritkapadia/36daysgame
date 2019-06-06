/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Knife item, used to make flint and steel
 */
public class ItemKnife extends Item {
        /**
         * Class constructor, calls the Item constructor using the knife image file
         */
        ItemKnife() {
                super("Artwork/knife_small.png", "Butter Knife (Harmless)");
        }
        
        /**
         * @returns false since the knife can be used multiple times
         */
        public boolean isConsumable() {
                return false;
        }
        
        /**
         * Makes the entity use the knife, but since they aren't using it on anything they damage themselves
         * @returns true if the item was used succesfully
         * 
         * @param e is the entity that uses the item
         * @param w is the World in which the entity is located
         * @param x is the x coordinate of the mouse
         * @param y is the y coordinate of the mouse
         * @param z is the z coordinate of the entity
         */
        public boolean use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(1);
                return true;
        }
}
