/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 */

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

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
	 */
	HighScoresPane(Scene scene, Path basePath) {
		this.scene = scene;
		try {
			String out = "Tier 1\t1000\n" +
				"Tier 2\t900\n" +
				"Tier 3\t800\n" +
				"Tier 4\t700\n" +
				"Tier 5\t600\n" +
				"Tier 6\t500\n" +
				"Tier 7\t400\n" +
				"Tier 8\t300\n" +
				"Tier 9\t200\n" +
				"Tier 10\t100";
			if(!basePath.resolve("scores").toFile().exists())
				basePath.resolve("scores").toFile().mkdirs();
			if(!basePath.resolve("scores").resolve("Deficiency").toFile().exists())
				Files.write(basePath.resolve("scores").resolve("Deficiency"), out.getBytes("UTF-8"));
			if(!basePath.resolve("scores").resolve("Panic").toFile().exists())
				Files.write(basePath.resolve("scores").resolve("Panic"), out.getBytes("UTF-8"));
			if(!basePath.resolve("scores").resolve("Escape").toFile().exists())
				Files.write(basePath.resolve("scores").resolve("Escape"), out.getBytes("UTF-8"));

		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}

		getChildren().add(new Label("High Scores"){{setId("title");}});
		getChildren().add(new HBox(){{
			setAlignment(Pos.CENTER);
			setSpacing(50);
			try {
				getChildren().add(new HighScores(scene, "Deficiency", basePath.resolve("scores").resolve("Deficiency").toFile()));
				getChildren().add(new HighScores(scene, "Panic", basePath.resolve("scores").resolve("Panic").toFile()));
				getChildren().add(new HighScores(scene, "Escape", basePath.resolve("scores").resolve("Escape").toFile()));
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
