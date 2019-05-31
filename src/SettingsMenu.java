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
 * A pane that displays the logo fading in and out
 * 
 * Variables:
 * 
 * scene      -A Scene object that is a reference to the main scene
 * ft         -A FadeTransition object that contains the fade transition for the logo
 *
 * @author Harit Kapadia, Jack Farley
 */
public class SettingsMenu extends StackPane {
        public static HBox menuBar;
        public Stage menuStage;
        public Scene menuScene;
        
        public static int getMenuWidth(int buttons){
                return buttons*30+(buttons-1)*30 + 50;
        }
                
        
        public static void initButtons(Scene scene, Stage stage){
                SettingsMenu.menuBar = new HBox(){{
                        setStyle("-fx-background-color: transparent;");
                        setManaged(false);
                        getChildren().add(new ToggleButton(){{
                                setId("mutebutton");
                                //setSelected(Main.muteControl.getValue());
                                setOnAction(e -> Main.muteControl.setValue(!Main.muteControl.getValue()));
                        }});
                        getChildren().add(new Button(){{
                                setId("exitbutton");
                                setOnAction(e -> Main.setPane(scene, "Main Menu"));
                        }});
                        setSpacing(30);
                }};
        }
        
        
        /**
         * The pane constructor.
         * The pane is initialized with an image and an animation that causes the image to fade in and out
         * @param scene The window on which the pane will be displayed.
         * @param stage The stage on which the scene is displayed
         */
        SettingsMenu (Scene scene, Stage stage, Boolean mainMenu) {
                setManaged(false);
                setPrefWidth(scene.getWidth());
                setPrefHeight(50);
                getChildren().add(SettingsMenu.menuBar);
                getStylesheets().add("stylesheet.css");
        }
        
        
        SettingsMenu (Scene scene, Stage stage) {
                setManaged(false);
                setPrefWidth(scene.getWidth());
                setPrefHeight(50);
                SettingsMenu.menuBar.getChildren().add(new Button(){{
                        setId("pausebutton");
                }});
                getChildren().add(SettingsMenu.menuBar); 
                getStylesheets().add("stylesheet.css");     
                
        }
        
}
