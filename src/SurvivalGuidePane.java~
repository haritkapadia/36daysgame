import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class SurvivalGuidePane extends HBox {
	private Stage stage;

	SurvivalGuidePane(Stage stage) {
		this.stage = stage;
		getChildren().add(new VBox());
		getChildren().add(new HBox(){{
			getChildren().add(new Label("Left Page"));
			getChildren().add(new Label("Right Page"));
		}});
	}
}

class SurvivalGuideBook {

}

class SurvivalGuideSection {
	private String title;
	private SurvivalGuideContent[] content;

	SurvivalGuideSection(String title, SurvivalGuideContent[] content) {
		this.title = title;
		this.content = content;
	}
}

class SurvivalGuideContent {
	private String text;
	private String type;

	SurvivalGuideContent(String text, String type) {
		this.text = text;
		this.type = type;
	}
}
