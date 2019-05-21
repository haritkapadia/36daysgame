import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class AboutPane extends VBox {
	private Stage stage;

	AboutPane(Stage stage) {
		this.stage = stage;
		getChildren().add(new Label("About"));
		getChildren().add(new ImageView(){{
			setImage(new Image("sierratech-logo.png"));
		}});
		getChildren().add(new Label("Game and assets created by Harit Kapadia and Jack Farley."));
		getChildren().add(new Button("Return"){{
			setOnAction(e -> stage.setScene(Main.scenes.get("Main Menu")));
		}});
	}
}
