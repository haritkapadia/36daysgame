/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemChanterelle extends Item {
        ItemChanterelle() {
                super("Artwork/chanterelle_small.png", "Chanterelle");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
