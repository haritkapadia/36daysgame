/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
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
	MouseButton clickButton;

	InputManager(Game game, World world, Player player) {
		this.game = game;
		this.world = world;
		this.player = player;
		clickPosition = player.getPosition();
		mouseX = game.getScene().getWidth() / 2;
		mouseY = game.getScene().getHeight() / 2;
		clickButton = null;
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
		BlockKey b = world.getBlock((int)p.getX(), (int)p.getY(), 1);
		double angle = Math.atan2(clickPosition.getY() - player.getY(), clickPosition.getX() - player.getX());

		clickPosition = new Point2D(p.getX() + 0.5, p.getY() + 0.5);
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
		clickButton = e.getButton();
	}

	public Point2D getDesiredDisplacement() {
		Point2D displacement = clickPosition.subtract(player.getPosition());
		return displacement;
	}

	public void resetClick() {
		clickPosition = Main.point2d(player.getPosition());
		clickButton = null;
	}

	public Point2D getWorldMouseCoordinates() {
		return screenToWorldCoordinate(mouseX, mouseY);
	}

	public MouseButton getClickButton() {
		return clickButton;
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
		Point2D mousePosition = Main.toPoint2D(World.blockCoordinate(screenToWorldCoordinate(mouseX, mouseY)));
		if(e.getCode() == KeyCode.P) {
			game.killQuests();
			game.stop();
			Main.setPane(game.getScene(), "Main Menu");
			// try {
			//	Files.write(Paths.get("player.save"), Arrays.asList(new String[]{player.getAsString()}), Charset.forName("UTF-8"));
			//	world.writeChunks();
			// } catch(Exception ee) {
			//	ee.printStackTrace();
			//	System.exit(0);
			// }
			world.write();
		}
		if(!pressed.get(KeyCode.Q) && e.getCode() == KeyCode.Q) {
			player.useTool(0, mousePosition);
		} else if(!pressed.get(KeyCode.W) && e.getCode() == KeyCode.W) {
			player.useTool(1, mousePosition);
		} else if(!pressed.get(KeyCode.E) && e.getCode() == KeyCode.E) {
			player.useTool(2, mousePosition);
		} else if(!pressed.get(KeyCode.R) && e.getCode() == KeyCode.R) {
			player.useTool(3, mousePosition);
		} else if(!pressed.get(KeyCode.T) && e.getCode() == KeyCode.T) {
			player.useTool(4, mousePosition);
		}
		pressed.put(e.getCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		pressed.put(e.getCode(), false);
	}
}
