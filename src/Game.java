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
import javafx.scene.effect.*;
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
		Rectangle2D r = camera.getViewBounds(); // works as intended
		double maxRatio = Math.max(scene.getWidth(), scene.getHeight()) / Math.max(r.getWidth(), r.getHeight());
		double maxS = Math.max(scene.getWidth(), scene.getHeight());
		double minS = Math.min(scene.getWidth(), scene.getHeight());
		Point sw = World.blockCoordinate(new Point2D(r.getMinX(), r.getMinY())); // These work
		Point se = World.blockCoordinate(new Point2D(r.getMaxX(), r.getMinY())); // These work
		Point nw = World.blockCoordinate(new Point2D(r.getMinX(), r.getMaxY())); // These work

		for(int z = 0; z < 2; z++) {
			for(int i = (int)sw.getX(); i <= (int)se.getX(); i++) {
				for(int j = (int)sw.getY(); j <= (int)nw.getY(); j++) {
					double screenX = (i - camera.getX()) * maxRatio + scene.getWidth()/2;
					double screenY = scene.getHeight() - ((j - camera.getY()) * maxRatio + scene.getHeight() / 2);
					double screenL = 1 / camera.getBlockFactor() * maxS;
					BlockKey b = world.getBlock(i, j, z);
					if(b != null)
						g.drawImage(ResourceManager.getBlock(b).getImage(),
							    screenX,
							    screenY - screenL,
							    screenL,
							    screenL);
				}
			}
		}

		for(Entity e : world.getEntities()) {
			double screenX = (e.getX() - e.getRadius() - camera.getX()) * maxRatio + scene.getWidth()/2;
			double screenY = scene.getHeight() - ((e.getY() - e.getRadius() - camera.getY()) * maxRatio + scene.getHeight() / 2);
			double screenL = 2 * e.getRadius() / camera.getBlockFactor() * maxS;
			g.drawImage(e.getImage(), screenX, screenY - screenL, screenL, screenL);
		}

		Point m = World.blockCoordinate(i.getWorldMouseCoordinates());
		double screenX = (m.getX() - camera.getX()) * maxRatio + scene.getWidth()/2;
		double screenY = scene.getHeight() - ((m.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2);
		double screenL = 1 / camera.getBlockFactor() * maxS;
		g.strokeRect(screenX, screenY - screenL, screenL, screenL);
		double playerX = (player.getX() - camera.getX()) * maxRatio + scene.getWidth()/2;
		double playerY = scene.getHeight() - ((player.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2);
		Point2D c = i.getClickPosition();
		double clickX = (c.getX() - camera.getX()) * maxRatio + scene.getWidth()/2;
		double clickY = scene.getHeight() - ((c.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2);
		g.strokeLine(playerX, playerY, clickX, clickY);



		g.setFill(new Color(0, 0, 0.2, 0.7));
		g.fillRect(0, 0, scene.getWidth(), scene.getHeight());
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
