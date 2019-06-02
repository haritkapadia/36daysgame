public class ItemFlint extends Item {
        ItemFlint() {
                super("Artwork/flint_small.png", "Flint");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
