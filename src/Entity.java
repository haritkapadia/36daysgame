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
import java.awt.Point;

/**
 * The superclass for player, contains all of the player's statistics and methods to access them
 *
 * Variables
 *
 * id          -A String that stores the identity of the entity
 * inventory   -Stores all fo the items in the entity's inventory
 * position    -Stores the entity's position
 * radius      -Stores the size of the entity
 * facing      -Stores the direction that the entity is facing
 * health      -Stores the entity's health amount
 * MAX_HEALTH  -Stores the amount that the health is out of
 * hunger      -Stores how full the entity is
 * MAX_HUNGER  -Stores the amount that the hunger bar is out of
 * thirst      -Stores how quenched the entity is
 * MAX_THIRST  -Stores the amount that the thirst bar is out of
 * STOMACH_REDUCTION_TIME -Stores how quickly the entity gets hungry
 * world       -A reference to the game world object
 * SPEED       -Stores how fast the entity moves
 */
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
	protected int exposure;
	protected int MAX_EXPOSURE;
	protected int thirst;
	protected int MAX_THIRST;
	public static final double STOMACH_REDUCTION_TIME = 1;
	protected World world;
	protected double SPEED;




	public Entity(String id, ItemKey[] inventory, Point2D position, double radius, Direction facing, int health, int MAX_HEALTH, int hunger, int MAX_HUNGER, int exposure, int MAX_EXPOSURE, int thirst, int MAX_THIRST, World world, double SPEED) {
		this.id = id;
		this.inventory = inventory;
		this.position = position;
		this.radius = radius;
		this.facing = facing;
		this.health = health;
		this.MAX_HEALTH = MAX;
		this.hunger = hunger;
		this.MAX_HUNGER = MAX;
		this.exposure = exposure;
		this.MAX_EXPOSURE = MAX;
		this.thirst = thirst;
		this.MAX_THIRST = MAX;
		this.world = world;
		this.SPEED = SPEED;
	}

	// /*
	//  * Overrides the abstract superclass method
	//  */
	// public void interpolate(double d);

	/**
	 * Overloaded constructor
	 * @param world is a reference to the game world
	 */
	public Entity(World world) {
		this(world, 10);
	}

	/**
	 * Overloaded constructor
	 * @param world is a reference to the game world
	 * @param speed is how fast the entity can move
	 */
	public Entity(World world, double speed) {
		this.world = world;
		position = new Point2D(0, 0);
		radius = 0.5;
		SPEED = speed;
		health = 10;
		MAX_HEALTH = 10;
		hunger = 10;
		MAX_HUNGER = 10;
		exposure = 20;
		MAX_EXPOSURE =20;
		thirst = 20;
		MAX_THIRST = 20;
		inventory = new ItemKey[15];
		facing = Direction.DOWN;
	}

	public void exist();

	/**
	 * Sets the value of a slot in the inventory
	 * @param i is the slot number
	 * @param k is the item that is to be placed there
	 */
	public void setInventory(int i, ItemKey k) {
		inventory[i] = k;
	}

	/**
	 * @returns the item at the given inventory slot number
	 * @param i is the inventory slot number
	 */
	public ItemKey getInventory(int i) {
		return inventory[i];
	}

	/**
	 * @returns the entity's inventory
	 */
	public ItemKey[] getInventory() {
		return inventory;
	}

	/**
	 * @returns the entity's speed
	 */
	public double getSpeed() {
		return SPEED;
	}

	/**
	 * @returns the entity's position
	 */
	public Point2D getPosition() {
		return position;
	}

	/**
	 * Sets the entity's position to a new value
	 * @param position the new position
	 */
	public void setPosition(Point2D position) {
		this.position = Main.point2d(position);
	}

	/**
	 * @returns the entity's size
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @returns the entity's identity
	 */
	public String getID() {
		return id;
	}

	/**
	 * @returns the entity's health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @returns the amount that the entity's health is out of
	 */
	public int getMaximumHealth() {
		return MAX_HEALTH;
	}

	/**
	 * @returns the entity's hunger status
	 */
	public int getHunger(){
		return hunger;
	}

	/**
	 * @returns the entity's exposure status
	 */
	public int getExposure(){
		return exposure;
	}

	/**
	 * @returns the amount of cold that the player can withstand
	 */
	public int getMaxExposure(){
		return MAX_EXPOSURE;
	}

	/**
	 * @returns the entity's thirst status
	 */
	public int getThirst(){
		return thirst;
	}

	/**
	 * @returns how much the user needs to drink
	 */
	public int getMaxThirst(){
		return MAX_THIRST;
	}

	/**
	 * @returns how much food the entity needs
	 */
	public int getMaxHunger(){
		return MAX_HUNGER;
	}

	/**
	 * Changes the hunger by a given amount
	 * @param foodWorth is the nutritional value of what has been eaten
	 */
	public void eatFood(int foodWorth){
		if(hunger + foodWorth <= MAX_HUNGER)
			hunger += foodWorth;
		else
			hunger = MAX_HUNGER;
	}

	/**
	 * Changes the health by a given amount
	 * @param damage is the amount that the health is to be changed by
	 */
	public void takeDamage(int damage) {
		if (health - damage <= MAX_HEALTH)
			health -= damage;
		else
			health = MAX_HEALTH;
	}

	/**
	 * Changes the exposure by a given amount, if the player gets too cold they die
	 * @param n the amount that the exposure is to be changed by
	 */
	public void lowerExposure (int n){
		if (exposure - n <= MAX_EXPOSURE)
			exposure -= n;
		else
			exposure = MAX_EXPOSURE;
	}

	/**
	 * Makes the entity interact with a block
	 * @param x is the x coordinate of the block
	 * @param y is the y coordinate of the block
	 * @param z is the z coordinate of the block
	 * @param b is the type of block
	 */
	public void interact(int x, int y, int z) {
		world.interactBlock(this, x, y, z);
	}

	/**
	 * Places a block
	 * @returns true if placing a block was successful
	 * @param x is the x coordinate of the block
	 * @param y is the y coordinate of the block
	 * @param z is the z coordinate of the block
	 * @param b is the type of block
	 */
	public boolean placeBlock(int x, int y, int z, BlockKey b) {
		return world.setBlock(x, y, z, b);
	}

	/**
	 * @returns the direction that the entity is facing
	 */
	public Direction getFacing() {
		return facing;
	}

	/**
	 * Sets the direction that the entity is facing
	 * @param facing is the new direction
	 */
	public void setFacing(Direction facing) {
		this.facing = facing;
	}

	/**
	 * @returns the x coordinate of the entity
	 */
	public double getX() {
		return position.getX();
	}

	/**
	 * @returns the y coordinate of the entity
	 */
	public double getY() {
		return position.getY();
	}

	/**
	 * Makes the entity use a tool
	 * @param i is the inventory slot containing the tool
	 * @param target is where the tool is to be used
	 */
	public void useTool(int i, Point2D target) {
		if(inventory[i] != null) {
			Item item = ResourceManager.getItem(inventory[i]);
			boolean used = item.use(this, world, target.getX(), target.getY(), 1);
			if(item.isConsumable() && used)
				inventory[i] = null;
		}
	}

	/**
	 * Makes the entity drop an item
	 * @param i is the inventory slot containing the item
	 */
	public void dropItem(int i) {
		if(inventory[i] != null) {
			Point blockPos = World.blockCoordinate(position);
			BlockKey block = world.getBlock((int)blockPos.getX(), (int)blockPos.getY(), 1);
			if(block == null || ResourceManager.getBlock(block) instanceof InsectBlock) {
				world.setBlock((int)blockPos.getX(), (int)blockPos.getY(), 1, ResourceManager.getPortableBlock(inventory[i]));
				inventory[i] = null;
			}else if (nearestFreeBlock() != null){
				blockPos = nearestFreeBlock();
				world.setBlock((int)blockPos.getX(), (int)blockPos.getY(), 1, ResourceManager.getPortableBlock(inventory[i]));
				inventory[i] = null;
			}
		}
	}

	/**
	 * Finds the nearest free block on which to drop an item
	 * @returns the coordinates of the nearest free block
	 */
	public Point nearestFreeBlock(){
		for (int row = (int)World.blockCoordinate(position).getY() - 1; row <= (int)World.blockCoordinate(position).getY()+1; row++){
			for(int col = (int)World.blockCoordinate(position).getX()-1; col <= (int)World.blockCoordinate(position).getX()+1; col++){
				if(world.getBlock(col, row, 1)==null || ResourceManager.getBlock(world.getBlock(row, col, 1)) instanceof InsectBlock)
					return new Point(col, row);
			}
		}
		return null;
	}

	/**
	 * @returns the entity represented as a string to be saved to a file
	 */
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
		out += "exposure\t"+exposure + "\n";
		out += "MAX_EXPOSURE\t" + MAX_EXPOSURE + "\n";
		out += "thirst\t"+thirst+"\n";
		out += "MAX_THIRST\t"+MAX_THIRST+"\n";
		out += "SPEED\t" + SPEED + "\n";
		return out;
	}

	/**
	 * Constructor used to create an entity and load its stats from a file
	 * @param world is the game world
	 * @param s is the Sting object containing the entity's information
	 * @see getAsString()
	 */
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
					if(items[i].trim().equals("null")){
						inventory[i] = null;
					}
					else{
						inventory[i] = ItemKey.valueOf(items[i].trim());
					}
				}
			} else if(L[0].equals("position")) {
				String[] coordinates = L[1].split(", ");
				position = new Point2D(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
			} else if(L[0].equals("radius")) {
				radius = Double.parseDouble(L[1]);
			} else if(L[0].equals("facing")) {
				facing = Direction.valueOf(L[1].trim());
			} else if(L[0].equals("health")) {
				health = Integer.parseInt(L[1].trim());
			} else if(L[0].equals("MAX_HEALTH")) {
				MAX_HEALTH = Integer.parseInt(L[1].trim());
			} else if(L[0].equals("stomachFullness")) {
				hunger = Integer.parseInt(L[1].trim());
			} else if(L[0].equals("MAX_STOMACH")) {
				MAX_HUNGER = Integer.parseInt(L[1].trim());
			} else if(L[0].equals("SPEED")) {
				SPEED = Double.parseDouble(L[1].trim());
			}else if (L[0].equals("exposure")){
				exposure = Integer.parseInt(L[1].trim());
			}else if (L[0].equals("MAX_EXPOSURE")){
				MAX_EXPOSURE = Integer.parseInt(L[1].trim());
			}else if (L[0].equals("thirst")){
				thirst = Integer.parseInt(L[1].trim());
			}else if (L[0].equals("MAX_THIRST")){
				MAX_THIRST = Integer.parseInt(L[1].trim());
			}
		}
	}

	/**
	 * @returns true if the entity has a transparent background
	 */
	public boolean isTransparent() {
		return true;
	}

	/**
	 * @returns the entity's image
	 */
	public Image getImage() {
		return null;
	}
}
