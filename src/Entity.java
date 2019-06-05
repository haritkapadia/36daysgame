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
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;

public class Entity extends Transition implements Drawable {
	protected String id;
	protected ItemKey[] inventory;
	protected Point2D position;
	protected double radius;
	protected Direction facing;
	protected int health;
	protected int MAX_HEALTH;
	protected int hunger;
	protected int MAX_HUNGER;
	public static final double STOMACH_REDUCTION_TIME = 1;
	protected World world;
	protected double SPEED;

	public void interpolate(double d) {}

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
		hunger = 10;
		MAX_HUNGER = 10;
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

	public String getID() {
		return id;
	}

	public int getHealth() {
		return health;
	}

	public int getMaximumHealth() {
		return MAX_HEALTH;
	}

	public int getHunger(){
		return hunger;
	}

	public int getMaxHunger(){
		return MAX_HUNGER;
	}

	public void eatFood(int foodWorth){
		hunger += foodWorth;
	}

	public void takeDamage(int damage) {
		health -= damage;
	}

	public void interact(int x, int y, int z) {
		world.interactBlock(this, x, y, z);
	}

	public boolean placeBlock(int x, int y, int z, BlockKey b) {
		return world.setBlock(x, y, z, b);
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
		if(inventory[i] != null) {
			Item item = ResourceManager.getItem(inventory[i]);
			boolean used = item.use(this, world, target.getX(), target.getY(), 1);
			if(item.isConsumable() && used)
				inventory[i] = null;
		}
	}

	public String getAsString() {
		String out = "";
		String inventoryString = Arrays.toString(inventory);
		out += "id\t" + id + "\n";
		out += "inventory\t" + inventoryString.substring(1, inventoryString.length() - 1) + "\n";
		out += "position\t" + position.getX() + ", " + position.getY() + "\n";
		out += "radius\t" + radius + "\n";
		out += "facing\t" + facing + "\n";
		out += "health\t" + health + "\n";
		out += "MAX_HEALTH\t" + MAX_HEALTH + "\n";
		out += "stomachFullness\t" + hunger + "\n";
		out += "MAX_STOMACH\t" + MAX_HUNGER + "\n";
		out += "SPEED\t" + SPEED + "\n";
		return out;
	}

	Entity(World world, String s) {
		this.world = world;
		inventory = new ItemKey[15];
		String[] lines = s.split("\n");
		for(String l : lines) {
			final String[] L = l.split("\t");
			if(L[0].equals("id")) {
				id = L[1];
			} else if(L[0].equals("inventory")) {
				String[] items = L[1].split(", ");
				for(int i = 0; i < items.length; i++) {
					if(items[i].equals("null"))
						inventory[i] = null;
					else
						inventory[i] = ItemKey.valueOf(items[i]);
				}
			} else if(L[0].equals("position")) {
				String[] coordinates = L[1].split(", ");
				position = new Point2D(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			} else if(L[0].equals("radius")) {
				radius = Double.parseDouble(L[1]);
			} else if(L[0].equals("facing")) {
				facing = Direction.valueOf(L[1]);
			} else if(L[0].equals("health")) {
				health = Integer.parseInt(L[1]);
			} else if(L[0].equals("MAX_HEALTH")) {
				MAX_HEALTH = Integer.parseInt(L[1]);
			} else if(L[0].equals("stomachFullness")) {
				hunger = Integer.parseInt(L[1]);
			} else if(L[0].equals("MAX_STOMACH")) {
				MAX_HUNGER = Integer.parseInt(L[1]);
			} else if(L[0].equals("SPEED")) {
				SPEED = Double.parseDouble(L[1]);
			}
		}
	}

	public boolean isTransparent() {
		return true;
	}

	public Image getImage() {
		return null;
	}
}
