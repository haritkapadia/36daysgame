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

public class Camera {
	private Scene scene;
	private World world;
	private Point2D position;
	private double blockFactor;

	Camera(Scene scene, World world, Point2D position) {
		this.scene = scene;
		this.world = world;
		this.position = position;
		blockFactor = 20;
	}

	public Point2D getPosition() {
		return position;
	}

	// works as intended
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

	public double getBlockFactor() {
		return blockFactor;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void move(double dx, double dy) {
		position = position.add(dx, dy);
	}

	public void move(Point2D p) {
		position = position.add(p);
	}

	public double getX() {
		return position.getX();
	}

	public double getY() {
		return position.getY();
	}
}
