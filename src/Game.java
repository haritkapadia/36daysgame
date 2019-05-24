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
import java.awt.Point;

public class Game extends AnimationTimer {
	private Scene scene;
	World world;
	Camera camera;
	StackPane gamePane;
	Canvas canvas;

	double vx, vy;
	long prevTime = -1;

	Game(Scene scene) {
		this.scene = scene;
		world = new World();
		camera = new Camera(scene, world, new Point2D(6, 0));
		canvas = new Canvas((int)scene.getWidth(), (int)scene.getHeight());
		gamePane = new StackPane();

		gamePane.getChildren().add(canvas);

		final double SPEED = 10;
		scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
				if(e.getCode() == KeyCode.W)
					vy = SPEED;
				else if(e.getCode() == KeyCode.S)
					vy = -SPEED;
				else if(e.getCode() == KeyCode.A)
					vx = -SPEED;
				else if(e.getCode() == KeyCode.D)
					vx = SPEED;
				else if(e.getCode() == KeyCode.Q) {
					stop();
					Main.setPane(scene, "Main Menu");
				}
			});

		scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
				if(e.getCode() == KeyCode.W)
					vy = 0;
				else if(e.getCode() == KeyCode.S)
					vy = 0;
				else if(e.getCode() == KeyCode.A)
					vx = 0;
				else if(e.getCode() == KeyCode.D)
					vx = 0;
				else if(e.getCode() == KeyCode.Q) {
					stop();
					Main.setPane(scene, "Main Menu");
				}
			});


		start();
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
		System.out.println(vx * dt / 1e9);
		camera.move(vx * dt / 1e9, vy * dt / 1e9);


		prevTime = time;
	}

	public void handle(long time) {
		processInput(time);
		drawScreen();
	}

	public void drawScreen() {
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
				if(c != null) {
					System.out.print("screenX: " + screenX + "\t");
					System.out.print("screenY: " + screenY + "\t");
					System.out.print("screenW: " + screenW + "\t");
					System.out.print("screenH: " + screenH + "\n");

					g.drawImage(c.getChunkImage(),
						    screenX,
						    screenY - screenH,
						    screenW,
						    screenH);
				}
			}
		}
	}
}
