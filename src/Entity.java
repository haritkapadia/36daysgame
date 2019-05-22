import java.awt.Point;

public abstract class Entity {
	Point position; // in 2^-6 blocks
	int health;
	int stomachFullness;
	int maxStomach;
	public static final int STOMACH_REDUCTION_TIME = 1000;
	World world;
	Item hand;
	public abstract void move();
	public abstract void useHand();
	public abstract void interact(Block block);

	// Entity(World world) {
	//	this.world = world;
	// }
}
