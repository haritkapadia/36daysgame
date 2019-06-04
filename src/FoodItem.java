/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class FoodItem extends Item {
	FoodItem(String file, String name){
		super(file, "???");
	}
	public boolean use(Entity e, World w, double x, double y, double z){
		return true;
	}
}
