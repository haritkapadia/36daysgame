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
	private final double SPEED = 10;
	EnumMap<KeyCode, Boolean> pressed;
	Game game;
	Player player;

	InputManager(Game game, Player player) {
		this.game = game;
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
			vy += SPEED;
			player.setFacing(Direction.UP);
			player.play();
		} else if(!pressed.get(KeyCode.S) && e.getCode() == KeyCode.S) {
			vy += -SPEED;
			player.setFacing(Direction.DOWN);
			player.play();
		} else if(!pressed.get(KeyCode.D) && e.getCode() == KeyCode.D) {
			vx += SPEED;
			player.setFacing(Direction.RIGHT);
			player.play();
		} else if(!pressed.get(KeyCode.A) && e.getCode() == KeyCode.A) {
			vx += -SPEED;
			player.setFacing(Direction.LEFT);
			player.play();
		}
		pressed.put(e.getCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		if(pressed.get(KeyCode.W) && e.getCode() == KeyCode.W) {
			vy -= SPEED;
			player.stop();
		} else if(pressed.get(KeyCode.S) && e.getCode() == KeyCode.S) {
			vy -= -SPEED;
			player.stop();
		} else if(pressed.get(KeyCode.D) && e.getCode() == KeyCode.D) {
			vx -= SPEED;
			player.stop();
		} else if(pressed.get(KeyCode.A) && e.getCode() == KeyCode.A) {
			vx -= -SPEED;
			player.stop();
		}
		pressed.put(e.getCode(), false);
	}

	public Point2D getDisplacement() {
		return new Point2D(vx, vy);
	}
}
