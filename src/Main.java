/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 * Driver class
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
import javafx.scene.media.*;
import java.io.*;
import javafx.animation.*;
import javafx.util.*;
import javafx.scene.control.*;

/**
 * The main class, creating the window, scenes, and starting the program.
 *
 * Variables:
 * panes       -A HashMap used to store all of the panes
 * mediaPlayer -A MediaPlayer used to play the background music
 *
 * @author Harit Kapadia, Jack Farley
 */
public class Main extends Application {
        public static HashMap<String, Parent> panes;
        public static MediaPlayer mediaPlayer;
        
        /**
         * Changes the pane being displayed on the scene
         */
        public static void setPane(Scene scene, String name) {
                scene.setRoot(panes.get(name));
        }
        
        /**
         * Changes the pane being displayed on the scene
         */
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
         * Variables:
         * 
         * MUSIC        -A File variable that stores the background music file
         *
         * @param primaryStage The window on which the program will run.
         */
        @Override
        public void start(Stage primaryStage) {
                // final File MUSIC = new File("Walking-dreamy-bass-synth-loop.wav");
                final Media MUSIC = new Media(new File("Netherplace.wav").toURI().toString());                
                
                Main.mediaPlayer = new MediaPlayer(MUSIC);
                Main.mediaPlayer.setAutoPlay(true);
                Main.mediaPlayer.setStartTime(Duration.seconds(0));
                Main.mediaPlayer.setStopTime(Duration.INDEFINITE);
                Main.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                
                Scene main = new Scene(new Pane());// placeholder
                
                primaryStage.setFullScreenExitHint("");
                primaryStage.setFullScreenExitKeyCombination(null);
                primaryStage.setTitle("36 Days - Wilderness Survival Game");
                primaryStage.setScene(main);
                primaryStage.setFullScreen(true);
                primaryStage.show();
                
                panes.put("Main Menu", new MainMenuPane(main, primaryStage));
                panes.put("Level Select", new LevelSelectPane(main, primaryStage));
                panes.put("High Scores", new HighScoresPane(main, primaryStage));
                panes.put("About", new AboutPane(main, primaryStage));
                panes.put("Survival Guide", new SurvivalGuidePane(main, primaryStage));
                panes.put("Splash Screen", new SplashPane(main));
                
                
                Main.setPane(main, "Splash Screen");
                ((SplashPane)panes.get("Splash Screen")).ft.play();
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
