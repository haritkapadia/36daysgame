public class ItemFlyAgaric extends Item {
        ItemFlyAgaric() {
                super("Artwork/flyagaric_small.png", "Fly Agaric");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
