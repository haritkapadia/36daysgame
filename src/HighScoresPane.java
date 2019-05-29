/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 */

import java.io.*;
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

/**
 * A pane displaying high scores for each mode.
 *
 * @author Harit Kapadia, Jack Farley
 * @see LevelSelectPane
 */
public class HighScoresPane extends VBox {
        private Scene scene;
        
        /**
         * The pane constructor.
         * The pane is initialised with a title, the high scores for each level, and a button to return to the main menu.
         *
         * @param scene The window on which the pane will be displayed.
         * @param stage The stage on which the scene is displayed
         */
        HighScoresPane(Scene scene, Stage stage) {
                this.scene = scene;
                getChildren().add(new Label("High Scores"){{setId("title");}});
                getChildren().add(new SettingsMenu(scene, stage));
                getChildren().add(new HBox(){{
                        setAlignment(Pos.CENTER);
                        setSpacing(50);
                        try {
                                getChildren().add(new HighScores(scene, "Deficiency", new File("deficiency.dat")));
                                getChildren().add(new HighScores(scene, "Panic", new File("deficiency.dat")));
                                getChildren().add(new HighScores(scene, "Escape", new File("deficiency.dat")));
                        } catch(IOException e) {
                                e.printStackTrace();
                        }
                }});
                getChildren().add(new Button("Return"){{
                        setOnAction(e -> Main.setPane(scene, "Main Menu"));
                }});
                
                getStylesheets().add("stylesheet.css");
                
                setAlignment(Pos.CENTER);
                setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
                
                setPadding(new Insets(-50, 50, 50, 50));
                setSpacing(50);
        }
}

/**
 * A class representing one high scores table for a level.
 *
 * @author Harit Kapadia, Jack Farley
 * @see HighScoresPane
 */
class HighScores extends VBox {
        /**
         * The class constructor.
         * The pane is initialised with a list of high scores, read from a file.
         *
         * @param scene The window on which the pane will be displayed.
         * @param name The name of the level.
         * @param file The file from which to read the high scores from.
         */
        HighScores(Scene scene, String name, File file) throws IOException {
                List<String[]> scores = new ArrayList<String[]>();
                new BufferedReader(new FileReader(file)){{
                        String line;
                        while((line = readLine()) != null)
                        scores.add(line.split("\t",2));
                }}.close();
                
                
                getChildren().add(new Label(name));
                getChildren().add(new GridPane(){{
                        getColumnConstraints().addAll(new ColumnConstraints(){{setPercentWidth(20);}}, new ColumnConstraints(){{setPercentWidth(50);}}, new ColumnConstraints(){{setPercentWidth(30);}});
                        
                        for(int i = 0; i < scores.size(); i++) {
                                int c = 0;
                                add(new Label(Integer.toString(i + 1)+". "), c++, i);
                                add(new Label(scores.get(i)[0]), c++, i);
                                add(new Label(scores.get(i)[1]){{GridPane.setHalignment(this,HPos.RIGHT);}}, c++, i);
                        }
                        
                }});
                
                getStylesheets().add("stylesheet.css");
                
                setAlignment(Pos.CENTER);
                setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
                setSpacing(10);
                
        }
}
