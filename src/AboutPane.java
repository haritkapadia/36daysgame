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
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

/**
 * A pane displaying information about the game and its creators.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class AboutPane extends VBox {
	private Stage stage;

	/**
	 * The pane constructor.
	 * The pane is initialised with a title, the company logo, information about the creators, and a button to return to the main menu.
	 *
	 * @param stage The window on which the pane will be displayed.
	 */
	AboutPane(Stage stage) {
		this.stage = stage;
		getChildren().add(new Label("About"));
		getChildren().add(new ImageView(){{
			setImage(new Image("sierratech-logo.png"));
		}});
		getChildren().add(new Label("Game and assets created by Harit Kapadia and Jack Farley."));
		getChildren().add(new Button("Return"){{
			setOnAction(e -> stage.setScene(Main.scenes.get("Main Menu")));
		}});
	}
}
