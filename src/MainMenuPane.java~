import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class MainMenuPane extends VBox {
	Stage stage;

	MainMenuPane(Stage stage) {
		this.stage = stage;
		// getColumnConstraints.add(new ColumnConstraints(){{
		//         setHGrow(Priority.NEVER);
		// }});
		getChildren().add(new Label("36 Days - Wilderness Survival Game"));
		for(String s : new String[]{"Level Select", "Survival Guide", "High Scores", "About"})
			getChildren().add(new Button(s){{
				setOnAction(e -> stage.setScene(Main.scenes.get(s)));
			}});
		getChildren().add(new Button("Exit"){{
			setOnAction(e -> stage.close());
		}});
	}
}
