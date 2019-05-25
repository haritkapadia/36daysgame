import java.util.*;
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
import javafx.embed.swing.SwingFXUtils;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Game extends AnimationTimer {
	private Scene scene;
	World world;
	Camera camera;
	StackPane gamePane;
	Canvas canvas;
	Player player;
	InputManager i;

	long prevTime = -1;

	Game(Scene scene) {
		this.scene = scene;
		world = new World();
		player = new Player(world);
		world.addEntity(player);
		i = new InputManager(this, player);
		camera = new Camera(scene, world, player.getPosition());
		canvas = new Canvas((int)scene.getWidth(), (int)scene.getHeight());
		gamePane = new StackPane();

		gamePane.getChildren().add(canvas);

		scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> i.keyPressed(e));
		scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> i.keyReleased(e));
	}

	public StackPane getPane() {
		return gamePane;
	}

	private void processInput(long time) {
		if(prevTime == -1) {
			prevTime = time;
			return;
		}

		long dt = time - prevTime;
		player.move(i.getDisplacement().multiply(dt / 1e9));
		camera.setPosition(player.getPosition());


		prevTime = time;
	}

	public void handle(long time) {
		processInput(time);
		drawScreen();
	}

	public void drawScreen() {
		GraphicsContext g = canvas.getGraphicsContext2D();
		// g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		Rectangle2D r = camera.getViewBounds(); // works as intended
		Point sw = Chunk.globalToChunkPoint(new Point2D(r.getMinX(), r.getMinY())); // These work
		Point se = Chunk.globalToChunkPoint(new Point2D(r.getMaxX(), r.getMinY())); // These work
		Point nw = Chunk.globalToChunkPoint(new Point2D(r.getMinX(), r.getMaxY())); // These work

		double maxRatio = Math.max(scene.getWidth(), scene.getHeight()) / Math.max(r.getWidth(), r.getHeight());
		double maxS = Math.max(scene.getWidth(), scene.getHeight());
		double minS = Math.min(scene.getWidth(), scene.getHeight());

		for(int i = (int)sw.getX(); i <= (int)se.getX(); i++) {
			for(int j = (int)sw.getY(); j <= (int)nw.getY(); j++) {
				Point2D chunkPos = Chunk.chunkToGlobalPoint(new Point(i, j));
				Chunk c = world.getChunk((int)chunkPos.getX(), (int)chunkPos.getY());
				int screenX = (int)((chunkPos.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
				int screenY = (int)(scene.getHeight() - ((chunkPos.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
				int screenW = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * maxS);
				int screenH = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * maxS);
				if(c == null) {
					world.putChunk(new Point((int)chunkPos.getX(), (int)chunkPos.getY()));
					c = world.getChunk(new Point((int)chunkPos.getX(), (int)chunkPos.getY()));
				}
				g.drawImage(SwingFXUtils.toFXImage(c.getChunkImage(), null),
					    screenX,
					    screenY - screenH,
					    screenW,
					    screenH);
			}
		}

		for(Entity e : world.getEntities()) {
			Point2D p = e.getPosition();
			Dimension2D d = e.getDimension();
			int screenX = (int)((p.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
			int screenY = (int)(scene.getHeight() - ((p.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
			int screenW = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * d.getWidth());
			int screenH = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * d.getHeight());
			g.drawImage(SwingFXUtils.toFXImage((BufferedImage)e.getImage(), null), screenX, screenY - screenH, screenW, screenH);
		}
	}

	public Scene getScene() {
		return scene;
	}
}
