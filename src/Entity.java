import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;

public abstract class Entity extends Transition implements Drawable {
	protected Point2D position;
	protected double radius;
	protected Direction facing;
	protected int health;
	protected final int MAX_HEALTH;
	protected int stomachFullness;
	protected final int MAX_STOMACH;
	public static final double STOMACH_REDUCTION_TIME = 1;
	protected World world;
	protected Item hand;
	protected final double SPEED;

	public Entity(World world) {
		this(world, 10);
	}
	public Entity(World world, double speed) {
		this.world = world;
		position = new Point2D(0, 0);
		radius = 0.5;
		SPEED = speed;
		health = 10;
		MAX_HEALTH = 10;
		stomachFullness = 10;
		MAX_STOMACH = 10;
		facing = Direction.DOWN;
		hand = new ItemKnife();
	}
	public double getSpeed() {
		return SPEED;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = Main.point2d(position);
	}

	public double getRadius() {
		return radius;
	}

	public int getHealth() {
		return health;
	}

	public int getMaximumHealth() {
		return MAX_HEALTH;
	}

	public void takeDamage(Entity e, int damage) {
		health -= damage;
	}

	public void interact(int x, int y, int z) {
		world.interactBlock(this, x, y, z);
	}

	public Direction getFacing() {
		return facing;
	}

	public void setFacing(Direction facing) {
		this.facing = facing;
	}

	public double getX() {
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}

	public void useHand() {
		hand.use(this, world, getX(), getY(), 1);
	}
}
