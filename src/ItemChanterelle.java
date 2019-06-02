public class ItemChanterelle extends Item {
        ItemChanterelle() {
                super("Artwork/chanterelle_small.png", "Chanterelle");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
