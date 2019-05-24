/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 * This class is used to display the main menu
 */

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.util.*;
import javafx.animation.*;
import javafx.util.Duration;
/**
 * A pane displaying the main menu.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class MainMenuPane extends VBox {
 Stage stage;
 public static final int SWIDTH = 1095;
 public static final int SHEIGHT = 718;

 
 
 
 /**
  * The class constructor.
  * The pane is initialised with a list of buttons allowing the user to travel to the other panes of the program.
  * 
  * Variables:
  * tt       -A HashMap containing all the TranslateTransitions
  * delays   -A HashMap containing all the delays for the TranslateTransitions to stagger the animations
  * buttons  -A HashMap containing all the buttons for the main menu
  * pt       -A ParallelTransition containing all of the TranslateTransitions
  * n        -An int used to incrememt the delays
  *
  * @param stage The window on which the pane will be displayed.
  */
 MainMenuPane(Stage stage) {
   stage.setResizable(false);
   stage.setHeight(SHEIGHT);
   stage.setWidth(SWIDTH);
   Map<String, TranslateTransition> tt = new HashMap<String, TranslateTransition>();
   Map<String, Integer> delays = new HashMap<String, Integer>();
   Map<String, Button> buttons = new HashMap<String, Button>();
   ParallelTransition pt = new ParallelTransition();
   int n = 0;
   StackPane sp = new StackPane();
   Button muteButton, unmuteButton;
  
   muteButton = new Button("");
   muteButton.setOnAction(event -> {
     Main.mediaPlayer.setVolume(0);
     muteButton.toBack();});
   muteButton.setId("unmutebutton");
   muteButton.setMaxWidth(1);
  
   unmuteButton = new Button ("");
   unmuteButton.setOnAction(event -> {
     Main.mediaPlayer.setVolume(100);
     muteButton.toFront();});
   unmuteButton.setId("mutebutton");
  
   sp.getChildren().add(unmuteButton);
   sp.getChildren().add(muteButton);
  
  
   for(String s : new String[]{"Level Select", "Survival Guide", "High Scores", "About", "Exit"}){
     delays.put(s, n);
     n+=500;
   }
   
  for(String s : new String[]{"Level Select", "Survival Guide", "High Scores", "About", "Exit"}){
    buttons.put(s, new Button(s){{
      tt.put(s, new TranslateTransition(Duration.millis(1000), this));
      tt.get(s).setFromX(getLayoutX()-1000);
      tt.get(s).setToX(getLayoutX());
      tt.get(s).setDelay(Duration.millis(delays.get(s)));
      setCache(true);
      setCacheHint(CacheHint.SPEED);
      pt.getChildren().add(tt.get(s));
      setOnAction(e -> stage.setScene(Main.scenes.get(s)));
   }});
  }
  
  setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
  setAlignment(Pos.CENTER);
  setPadding(new Insets(50, 50, 50, 50));
  setSpacing(10);
  this.stage = stage;
  getChildren().add(new Label("36 Days - Wilderness Survival Game"));
   for(String s : new String[]{"Level Select", "Survival Guide", "High Scores", "About"}){
     getChildren().add(buttons.get(s));
   }
  
  
  
  
  getChildren().add(new Button("Exit"){{
    tt.put("Exit", new TranslateTransition(Duration.millis(1000), this));
    tt.get("Exit").setFromX(getLayoutX()-1000);
    tt.get("Exit").setToX(getLayoutX());
    tt.get("Exit").setDelay(Duration.millis(delays.get("Exit")));
    pt.getChildren().add(tt.get("Exit"));
   setOnAction(e -> stage.close());
  }});
  
  getChildren().add(sp);
  
  pt.play();
 }
}
