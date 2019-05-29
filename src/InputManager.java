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

	InputManager(Game game, World world, Player player) {
		this.game = game;
		this.world = world;
		this.player = player;
		pressed = new EnumMap<KeyCode, Boolean>(KeyCode.class){{
				for(KeyCode k : KeyCode.values())
					put(k, false);
			}};
	}

	public void keyPressed(KeyEvent e) {
		if(e.getCode() == KeyCode.Q) {
			game.stop();
			Main.setPane(game.getScene(), "Main Menu");
		}
		if(!pressed.get(KeyCode.W) && e.getCode() == KeyCode.W) {
			vy += 1;
			player.setFacing(Direction.UP);
		} else if(!pressed.get(KeyCode.S) && e.getCode() == KeyCode.S) {
			vy += -1;
			player.setFacing(Direction.DOWN);
		} else if(!pressed.get(KeyCode.D) && e.getCode() == KeyCode.D) {
			vx += 1;
			player.setFacing(Direction.RIGHT);
		} else if(!pressed.get(KeyCode.A) && e.getCode() == KeyCode.A) {
			vx += -1;
			player.setFacing(Direction.LEFT);
		}
		pressed.put(e.getCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		if(pressed.get(KeyCode.W) && e.getCode() == KeyCode.W) {
			vy -= 1;
		} else if(pressed.get(KeyCode.S) && e.getCode() == KeyCode.S) {
			vy -= -1;
		} else if(pressed.get(KeyCode.D) && e.getCode() == KeyCode.D) {
			vx -= 1;
		} else if(pressed.get(KeyCode.A) && e.getCode() == KeyCode.A) {
			vx -= -1;
		} else if(pressed.get(KeyCode.SPACE) && e.getCode() == KeyCode.SPACE) {
			Point2D p = player.getPosition();
			world.destroyBlock((int)p.getX(), (int)p.getY(), 1);
		}
		pressed.put(e.getCode(), false);
	}

	public Point2D getDirection() {
		return new Point2D(vx, vy).normalize();
	}
}
