/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 * Driver class
 */

import java.io.*;
import java.util.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.*;
import javax.sound.sampled.*;

/**
 * The main class, creating the window, scenes, and starting the program.
 *
 * Variables:
 * panes       -A HashMap used to store all of the panes
 * mediaPlayer -A MediaPlayer used to play the background music
 * muteControl -A BooleanControl variable that is used to mute the background music
 *
 * @author Harit Kapadia, Jack Farley
 */
public class Main extends Application {
        public static HashMap<String, Parent> panes;
        public static MediaPlayer mediaPlayer;
        public static BooleanControl muteControl;
        public static HashMap<String, Parent> stuff;
        public static SettingsMenu settingsMenu;
        
        /**
         * Changes the pane being displayed on the scene
         */
        public static void setPane(Scene scene, String name) {
                scene.setRoot(panes.get(name));
                
                if (!name.equals("Splash Screen")&&!name.equals("Survival Guide")){
                        try{
                                ((Pane)panes.get(name)).getChildren().add(Main.settingsMenu);
                                Main.settingsMenu.relocate(scene.getWidth()-SettingsMenu.getMenuWidth(2),50);
                        }catch(Exception e){
                                System.out.println(e);
                        }
                }
        }
        
        /**
         * Changes the pane being displayed on the scene
         */
        public static void setPane(Scene scene, Parent node) {
                scene.setRoot(node);
                System.out.println(node);
        }
        
        public static Point2D point2d(Point2D p) {
                return new Point2D(p.getX(), p.getY());
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
         * clip         -A Clip variable that is used to play the audio
         *
         * @param primaryStage The window on which the program will run.
         */
        @Override
        public void start(Stage primaryStage) {
                final File MUSIC = new File("Walking-dreamy-bass-synth-loop.wav");
                Clip clip;
                try{
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(MUSIC);
                        
                        clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        clip.start();
                        Main.muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
                        Main.muteControl.setValue(!Main.muteControl.getValue());
                }catch(Exception e){
                        System.out.println(e);
                        System.exit(0);
                }
                
                Scene main = new Scene(new Pane());// placeholder
                SettingsMenu.initButtons(main, primaryStage);
                
                
                primaryStage.setFullScreenExitHint("");
                primaryStage.setFullScreenExitKeyCombination(null);
                primaryStage.setTitle("36 Days - Wilderness Survival Game");
                primaryStage.setScene(main);
                primaryStage.setFullScreen(true);
                primaryStage.show();
                
                panes.put("Level Select", new LevelSelectPane(main, primaryStage));
                panes.put("High Scores", new HighScoresPane(main, primaryStage));
                panes.put("Survival Guide", new SurvivalGuidePane(main, null));
                panes.put("Splash Screen", new SplashPane(main));
                panes.put("Main Menu", new MainMenuPane(main, primaryStage));
                panes.put("About", new AboutPane(main, primaryStage));
                panes.put("Instructions", new InstructionsPane(main, primaryStage));
                
                
                Main.settingsMenu = new SettingsMenu(main, primaryStage, true);
                
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
