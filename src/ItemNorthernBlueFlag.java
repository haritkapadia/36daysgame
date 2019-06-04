/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemNorthernBlueFlag
 extends Item {
        ItemNorthernBlueFlag() {
                super("Artwork/northernblueflag_small.png", "Northern Blue Flag");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
