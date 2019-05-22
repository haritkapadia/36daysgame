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
 * A pane allowing the selection of the playing of three levels.
 *
 * @param Harit Kapadia, Jack Farley
 */
public class LevelSelectPane extends BorderPane {
	private Stage stage;

	/**
	 * The pane constructor.
	 * The pane is initialised with the level description and entry for each level.
	 *
	 * @param stage The window on which the pane will be displayed.
	 */
	LevelSelectPane(Stage stage) {
		this.stage = stage;
		setTop(new Label("Level Select"));
		setCenter(new HBox(){{
			getChildren().add(new LevelPane(stage, "Deficiency", "Knowledge is wealth.", true));
			getChildren().add(new LevelPane(stage, "Panic", "Stop and smell the roses.", false));
			getChildren().add(new LevelPane(stage, "Escape", "Stay for a while.", false));
		}});
		setBottom(new Button("Return"){{
			setOnAction(e -> stage.setScene(Main.scenes.get("Main Menu")));
		}});
	}
}

/**
 * A class representing a level selection pane.
 *
 * @author Harit Kapadia, Jack Farley
 * @see LevelSelectPane
 */
class LevelPane extends VBox {
	private Stage stage;

	/**
	 * The class constructor.
	 * The pane is initialised with a level name, description, and a button to play the level.
	 *
	 * @param stage The window on which the pane will be displayed.
	 * @param name The name of the level.
	 * @param description The description of the level.
	 * @param playable The state of playability of the level.
	 */
	LevelPane(Stage stage, String name, String description, boolean playable) {
		this.stage = stage;
		getChildren().add(new Label(name));
		getChildren().add(new Label(description));
		if(playable)
			getChildren().add(new Button("Enter"));
		else
			getChildren().add(new Label("Locked"));
	}
}
