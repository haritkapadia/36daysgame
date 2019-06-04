/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemBedstraw extends FoodItem {
        ItemBedstraw() {
                super("Artwork/bedstraw_small.png", "Bedstraw");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
