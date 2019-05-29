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
	protected Direction facing;
	protected Point2D dimension;
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
		dimension = new Point2D(1, 1);
		SPEED = speed;
		health = 6;
		MAX_HEALTH = 10;
		stomachFullness = 10;
		MAX_STOMACH = 10;
		facing = Direction.DOWN;
	}
	public double getSpeed() {
		return SPEED;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public Point2D getDimension() {
		return dimension;
	}

	public int getHealth() {
		return health;
	}

	public int getMaximumHealth() {
		return MAX_HEALTH;
	}

	public void interact(Interactable interactable) {
		interactable.onInteract(this);
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

	public double getWidth() {
		return dimension.getX();
	}

	public double getHeight() {
		return dimension.getY();
	}

	public abstract void useHand();
}
