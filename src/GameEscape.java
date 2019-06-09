/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 */

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.nio.file.attribute.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import javafx.util.*;
import java.awt.Point;

/**
 * This class sets up the escape stage of the game
 */
public class GameEscape extends Game {
	private boolean planeFlown;

	/**
	 * Class constructor, calls the super class constructor
	 * @param scene The scene on which the game is displayed
	 * @param worldPath The file path to the world save files
	 * @param onWin The code that will be executed once the stage has been completed
	 */
	GameEscape(Scene scene, Path worldPath, Runnable onWin) {
		super(scene, worldPath, onWin);
		try {
			planeFlown = Paths.get(worldPath.toString(), "plane").toFile().exists();
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void handle(long time) {
		super.handle(time);
		if(/*getWorld().getStopwatch().getElapsed() > 600 * 36 * 1e9 &&*/ !planeFlown) {
			try {
				worldPath.resolve("plane").toFile().createNewFile();
			}
			catch (Throwable e) {
				System.out.println("Error " + e.getMessage());
				e.printStackTrace();
			}

			ZoomTransition zoomOut = new ZoomTransition(this);
			zoomOut.play();
		}
	}

	/**
	 * Initializes the quests for the escape stage
	 *
	 * Variables:
	 *
	 * survive      -This is a Quest object that contains the sole quest for the escape stage
	 *
	 */
	public void initialiseQuests() {
		Quest survive = new Quest (questManager,
					     "Survive",
					     "You will need to survive 36 days to win the game, don't move too far and keep a signal fire going to increase the chances of the plane finding you.",
					     1,
					     ResourceManager.getItem(ItemKey.FLINTSTEEL),
					     null,
					     null,
					     null);

		questManager.addQuest(survive);
		questManager.startQuest(survive);
	}
}

class ZoomTransition extends Transition {
	Camera camera = null;

	ZoomTransition(Game game) {
		camera = game.getCamera();
		setCycleDuration(new Duration(1000));
		setCycleCount(1);
	}

	public void interpolate(double t) {
		camera.setBlockFactor((1 - t) * 200);
	}
}
