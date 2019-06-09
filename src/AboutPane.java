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

/**
 * A pane displaying information about the game and its creators.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class AboutPane extends VBox {
	private Scene scene;

	/**
	 * The pane constructor.
	 * The pane is initialised with a title, the company logo, information about the creators, and a button to return to the main menu.
	 *
	 * @param scene The window on which the pane will be displayed.
	 * @param stage The stage on which the scene is displayed
	 */
	AboutPane(Scene scene, Stage stage) {
		this.scene = scene;
		getChildren().add(new Label("About"){{setId("bigtitle");}});

		getChildren().add(new HBox(){{
			getChildren().add(new ImageView(){{
				setImage(new Image("sierratech-logo.png"){{
					setPreserveRatio(true);
					setFitWidth((scene.getWidth()-50)/2);
				}});
			}});

			getChildren().add(new Label("36 Days is an educational role-playing game designed to educate youth on surviving in the wilderness."+
						    " The advice in the game is based off of legitimate research and in theory should hold true in a survival situation."+
						    " But, circumstances differ and this year's peer-reviewed research may be next year's old wives tale."+
						    " Therefore, we advise that you do not try any of the survival techniques taught in this game unless "+
						    "absolutely necessary, and we are not responsible for any injuries, illness or death that may ensue."+
						    " Built in Java with the help of JavaFX 12 for UI components and J3D Texture for an implementation of noise generation." +
						    " Game and assets created by Harit Kapadia and Jack Farley."){{
				setWrapText(true);
				setWidth((scene.getWidth()-50)/2);
				setPadding(new Insets(-50, 50, 50, 50));
			}});
			setAlignment(Pos.CENTER);
		}});

		getChildren().add(new Button("Return"){{
			setOnAction(e -> Main.setPane(scene, "Main Menu"));
		}});

		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));

		getStylesheets().add("stylesheet.css");

		setPadding(new Insets(50, 50, 50, 50));
		setSpacing(30);
	}
}
