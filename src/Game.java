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
import java.awt.Point;

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
		Rectangle2D r = camera.getViewBounds();
		Point2D cameraLeft = Chunk.chunkToGlobalPoint(Chunk.globalToChunkPoint(camera.getPosition()));
		Point sw = Chunk.globalToChunkPoint(new Point2D(r.getMinX(), r.getMinY()));
		Point se = Chunk.globalToChunkPoint(new Point2D(r.getMaxX(), r.getMinY()));
		Point nw = Chunk.globalToChunkPoint(new Point2D(r.getMinX(), r.getMaxY()));

		for(int i = (int)sw.getX(); i <= (int)se.getX(); i++) {
			for(int j = (int)sw.getY(); j <= (int)nw.getY(); j++) {
				Point2D chunkPos = Chunk.chunkToGlobalPoint(new Point(i, j));
				g.drawImage(world.getChunk((int)chunkPos.getX(), (int)chunkPos.getY()).getChunkImage(),
					    (int)(chunkPos.getX() - cameraLeft.getX()),
					    (int)(scene.getHeight() - (chunkPos.getY() - cameraLeft.getY())),
					    (int)(Math.max(scene.getWidth(), scene.getHeight()) / Chunk.CHUNK_SIDE_LENGTH),
					    (int)(Math.max(scene.getWidth(), scene.getHeight()) / Chunk.CHUNK_SIDE_LENGTH));
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
