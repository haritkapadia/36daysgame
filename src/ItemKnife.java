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
         */
        public boolean use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(1);
                return true;
        }
}
