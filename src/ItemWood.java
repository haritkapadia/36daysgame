public class ItemWood extends Item {
        ItemWood() {
                super("Artwork/wood_small.png", "Wood");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
