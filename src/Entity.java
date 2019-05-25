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
	protected Dimension2D dimension;
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
		SPEED = speed;
		health = 10;
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

	public Dimension2D getDimension() {
		return dimension;
	}

	public void interact(Block block) {
		block.interact(this);
	}

	public Direction getFacing() {
		return facing;
	}

	public void setFacing(Direction facing) {
		this.facing = facing;
	}

	public abstract void useHand();
}
