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
  */
 AboutPane(Scene scene) {
  this.scene = scene;
  getChildren().add(new Label("About"){{setId("bigtitle");}});
  
  getChildren().add(new HBox(){{
    getChildren().add(new ImageView(){{
      setImage(new Image("sierratech-logo.png"){{
        setPreserveRatio(true);
        setFitWidth((scene.getWidth()-50)/2);
      }});
    }});
  
    getChildren().add(new Label("Game and assets created by Harit Kapadia and Jack Farley. The game has many interesting properties such as blah blah blah."){{
      setWrapText(true);
      setWidth((scene.getWidth()-50)/2);
      setPadding(new Insets(-50, 50, 50, 50));
    }});
    setAlignment(Pos.CENTER);
  }});
  
  getChildren().add(new Button("Return"){{
   setOnAction(e -> Main.setPane(scene, "Main Menu"));
  }});
  
  getStylesheets().add("stylesheet.css");
  
  setAlignment(Pos.CENTER);
  setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
  
  setPadding(new Insets(-50, 50, 50, 50));
  setSpacing(30);
 }
}
