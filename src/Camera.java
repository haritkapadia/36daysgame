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
		blockFactor = 10;
	}

	public Point2D getPosition() {
		return position;
	}

	public Rectangle2D getViewBounds() {
		double width = scene.getWidth();
		double height = scene.getHeight();
		double max = Math.max(width, height);
		Rectangle2D out;
		if(max == width)
			return new Rectangle2D(position.getX() - Chunk.CHUNK_SIDE_LENGTH / 2.0,
					       position.getY() - Chunk.CHUNK_SIDE_LENGTH * height / width / 2.0,
					       Chunk.CHUNK_SIDE_LENGTH,
					       Chunk.CHUNK_SIDE_LENGTH * height / width);
		else
			return new Rectangle2D(position.getX() - Chunk.CHUNK_SIDE_LENGTH * width / height / 2.0,
					       position.getY() - Chunk.CHUNK_SIDE_LENGTH / 2.0,
					       Chunk.CHUNK_SIDE_LENGTH * height / width,
					       Chunk.CHUNK_SIDE_LENGTH);
	}
}
