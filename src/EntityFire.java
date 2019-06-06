/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/26
 */

import javafx.scene.image.Image;

/**
 * Runs the fire thread
 *
 * Variables:
 *
 * x,y,z     -The x,y, and z coordinates of the fire respectively
 * w         -The game world that the fire is part of
 * fuel      -The amount of fuel that the fire has left
 * fireID    -The fire's position in the fires ArrayList
 * e         -The entity that the fire effects
 */
public class EntityFire extends Entity {
	Stopwatch burnTracker;
	/**
	 * Class constructor, initializes all the variables
	 * @param e is the Entity that is affected by this object
	 * @param w is the game world that this object is part of
	 * @param x is the x coordinate of the fire
	 * @param y is the y coordinate of the fire
	 * @param z is the z coordinate of the fire
	 * @param n is the fire's number within the fires ArrayList
	 */
	EntityFire(String id, World w, int x, int y){
		super(id, new ItemKey[0], new Point2D(x, y), 0.5, Direction.DOWN, 10, 10, 0, 0, 0, 0, 0, 0, w, 0);
		// health, max, hunger, max, exposure, max, thirst, max
		burnTracker = new Stopwatch(0);
		burnTracker.start();
	}

	/**
	 * Makes the fire burn
	 */
	public void exist(){
		if(burnTracker.getElapsed() > 1e9)
			takeDamage(1);
		for (double wx = getX()-1; wx<= getX()+1; wx++) {
			for (double wy = getY()-1; wy <= getY()+1; wy++) {
				Point _b = World.getBlockCoordinate(new Point2D(wx, wy));
				BlockKey b = w.getBlock(_b.getX(), _b.getY(), 1);
				if (b != null && (b == BlockKey.WOOD || b == BlockKey.TREE)) {
					world.startFire(_b);
				}
			}
		}
		for(Entity e : world.getEntities()) {
			if(!(e instanceof EntityFire) && getPosition().distance(e.getPosition()) < getRadius() + e.getRadius())
				e.takeDamage(1);
		}
	}
}
