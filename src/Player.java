/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.util.*;
import javafx.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;

/**
 * This class is used to create a player object which the user controls
 * 
 * Variables:
 * 
 * image          -The sprite image
 * inventoryPane  -An InventoryPane object used to display the inventory
 * healthWait     -How long it's been since the the player's health lowered from being hungry
 * hungerWait     -How long it's been since  the player's hunger decreased
 * exposureWait   -How long it's been since the player's exposure decreased
 * thirstWait     -How long it's been since the player's thirst decreased
 * prevElapsed    -Current game time
 */
public class Player extends Entity implements java.io.Serializable {
        transient Image image;
        transient InventoryPane inventoryPane;
        private long healthWait;
        private long hungerWait;
        private long exposureWait;
        private long thirstWait;
        private long prevElapsed;
        
        /**
         * Class constructor, calls the super constructor and initializes the variables
         * @param world The World that the player is part of
         * @param s The identifier string of the player
         */
        Player(World world, String s) {
                super(world);
                id = s;
                inventory[0] = ItemKey.KNIFE;
                inventory[1] = ItemKey.WATERBOTTLE;
                image = ResourceManager.getPlayerSprite(Direction.DOWN, 2);
                prevElapsed = world.getStopwatch().getElapsed();
        }
        
        /**
         * Changes the player's image in relation to where they are facing
         */
        public void interpolate(double frac) {
                int frame = ((int)(frac * 3) + 1) % 3;
                image = ResourceManager.getPlayerSprite(getFacing(), frame);
        }
        
        /**
         * Changes the player's image to the stationary image and calls the stop method from the super class
         */
        public void stop() {
                image = ResourceManager.getPlayerSprite(getFacing(), 1);
                super.stop();
        }
        
        /**
         * Updates the player's stats
         * 
         * Variables:
         * 
         * now     -The current game time
         */
        public void exist() {
                long now = world.getStopwatch().getElapsed();
                healthWait += now - prevElapsed;
                exposureWait += now - prevElapsed;
                hungerWait += now - prevElapsed;
                thirstWait += now - prevElapsed;
                prevElapsed = now;
                if(healthWait >= 10e9) {
                        if(getHunger() < getMaxHunger()/2)
                                takeDamage(1);
                        healthWait -= (long)10e9;
                }
                if(exposureWait >= 1e9) {
                        if (world.getLightLevel() <= 0.4)
                                lowerExposure(1);
                        if(getExposure() < getMaxExposure() * 0.4)
                                takeDamage(1);
                        exposureWait -= (long)1e9;
                }
                if(hungerWait >= 20e9) {
                        eatFood(-1);
                        if(getHunger() > getMaxHunger() * 0.8)
                                takeDamage(-1);
                        hungerWait -= (long)20e9;
                }
                if(thirstWait >= 10e9) {
                        drink(-1);
                        if(getThirst() > getMaxThirst() * 0.8)
                                takeDamage(-1);
                        thirstWait -= (long)10e9;
                }
        }
        
        /**
         * Makes the player destroy a block
         * 
         * @param x The x coordinate of the block
         * @param y The y coordinate of the block
         * @param z The z coordinate of the block
         */
        public void destroy(int x, int y, int z) {
                world.destroyBlock(x, y, z);
        }
        
        /**
         * Moves the player by a given displacement
         * 
         * @param displacement The displacement by which the player is moved
         */
        public void move(Point2D displacement) {
                setPosition(getPosition().add(displacement));
        }
        
        /**
         * @returns The player's image
         */
        public Image getImage() {
                return image;
        }
        
        /**
         * @returns true since the player has a transparent background
         */
        public boolean isTransparent() {
                return true;
        }
        
        /**
         * Makes the player use a tool
         * @param i The inventory slot number of the tool
         * @param target The point that the tool is being used on
         */
        public void useTool(int i, Point2D target) {
                super.useTool(i, target);
                updateInventoryPaneSlot(i);
        }
        
        /**
         * Makes the player discard an item
         * 
         * @param i The inventory slot number of the item the player is discarding
         */
        public void dropItem(int i) {
                super.dropItem(i);
                updateInventoryPaneSlot(i);
        }
        
        /**
         * Changes the inventory pane to a new InventoryPane object
         * @param p The new InventoryPane
         */
        public void setInventoryPane(InventoryPane p) {
                inventoryPane = p;
        }
        
        /**
         * Updates the inventory pane slot to show an image of the item that it currently contains
         * 
         * @param i The inventory slot number
         */
        public void updateInventoryPaneSlot(int i) {
                inventoryPane.updateSlot(i);
        }
        
        /**
         * Changes the cycle duration
         * @param d The new cycle duration
         */
        public void setCycle(Duration d) {
                setCycleDuration(d);
        }
}
