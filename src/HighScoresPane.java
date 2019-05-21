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

public class HighScoresPane extends BorderPane {
	private Stage stage;

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

class HighScores extends VBox {
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
