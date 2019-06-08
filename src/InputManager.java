/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.awt.Point;

/**
 * This class handles all user input in the game
 *
 * Variables:
 *
 * vx, vy     -Control the x-velocity and y-velocity respectively
 * pressed    -Used to determine which key the user pressed
 * game       -Stores a reference to the game object
 * world      -Reference to the game world object
 * player     -Reference to the player object
 * clickPosition      -Stores where the player clicked
 * mouseX     -Stores the x coordinate of the mouse
 * mouseY     -Stores the y coordinate of the mouse
 * clickButton -Stores mouse input
 */
public class InputManager {
        private double vx = 0, vy = 0;
        private EnumMap<KeyCode, Boolean> pressed;
        private Game game;
        private World world;
        private Player player;
        private Point2D clickPosition;
        private double mouseX;
        private double mouseY;
        private MouseButton clickButton;
        
        /**
         * Class constructor, initializes all the variables
         * @param game is a reference to the game object
         * @param world is a reference to the game world object
         * @param player is a reference to the player object
         */
        InputManager(Game game, World world, Player player) {
                this.game = game;
                this.world = world;
                this.player = player;
                clickPosition = player.getPosition();
                mouseX = game.getScene().getWidth() / 2;
                mouseY = game.getScene().getHeight() / 2;
                clickButton = null;
                pressed = new EnumMap<KeyCode, Boolean>(KeyCode.class){{
                        for(KeyCode k : KeyCode.values())
                        put(k, false);
                }};
        }
        
        /**
         * Updates the position of the mouse
         * @param MouseEvent stores the mouse input
         */
        public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
        }
        
        /**
         * Moves the player in accordance to where the user clicked
         * @param MouseEvent stores mouse input
         */
        public void mouseClicked(MouseEvent e) {
                clickPosition = screenToWorldCoordinate(e.getX(), e.getY());
                Point p = World.blockCoordinate(clickPosition);
                BlockKey b = world.getBlock((int)p.getX(), (int)p.getY(), 1);
                double angle = Math.atan2(clickPosition.getY() - player.getY(), clickPosition.getX() - player.getX());
                
                clickPosition = new Point2D(p.getX() + 0.5, p.getY() + 0.5);
                if(-Math.PI <= angle && angle < -Math.PI * 3/4)
                        player.setFacing(Direction.LEFT);
                else if(-Math.PI * 3/4 <= angle && angle < -Math.PI * 1/4)
                        player.setFacing(Direction.DOWN);
                else if(-Math.PI * 1/4 <= angle && angle < Math.PI * 1/4)
                        player.setFacing(Direction.RIGHT);
                else if(Math.PI * 1/4 <= angle && angle < Math.PI * 3/4)
                        player.setFacing(Direction.UP);
                else if(Math.PI * 3/4 <= angle && angle < Math.PI)
                        player.setFacing(Direction.LEFT);
                clickButton = e.getButton();
        }
        
        /**
         * @returns the displacement between the player and where the user clicked as a 2D vector
         */
        public Point2D getDesiredDisplacement() {
                Point2D displacement = clickPosition.subtract(player.getPosition());
                return displacement;
        }
        
        /**
         * Resets the mouse
         */
        public void resetClick() {
                clickPosition = Main.point2d(player.getPosition());
                clickButton = null;
        }
        
        /**
         * @returns the mouse coordinates in relation to the world
         */
        public Point2D getWorldMouseCoordinates() {
                return screenToWorldCoordinate(mouseX, mouseY);
        }
        
        /**
         * @returns which button was clicked
         */
        public MouseButton getClickButton() {
                return clickButton;
        }
        
        /**
         * @returns mouse coordinates on the screen
         */
        public Point2D getMouseCoordinates() {
                return new Point2D(mouseX, mouseY);
        }
        
        /**
         * @returns where the mouse clicked
         */
        public Point2D getClickPosition() {
                return clickPosition;
        }
        
        /**
         * @returns the coordinates of the screen converted to coordinates of the world
         * @param x is the x coordinate
         * @param y is the y coordinate
         */
        private Point2D screenToWorldCoordinate(double x, double y) {
                Camera camera = game.getCamera();
                Rectangle2D r = camera.getViewBounds(); // works as intended
                Scene scene = game.getScene();
                double maxRatio = Math.max(scene.getWidth(), scene.getHeight()) / Math.max(r.getWidth(), r.getHeight());
                double worldX = (x - scene.getWidth() / 2) / maxRatio + camera.getX();
                double worldY = (scene.getHeight() / 2 - y) / maxRatio + camera.getY();
                return new Point2D(worldX, worldY);
        }
        
        /**
         * Gets keyboard input and causes the player to drop/use items
         * @param e stores the KeyEvent
         */
        public void keyPressed(KeyEvent e) {
                Point2D mousePosition = Main.toPoint2D(World.blockCoordinate(screenToWorldCoordinate(mouseX, mouseY)));
                if(e.getCode() == KeyCode.P) {
                        game.killQuests();
                        game.stop();
                        Main.setPane(game.getScene(), "Main Menu");
                        world.write();
                }
                if(!pressed.get(KeyCode.Q) && e.getCode() == KeyCode.Q) {
                        if(pressed.get(KeyCode.CONTROL))
                                player.dropItem(0);
                        else
                                player.useTool(0, mousePosition);
                        
                } else if(!pressed.get(KeyCode.W) && e.getCode() == KeyCode.W) {
                        if(pressed.get(KeyCode.CONTROL))
                                player.dropItem(1);
                        else
                                player.useTool(1, mousePosition);
                } else if(!pressed.get(KeyCode.E) && e.getCode() == KeyCode.E) {
                        if(pressed.get(KeyCode.CONTROL))
                                player.dropItem(2);
                        else
                                player.useTool(2, mousePosition);
                } else if(!pressed.get(KeyCode.R) && e.getCode() == KeyCode.R) {
                        if(pressed.get(KeyCode.CONTROL))
                                player.dropItem(3);
                        else
                                player.useTool(3, mousePosition);
                } else if(!pressed.get(KeyCode.T) && e.getCode() == KeyCode.T) {
                        if(pressed.get(KeyCode.CONTROL))
                                player.dropItem(4);
                        else
                                player.useTool(4, mousePosition);
                } else if(!pressed.get(KeyCode.M) && e.getCode() == KeyCode.M) {
                        List<Quest> active = new ArrayList<Quest>(){{
                                addAll(game.getQuestManager().getQuests());
                        }};
                        for(Quest q : active)
                                q.completeQuest();
                }
                pressed.put(e.getCode(), true);
        }
        
        /**
         * Resets keyboard input
         */
        public void keyReleased(KeyEvent e) {
                pressed.put(e.getCode(), false);
        }
}
