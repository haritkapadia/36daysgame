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
import javafx.geometry.*;
import javafx.geometry.Pos;
import java.util.*;
import javafx.scene.image.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.event.*;

/**
 * A pane that displays the pause menu in the game
 *
 * Variables:
 *
 * scene      -A Scene object that is a reference to the main scene
 * pauseMenu      -Stores a reference to this object
 *
 * @author Harit Kapadia, Jack Farley
 */
public class PauseMenu extends VBox {
	private Scene scene;
	PauseMenu pauseMenu;

	/**
	 * The pane constructor.
	 * The pane is initialized with an exit button and a resume button
	 * @param scene The window on which the pane will be displayed.
	 * @param stage The stage on which the scene is displayed
	 * @param game The Game object that the pause menu pauses
	 *
	 *
	 */
	PauseMenu (Scene scene, Stage stage, Game game) {
		setAlignment(Pos.CENTER);
		setSpacing(30);
		pauseMenu = this;

		getChildren().add(new Button("Exit"){{
			setOnAction(e -> {
					game.killQuests();
					game.stop();
					game.getWorld().write();
					scene.setCursor(null);
					Main.setPane(scene, "Main Menu");
					Main.settingsMenu.removeGameButtons();
				});
		}});

		getChildren().add(new Button("Resume"){{
			setOnAction(e ->{
					game.resume(pauseMenu);
				});
		}});

		getStylesheets().add("stylesheet.css");
		relocate(scene.getWidth()-150,50);


	}

}
