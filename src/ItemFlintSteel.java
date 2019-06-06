/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class ItemFlintSteel extends Item {

	ItemFlintSteel() {
		super("Artwork/flintsteel_small.png", "Flint and Steel");
	}

	public boolean isConsumable() {
		return false;
	}

	public boolean use(Entity e, World w, double x, double y, double z) {
		BlockKey b = w.getBlock((int)x,(int)y,(int)z);
		if(b != null && b == BlockKey.WOOD && !((int)e.getX() == (int)x && (int)e.getY() == (int)y)){
			w.setBlockUnsafe((int)x, (int)y, (int)z, null);
			w.setBlock((int)x,(int)y,(int)z,BlockKey.FIRE);
			w.fires.add(new Fire(e, w,(int)x,(int)y,(int)z, w.fires.size()));
			synchronized(this) {
				notifyAll();
			}
			return true;
		}
		return false;
	}
}
