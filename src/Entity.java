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
import java.io.*;
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
 * world       -A reference to the game world object
 * SPEED       -Stores how fast the entity moves
 * invincible  -Stores whether or not the entity should take damage
 * damageWait  -Stores how long the entity has been waiting to take damage.
 * prevElapsed -Stores the last time the entity checked if damage had to be taken.
 */
public abstract class Entity extends Transition implements Drawable, Serializable {
        protected String id;
        protected ItemKey[] inventory;
        protected double x;
        protected double y;
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
        protected transient World world = null;
        protected double SPEED;
        protected boolean invincible = false;
        long damageWait;
        long prevElapsed;
        
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
                x = 0;
                y = 0;
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
        
        
        /**
         * Overloaded constructor
         * @param id The id of the Entity
         * @param inventory The inventory of the Entity
         * @param x The x of the Entity
         * @param y The y of the Entity
         * @param radius The radius of the Entity
         * @param facing The facing of the Entity
         * @param health The health of the Entity
         * @param MAX_HEALTH The MAX_HEALTH of the Entity
         * @param hunger The hunger of the Entity
         * @param MAX_HUNGER The MAX_HUNGER of the Entity
         * @param exposure The exposure of the Entity
         * @param MAX_EXPOSURE The MAX_EXPOSURE of the Entity
         * @param thirst The thirst of the Entity
         * @param MAX_THIRST The MAX_THIRST of the Entity
         * @param id The id of the Entity
         * @param world A reference to the game world
         * @param SPEED The SPEED of the Entity
         */
        public Entity(String id, ItemKey[] inventory, double x, double y, double radius, Direction facing, int health, int MAX_HEALTH, int hunger, int MAX_HUNGER, int exposure, int MAX_EXPOSURE, int thirst, int MAX_THIRST, World world, double SPEED) {
                this.id = id;
                this.inventory = inventory;
                this.x = x;
                this.y = y;
                this.radius = radius;
                this.facing = facing;
                this.health = health;
                this.MAX_HEALTH = MAX_HEALTH;
                this.hunger = hunger;
                this.MAX_HUNGER = MAX_HUNGER;
                this.exposure = exposure;
                this.MAX_EXPOSURE = MAX_EXPOSURE;
                this.thirst = thirst;
                this.MAX_THIRST = MAX_THIRST;
                this.world = world;
                this.SPEED = SPEED;
        }
        
        /**
         * Reads an serialized Entity from a file.
         *
         * @param world the world that the entity is in.
         * @param file the file to read the entity from.
         * @return The Entity read from the file that has been associated with the specified world
         */
        public static Entity readEntity(World world, File file) {
                Entity out = null;
                try {
                        FileInputStream fos = new FileInputStream(file);
                        ObjectInputStream oos = new ObjectInputStream(fos);
                        out = (Entity)oos.readObject();
                        out.setWorld(world);
                        oos.close();
                        fos.close();
                }
                catch (Throwable e) {
                        System.out.println("Error " + e.getMessage());
                        e.printStackTrace();
                }
                return out;
        }
        
        /**
         * Writes an serialized Entity from a file.
         *
         * @param file the file to read the entity from.
         */
        public void writeEntity(File file) {
                try {
                        FileOutputStream fos = new FileOutputStream(file);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(this);
                        oos.close();
                        fos.close();
                }
                catch (Throwable e) {
                        System.out.println("Error " + e.getMessage());
                        e.printStackTrace();
                }
        }
        
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
        
        public void setWorld(World w) {
                world = w;
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
                return new Point2D(x, y);
        }
        
        /**
         * Sets the entity's position to a new value
         * @param position the new position
         */
        public void setPosition(Point2D position) {
                x = position.getX();
                y = position.getY();
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
        
        public void setInvincible(boolean invincible) {
                this.invincible = invincible;
        }
        
        /**
         * Describes the actions that an entity would take involuntarily, such as becoming thirsty
         */
        public abstract void exist();
        
        /**
         * Changes the hunger by a given amount
         * @param foodWorth is the nutritional value of what has been eaten
         */
        public void eatFood(int foodWorth){
                if(!invincible) {
                        if(hunger + foodWorth <= MAX_HUNGER)
                                hunger += foodWorth;
                        else
                                hunger = MAX_HUNGER;
                        if(hunger < 0)
                                hunger = 0;
                }
        }
        
        /**
         * Changes the hunger by a given amount
         * @param foodWorth is the nutritional value of what has been eaten
         */
        public void drink(int drunk){
                if(!invincible) {
                        if(thirst + drunk <= MAX_THIRST)
                                thirst += drunk;
                        else
                                thirst = MAX_THIRST;
                        if(thirst < 0)
                                thirst = 0;
                }
        }
        
        /**
         * Changes the health by a given amount
         * @param damage is the amount that the health is to be changed by
         */
        public void takeDamage(int damage) {
                long now = world.getStopwatch().getElapsed();
                damageWait += now - prevElapsed;
                prevElapsed = now;
                if(damageWait >= 2e8) {
                        if(!invincible) {
                                if (health - damage <= MAX_HEALTH)
                                        health -= damage;
                                else
                                        health = MAX_HEALTH;
                                if(health < 0)
                                        health = 0;
                        }
                        damageWait = 0;
                }
        }
        
        /**
         * Changes the exposure by a given amount, if the player gets too cold they die
         * @param n the amount that the exposure is to be changed by
         */
        public void lowerExposure (int n){
                if(!invincible) {
                        if (exposure - n <= MAX_EXPOSURE)
                                exposure -= n;
                        else
                                exposure = MAX_EXPOSURE;
                        if(exposure < 0)
                                exposure = 0;
                }
        }
        
        /**
         * Sets the exposure of the entity
         * @param exposure The number that exposure is to be set to
         */
        public void setExposure(int exposure) {
                this.exposure = exposure;
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
                return x;
        }
        
        /**
         * @returns the y coordinate of the entity
         */
        public double getY() {
                return y;
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
                        Point blockPos = World.blockCoordinate(getPosition());
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
                for (int row = (int)World.blockCoordinate(getPosition()).getY() - 1; row <= (int)World.blockCoordinate(getPosition()).getY()+1; row++){
                        for(int col = (int)World.blockCoordinate(getPosition()).getX()-1; col <= (int)World.blockCoordinate(getPosition()).getX()+1; col++){
                                if(world.getBlock(col, row, 1)==null || ResourceManager.getBlock(world.getBlock(row, col, 1)) instanceof InsectBlock)
                                        return new Point(col, row);
                        }
                }
                return null;
        }
}
