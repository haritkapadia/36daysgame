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

/**
 * A pane displaying high scores for each mode.
 *
 * @author Harit Kapadia, Jack Farley
 * @see LevelSelectPane
 */
public class HighScoresPane extends BorderPane {
	private Stage stage;

	/**
	 * The pane constructor.
	 * The pane is initialised with a title, the high scores for each level, and a button to return to the main menu.
	 *
	 * @param stage The window on which the pane will be displayed.
	 */
	HighScoresPane(Stage stage) {
		this.stage = stage;
		setTop(new Label("High Scores"));
		setCenter(new HBox(){{
			try {
				getChildren().add(new HighScores(stage, "Deficiency", new File("deficiency.dat")));
				getChildren().add(new HighScores(stage, "Panic", new File("deficiency.dat")));
				getChildren().add(new HighScores(stage, "Escape", new File("deficiency.dat")));
			} catch(IOException e) {
				e.printStackTrace();
				stage.close();
			}
		}});
		setBottom(new Button("Return"){{
			setOnAction(e -> stage.setScene(Main.scenes.get("Main Menu")));
		}});
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
	 * @param stage The window on which the pane will be displayed.
	 * @param name The name of the level.
	 * @param file The file from which to read the high scores from.
	 */
	HighScores(Stage stage, String name, File file) throws IOException {
		List<String[]> scores = new ArrayList<String[]>();
		new BufferedReader(new FileReader(file)){{
			String line;
			while((line = readLine()) != null)
				scores.add(line.split("\t"));
		}}.close();
		getChildren().add(new Label(name));
		getChildren().add(new GridPane(){{
			for(int i = 0; i < scores.size(); i++) {
				int c = 0;
				add(new Label(Integer.toString(i + 1)), c++, i);
				for(String s : scores.get(i))
					add(new Label(s), c++, i);
			}
		}});
	}
}
