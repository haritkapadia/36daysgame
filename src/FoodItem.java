/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class FoodItem extends Item {
	final int RH = 1;
	final int RD = 1;
	private int hungerRestoration;
	private int healthDeduction;

	FoodItem(String file, String name, int hunger, int health){
		super(file, "???");
		hungerRestoration = hunger;
		healthDeduction = health;
	}

	public boolean use(Entity e, World w, double x, double y, double z){
		e.takeDamage(healthDeduction);
		e.eatFood(hungerRestoration);
		return true;
	}
}
