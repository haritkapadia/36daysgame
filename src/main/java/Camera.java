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
import javafx.scene.canvas.*;
import javafx.stage.*;
import javafx.geometry.*;

/**
 * Controls what blocks are visible at any given time
 *
 * Variables:
 *
 * scene         -A reference to the main scene object contained in the primary stage
 * world         -A reference to the world object, contains everything in the game world
 * position      -A Point2D object used to store the player's position in the world
 * blockFactor   -A double used to convert screen coordinates to block coordinates
 */
public class Camera {
	private Scene scene;
	private World world;
	private Point2D position;
	private double blockFactor;

	/**
	 * Class constructor, initializes all of the variables
	 *
	 * @param scene is a reference to the scene contained by the primary stage
	 * @param world the game world
	 * @param position the player's position in the game
	 */
	Camera(Scene scene, World world, Point2D position) {
		this.scene = scene;
		this.world = world;
		this.position = position;
		blockFactor = 30;
	}

	/**
	 * @returns The player's position as a Point2D object
	 */
	public Point2D getPosition() {
		return position;
	}

	/**
	 * @returns The amount of the world that should be visible on screen as a Rectangle2D object
	 *
	 * Variables
	 *
	 * width:      The view bounds width
	 * height:     The view bounds height
	 * max:        Whichever of the width/height variables is larger
	 */
	public Rectangle2D getViewBounds() {
		double width = scene.getWidth();
		double height = scene.getHeight();
		double max = Math.max(width, height);
		if(max == width)
			return new Rectangle2D(position.getX() - blockFactor / 2.0,
					       position.getY() - blockFactor * height / width / 2.0,
					       blockFactor,
					       blockFactor * height / width);
		else
			return new Rectangle2D(position.getX() - blockFactor * width / height / 2.0,
					       position.getY() - blockFactor / 2.0,
					       blockFactor * height / width,
					       blockFactor);
	}

	/**
	 * @returns The block factor variable
	 */
	public double getBlockFactor() {
		return blockFactor;
	}

	/**
	 * Sets the blockFactor variable
	 * @param blockFactor is the new blockFactor of the player
	 */
	public void setBlockFactor(double blockFactor) {
		this.blockFactor = blockFactor;
	}

	/**
	 * Sets the position variable
	 * @param position is the new position of the player
	 */
	public void setPosition(Point2D position) {
		this.position = position;
	}

	/**
	 * Changes the position by given x and y amounts
	 * @param dx is the amount that the x coordinate will be changed by
	 * @param dy is the amount that the y coordinate will be changed by
	 */
	public void move(double dx, double dy) {
		position = position.add(dx, dy);
	}

	/**
	 * Moves the position by given x and y amounts
	 * @param p is the amount that the position will be moved in a Point2D form
	 */
	public void move(Point2D p) {
		position = position.add(p);
	}

	/**
	 * @returns the x coordinate
	 */
	public double getX() {
		return position.getX();
	}

	/**
	 * @returns the y coordinate
	 */
	public double getY() {
		return position.getY();
	}
}
