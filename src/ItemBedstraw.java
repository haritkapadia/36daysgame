public class ItemBedstraw extends FoodItem {
        ItemBedstraw() {
                super("Artwork/bedstraw_small.png", "Bedstraw");
        }
        
        public void use(Entity e, World w, double x, double y, double z) {
                e.takeDamage(e, 1);
        }
}
