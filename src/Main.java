/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 */

import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

/**
 * The main class, creating the window, scenes, and starting the program.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class Main extends Application {
	public static HashMap<String, Parent> panes;

	public static void setPane(Scene scene, String name) {
		scene.setRoot(panes.get(name));
	}

	public static void setPane(Scene scene, Parent node) {
		scene.setRoot(node);
	}

	/**
	 * Initialises the scene map in preparation of the full program.
	 */
	@Override
	public void init() {
		panes = new HashMap<String, Parent>();
	}

	/**
	 * Starts the program.
	 *
	 * @param primaryStage The window on which the program will run.
	 */
	@Override
	public void start(Stage primaryStage) {
		Scene main = new Scene(new BorderPane());

		panes.put("Main Menu", new MainMenuPane(main));
		panes.put("Level Select", new LevelSelectPane(main));
		panes.put("High Scores", new HighScoresPane(main));
		panes.put("About", new AboutPane(main));
		panes.put("Survival Guide", new SurvivalGuidePane(main));
		// panes.put("Game", new Game(main));

		primaryStage.setTitle("36 Days - Wilderness Survival Game");
		primaryStage.setScene(main);
		Main.setPane(main, "Main Menu");
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	/**
	 * The main method.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		launch();
	}

}
