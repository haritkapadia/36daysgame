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

	InputManager() {
		pressed = new EnumMap<KeyCode, Boolean>(KeyCode.class);
	}

	public void keyPressed(KeyEvent e) {
		pressed.set(e.getKeyCode(), true);
	}
}
