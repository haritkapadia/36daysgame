/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 */

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

/**
 * A pane displaying the main menu.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class MainMenuPane extends VBox {
	Scene scene;

	/**
	 * The class constructor.
	 * The pane is initialised with a list of buttons allowing the user to travel to the other panes of the program.
	 *
	 * @param scene The window on which the pane will be displayed.
	 */
	MainMenuPane(Scene scene) {
		this.scene = scene;
		getChildren().add(new Label("36 Days - Wilderness Survival Game"));
		for(String s : new String[]{"Level Select", "Survival Guide", "High Scores", "About"})
			getChildren().add(new Button(s){{
				setOnAction(e -> Main.setPane(scene, s));
			}});
		getChildren().add(new Button("Exit"){{
			//setOnAction(e -> scene.close());
		}});
	}
}
