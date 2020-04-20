package daysgame;

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
public class SplashPane extends VBox {
        private Scene scene;
        private FadeTransition ft;
        
        /**
         * The pane constructor.
         * The pane is initialized with an image and an animation that causes the image to fade in and out
         * @param scene The window on which the pane will be displayed.
         */
        SplashPane(Scene scene) {
                ImageView iv = new ImageView();
                System.out.println(SplashPane.class.getResource("/img").toExternalForm());
                iv.setImage(new Image(SplashPane.class.getResource("/img/sierratech-logo.png").toExternalForm()));
                iv.setPreserveRatio(true);
                iv.setFitHeight(scene.getHeight()*0.9);
                
                
                this.scene = scene;
                ft = new FadeTransition(Duration.millis(3000), iv);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.setCycleCount(2);
                ft.setAutoReverse(true);
                ft.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                                Main.setPane(scene, "Main Menu");
                                ((MainMenuPane)Main.panes.get("Main Menu")).getParallelTransition().play();
                        }
                });
                
                getChildren().add(iv);
                
                getStylesheets().add(SplashPane.class.getResource("/stylesheet.css").toExternalForm());
                
                setAlignment(Pos.CENTER);
                setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
                
                
        }
        
        /**
         * @returns The FadeTransition object that contains the fade transition for the logo
         */
        public FadeTransition getft(){
                return ft;
        }
        
}
