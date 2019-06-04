/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

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
	protected ItemKey[] inventory;
	protected Point2D position;
	protected double radius;
	protected Direction facing;
	protected int health;
	protected final int MAX_HEALTH;
	protected int stomachFullness;
	protected final int MAX_STOMACH;
	public static final double STOMACH_REDUCTION_TIME = 1;
	protected World world;
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
		inventory = new ItemKey[15];
		facing = Direction.DOWN;
	}

	public void setInventory(int i, ItemKey k) {
		inventory[i] = k;
	}

	public ItemKey getInventory(int i) {
		return inventory[i];
	}

	public ItemKey[] getInventory() {
		return inventory;
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

	public void placeBlock(int x, int y, int z, BlockKey b) {
		world.setBlock(x, y, z, b);
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

	public void useTool(int i, Point2D target) {
		if(inventory[i] != null)
			ResourceManager.getItem(inventory[i]).use(this, world, target.getX(), target.getY(), 1);
	}
}
