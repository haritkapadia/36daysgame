import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class LevelSelectPane extends BorderPane {
	private Stage stage;

	LevelSelectPane(Stage stage) {
		this.stage = stage;
		setTop(new Label("Level Select"));
		setCenter(new HBox(){{
			getChildren().add(new LevelPane(stage, "Deficiency", "Knowledge is wealth.", true));
			getChildren().add(new LevelPane(stage, "Panic", "Stop and smell the roses.", false));
			getChildren().add(new LevelPane(stage, "Escape", "Stay for a while.", false));
		}});
		setBottom(new Button("Return"){{
			setOnAction(e -> stage.setScene(Main.scenes.get("Main Menu")));
		}});
	}
}

class LevelPane extends VBox {
	private Stage stage;

	LevelPane(Stage stage, String name, String description, boolean playable) {
		this.stage = stage;
		getChildren().add(new Label(name));
		getChildren().add(new Label(description));
		if(!playable)
			getChildren().add(new Label("Locked"));
	}
}
