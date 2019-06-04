/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemDestroyingAngel extends Item {
        ItemDestroyingAngel() {
                super("Artwork/destroyingangel_small.png", "Destroying Angel");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
