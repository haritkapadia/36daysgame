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
 * A pane that contains buttons to change the game settings
 *
 * Variables:
 *
 * menuBar     -The HBox that contains the buttons
 * menuStage   -The stage on which the menu is located
 * pauseButton -The button used to pause the game
 * guideButton -The button used to display the Survival Guide
 * speedButton -The button used to alter the Stopwatch speed in the game
 *
 * @author Harit Kapadia, Jack Farley
 */
public class SettingsMenu extends StackPane {
        public static HBox menuBar;
        private Stage menuStage;
        private Scene menuScene;
        private Button pauseButton;
        private Button guideButton;
        private ToggleButton speedButton;
        
        /**
         * @returns the size of the menu for a given number of buttons
         * @param buttons is the number of buttons that the menu contains
         */
        public static int getMenuWidth(int buttons){
                return buttons*30+(buttons-1)*30 + 50;
        }
        
        /**
         * Initializes the menuBar HBox for the Main Menu pane
         * @param scene is the Scene on which everything is displayed
         * @param stage is the Stage which contains the Scene
         */
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
         * Adds the buttons that are only used in the game to the menuBar
         * @param gamePane is the pane which contains the menuBar
         * @param game is a reference to the main Game object
         */
        public void addGameButtons(StackPane gamePane, Game game){
                pauseButton = new Button(){{
                        setId("pausebutton");
                        setOnAction (e -> {
                                PauseMenu pauseMenu = new PauseMenu(menuScene, menuStage, game);
                                gamePane.getChildren().add(pauseMenu);
                                gamePane.setAlignment(pauseMenu, Pos.CENTER);
                                game.pause();
                        });
                }};
                guideButton = new Button(){{
                        setId("guidebutton");
                        setOnAction (e -> {
                                game.pause();
                                game.gameSurvivalGuide.toFront();
                                game.gameSurvivalGuide.setVisible(true);
                        });
                }};
                speedButton = new ToggleButton(){{
                        setId("speedbutton");
                        setOnAction (e -> {
                                game.getWorld().getStopwatch().setSpeed(2);
                        });
                }};
                SettingsMenu.menuBar.getChildren().addAll(pauseButton, guideButton, speedButton);
        }
        
        /**
         * Removes the buttons that are not used in the main menu
         */
        public void removeGameButtons(){
                SettingsMenu.menuBar.getChildren().remove(pauseButton);
                SettingsMenu.menuBar.getChildren().remove(guideButton);
                SettingsMenu.menuBar.getChildren().remove(speedButton);
        }
        
        /**
         * The pane constructor.
         * The pane is initialized with the buttons that are used in the main menu
         */
        SettingsMenu (Scene scene, Stage stage, Boolean mainMenu) {
                menuScene = scene;
                menuStage = stage;
                setManaged(false);
                setPrefWidth(scene.getWidth());
                setPrefHeight(50);
                getChildren().add(SettingsMenu.menuBar);
                getStylesheets().add("stylesheet.css");
        }
        
}
