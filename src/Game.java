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

public class Game extends StackPane {
	private Scene scene;
	World world;
	Camera camera;
	Canvas canvas;

	Game(Scene scene) {
		this.scene = scene;
		world = new World();
		camera = new Camera(scene, world, new Point2D(0, 0));
		canvas = new Canvas((int)scene.getWidth(), (int)scene.getHeight());

		getChildren().add(canvas);
		update();
	}

	public void update() {
		GraphicsContext g = canvas.getGraphicsContext2D();
		Rectangle2D p = camera.getViewBounds();

		Point nw = globalToChunkPoint(new Point2D(p.getMinX(), p.getMinY()));
		Point ne = globalToChunkPoint(new Point2D(p.getMaxX(), p.getMinY()));
		Point sw = globalToChunkPoint(new Point2D(p.getMinX(), p.getMaxY()));
		Point se = globalToChunkPoint(new Point2D(p.getMaxX(), p.getMaxY()));

		for(int i = nw.getX(); i <= ne.getX(); i++) {
			for(int j = nw.getY(); j <= sw.getY(); j++) {
				Chunk c = world.getChunk(new Point(i, j));
				if(c != null) {

				}
			}
		}
	}

	public void update(int notUsedLolXd) {
		GraphicsContext g = canvas.getGraphicsContext2D();
		Chunk chunk = world.getChunk(0, 0);
		Block[][][] blocks = chunk.getBlocks();
		for(int i = 0; i < blocks.length; i++) {
			for(int j = 0; j < blocks[i].length; j++) {
				if(blocks[i][j][0] != null)
					g.drawImage(blocks[i][j][0].getImage(), i * 32, j * 32);
			}
		}
	}
}
