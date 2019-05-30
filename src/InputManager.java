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

public class InputManager {
	double vx = 0, vy = 0;
	EnumMap<KeyCode, Boolean> pressed;
	Game game;
	World world;
	Player player;
	Point2D clickPosition;
	double mouseX;
	double mouseY;

	InputManager(Game game, World world, Player player) {
		this.game = game;
		this.world = world;
		this.player = player;
		clickPosition = player.getPosition();
		mouseX = game.getScene().getWidth() / 2;
		mouseY = game.getScene().getHeight() / 2;
		pressed = new EnumMap<KeyCode, Boolean>(KeyCode.class){{
				for(KeyCode k : KeyCode.values())
					put(k, false);
			}};
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
		clickPosition = screenToWorldCoordinate(e.getX(), e.getY());
		Point p = World.blockCoordinate(clickPosition);
		clickPosition = new Point2D(p.getX() + 0.5, p.getY() + 0.5);
		double angle = Math.atan2(clickPosition.getY() - player.getY(), clickPosition.getX() - player.getX());
		if(-Math.PI <= angle && angle < -Math.PI * 3/4)
			player.setFacing(Direction.LEFT);
		else if(-Math.PI * 3/4 <= angle && angle < -Math.PI * 1/4)
			player.setFacing(Direction.DOWN);
		else if(-Math.PI * 1/4 <= angle && angle < Math.PI * 1/4)
			player.setFacing(Direction.RIGHT);
		else if(Math.PI * 1/4 <= angle && angle < Math.PI * 3/4)
			player.setFacing(Direction.UP);
		else if(Math.PI * 3/4 <= angle && angle < Math.PI)
			player.setFacing(Direction.LEFT);

		if(e.getButton() == MouseButton.PRIMARY) {
			Point2D _p = screenToWorldCoordinate(e.getX(), e.getY());
			Point bl = World.blockCoordinate(_p);
			player.destroy((int)bl.getX(), (int)bl.getY(), 1);
		} else if(e.getButton() == MouseButton.SECONDARY) {
			Point2D _p = screenToWorldCoordinate(e.getX(), e.getY());
			Point bl = World.blockCoordinate(_p);
			player.interact((int)bl.getX(), (int)bl.getY(), 1);
		}
	}

	public Point2D getDesiredDisplacement() {
		Point2D displacement = clickPosition.subtract(player.getPosition());
		return displacement;
	}

	public void resetClick() {
		clickPosition = Main.point2d(player.getPosition());
	}

	public Point2D getWorldMouseCoordinates() {
		return screenToWorldCoordinate(mouseX, mouseY);
	}

	public Point2D getMouseCoordinates() {
		return new Point2D(mouseX, mouseY);
	}

	public Point2D getClickPosition() {
		return clickPosition;
	}

	private Point2D screenToWorldCoordinate(double x, double y) {
		Camera camera = game.getCamera();
		Rectangle2D r = camera.getViewBounds(); // works as intended
		Scene scene = game.getScene();
		double maxRatio = Math.max(scene.getWidth(), scene.getHeight()) / Math.max(r.getWidth(), r.getHeight());
		double worldX = (x - scene.getWidth() / 2) / maxRatio + camera.getX();
		double worldY = (scene.getHeight() / 2 - y) / maxRatio + camera.getY();
		return new Point2D(worldX, worldY);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getCode() == KeyCode.Q) {
			game.killQuests();
			game.stop();
			Main.setPane(game.getScene(), "Main Menu");
		}
		// if(!pressed.get(KeyCode.W) && e.getCode() == KeyCode.W) {
		//	vy += 1;
		//	player.setFacing(Direction.UP);
		// } else if(!pressed.get(KeyCode.S) && e.getCode() == KeyCode.S) {
		//	vy += -1;
		//	player.setFacing(Direction.DOWN);
		// } else if(!pressed.get(KeyCode.D) && e.getCode() == KeyCode.D) {
		//	vx += 1;
		//	player.setFacing(Direction.RIGHT);
		// } else if(!pressed.get(KeyCode.A) && e.getCode() == KeyCode.A) {
		//	vx += -1;
		//	player.setFacing(Direction.LEFT);
		// }
		pressed.put(e.getCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		// if(pressed.get(KeyCode.W) && e.getCode() == KeyCode.W) {
		//	vy -= 1;
		// } else if(pressed.get(KeyCode.S) && e.getCode() == KeyCode.S) {
		//	vy -= -1;
		// } else if(pressed.get(KeyCode.D) && e.getCode() == KeyCode.D) {
		//	vx -= 1;
		// } else if(pressed.get(KeyCode.A) && e.getCode() == KeyCode.A) {
		//	vx -= -1;
		// } else if(pressed.get(KeyCode.SPACE) && e.getCode() == KeyCode.SPACE) {
		//	Point2D p = player.getPosition();
		//	world.destroyBlock((int)p.getX(), (int)p.getY(), 1);
		// }
		pressed.put(e.getCode(), false);
	}
}
