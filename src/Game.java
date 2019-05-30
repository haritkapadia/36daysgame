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
	VBox ui = null;
	Player player;
	ProgressIndicator health;
	InputManager i;
	QuestManager questManager;
	Point2D prevPosition;

	long prevTime = -1;

	Game(Scene scene) {
		this.scene = scene;
		world = new World();
		player = new Player(world);
		world.addEntity(player);
		camera = new Camera(scene, world, player.getPosition());

		gamePane = new StackPane();
		canvas = new Canvas((int)scene.getWidth(), (int)scene.getHeight());
		health = new ProgressIndicator((double)player.getHealth() / player.getMaximumHealth());
		ui = new VBox(){{
			getChildren().add(health);
		}};
		gamePane.getChildren().addAll(canvas, ui);

		i = new InputManager(this, world, player);
		scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> i.keyPressed(e));
		scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> i.keyReleased(e));
		scene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> i.mouseClicked(e));
		scene.addEventHandler(MouseEvent.MOUSE_MOVED, e -> i.mouseMoved(e));



		questManager = new QuestManager(ui);
		initialiseQuests();
	}

	public StackPane getPane() {
		return gamePane;
	}

	private void processInput(long time) {
		if(prevTime == -1) {
			prevTime = time;
			prevPosition = Main.point2d(player.getPosition());
			return;
		}
		long dt = time - prevTime;
		Point2D displacement = i.getDesiredDisplacement();
		if(displacement.magnitude() > player.getSpeed() * dt / 1e9)
			displacement = displacement.normalize().multiply(player.getSpeed() * dt / 1e9);
		player.move(displacement);

		Point p = World.blockCoordinate(player.getPosition());
		BlockKey b = world.getBlock((int)p.getX(), (int)p.getY(), 1);
		if(b != null && ResourceManager.getBlock(b).isSolid()) {
			Point pp = World.blockCoordinate(prevPosition);
			player.setPosition(new Point2D(pp.getX() + 0.5, pp.getY() + 0.5));
			i.resetClick();
		}

		if(displacement.getX() == 0 && displacement.getY() == 0)
			player.stop();
		else
			player.play();
		camera.setPosition(player.getPosition());
		prevTime = time;
		prevPosition = Main.point2d(player.getPosition());
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
				Point chunkPoint = new Point(i, j);
				Point2D chunkPos = Chunk.chunkToGlobalPoint(chunkPoint);
				Chunk c = world.getChunk(chunkPoint);
				int screenX = (int)((chunkPos.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
				int screenY = (int)(scene.getHeight() - ((chunkPos.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
				int screenL = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * maxS);
				if(c == null) {
					world.generateChunk(chunkPoint);
					c = world.getChunk(chunkPoint);
				}
				g.drawImage(SwingFXUtils.toFXImage(c.getChunkImage(), null), screenX, screenY - screenL, screenL, screenL);
				int screenB = (int)(1 / camera.getBlockFactor() * maxS);
				g.strokeRect(screenX, screenY - screenB, screenB, screenB);
			}
		}

		for(Entity e : world.getEntities()) {
			int screenX = (int)((e.getX() - e.getRadius() - camera.getX()) * maxRatio + scene.getWidth()/2);
			int screenY = (int)(scene.getHeight() - ((e.getY() - e.getRadius() - camera.getY()) * maxRatio + scene.getHeight() / 2));
			int screenL = (int)(2 * e.getRadius() / camera.getBlockFactor() * maxS);
			g.drawImage(SwingFXUtils.toFXImage((BufferedImage)e.getImage(), null), screenX, screenY - screenL, screenL, screenL);
		}

		Point m = World.blockCoordinate(i.getWorldMouseCoordinates());
		int screenX = (int)((m.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
		int screenY = (int)(scene.getHeight() - ((m.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
		int screenL = (int)(1 / camera.getBlockFactor() * maxS);
		g.strokeRect(screenX, screenY - screenL, screenL, screenL);
		int playerX = (int)((player.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
		int playerY = (int)(scene.getHeight() - ((player.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
		Point2D c = i.getClickPosition();
		int clickX = (int)((c.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
		int clickY = (int)(scene.getHeight() - ((c.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
		g.strokeLine(playerX, playerY, clickX, clickY);
	}

	public void killQuests() {
		questManager.killQuests();
	}

	private void initialiseQuests() {
		Quest touchingPoison = new Quest(questManager,
						 "Touching Poison",
						 "What happens when you touch poison? Nothing!",
						 1,
						 ResourceManager.getBlock(BlockKey.POISON),
						 null);
		Quest breakingHogweed = new Quest(questManager,
						  "Breaking Weeds",
						  "See the effects of breaking hogweed!",
						  5,
						  ResourceManager.getBlock(BlockKey.HOGWEED),
						  new Quest[]{touchingPoison});
		questManager.addQuest(breakingHogweed);
		questManager.addQuest(touchingPoison);
		questManager.startQuest(breakingHogweed);
	}

	public Scene getScene() {
		return scene;
	}

	public Camera getCamera() {
		return camera;
	}
}
