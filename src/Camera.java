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
		scaleFactor = 10;
	}

	public Point3D getPosition() {
		return position;
	}

	public Rectangle2D getViewBounds() {
		double width = scene.getWidth();
		double height = scene.getHeight();
		double max = Math.max(width, height);
		Rectangle2D out;
		if(max == width)
			Rectangle2D out = new Rectangle2D(position.getX() - 0.5, position.getY() - 0.5 * width / height, 1, width / height);
		else
			Rectangle2D out = new Rectangle2D(position.getX() - 0.5 * width / height, position.getY() - 0.5, width / height, 1);
		return new Rectangle2D(out.getX(), out.getY(), out.getWidth() * blockFactor, out.getHeight() * blockFactor);
	}
}
