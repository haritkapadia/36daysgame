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

	long prevTime = 0;

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



		questManager = new QuestManager(ui);
		initialiseQuests();
	}

	public StackPane getPane() {
		return gamePane;
	}

	public void processInput(long time) {
		long dt = time - prevTime;

		Point2D newPos = player.getPosition().add(i.getDirection().multiply(player.getSpeed() * dt / 1e9));
		// for(double i = newPos.getX(); i < newPos.getX() + player.getWidth(); i++) {
		//	for(double j = newPos.getY(); j < newPos.getY() + player.getHeight(); j++) {
		//		Point blockPoint = World.blockCoordinate(i, j);
		//		BlockKey blockKey = world.getBlock(blockPoint, 1);
		//		if(blockKey != null && ResourceManager.getBlock(blockKey).isSolid()) {
		//			//Find min entry
		//			Direction min = Direction.DOWN;
		//			double minLength = Math.abs(i - blockPoint.getY());
		//			if(minLength > Math.abs(i - (blockPoint.getY() + 1))) {
		//				min = Direction.UP;
		//				minLength = Math.abs(i - (blockPoint.getY() + 1));
		//			}
		//			if(minLength > Math.abs(j - blockPoint.getX())) {
		//				min = Direction.LEFT;
		//				minLength = Math.abs(j - blockPoint.getX());
		//			}
		//			if(minLength > Math.abs(j - (blockPoint.getX() + 1))) {
		//				min = Direction.RIGHT;
		//				minLength = Math.abs(j - (blockPoint.getX() + 1));
		//			}

		//			if(min == Direction.DOWN)
		//				newPos.add(0, -minLength);
		//			else if(min == Direction.UP)
		//				newPos.add(0, minLength);
		//			else if(min == Direction.LEFT)
		//				newPos.add(-minLength, 0);
		//			else if(min == Direction.RIGHT)
		//				newPos.add(minLength, 0);
		//		}
		//	}
		// }

		player.setPosition(newPos);
		prevTime = time;
	}

	public void handle(long time) {
		drawScreen();
		processInput(time);
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
				int screenW = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * maxS);
				int screenH = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * maxS);
				if(c == null) {
					world.generateChunk(chunkPoint);
					c = world.getChunk(chunkPoint);
				}
				g.drawImage(SwingFXUtils.toFXImage(c.getChunkImage(), null), screenX, screenY - screenH, screenW, screenH);
			}
		}

		for(Entity e : world.getEntities()) {
			int screenX = (int)((e.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
			int screenY = (int)(scene.getHeight() - ((e.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
			int screenW = (int)(e.getWidth() / camera.getBlockFactor() * maxS);
			int screenH = (int)(e.getHeight() / camera.getBlockFactor() * maxS);
			g.drawImage(SwingFXUtils.toFXImage((BufferedImage)e.getImage(), null), screenX, screenY - screenH, screenW, screenH);
		}
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
}
