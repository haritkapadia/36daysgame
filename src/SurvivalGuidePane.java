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

/**
 * A pane displaying the survival guide.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class SurvivalGuidePane extends HBox {
	private Stage stage;

	/**
	 * The class constructor.
	 * The pane is initialised with placeholders for the survival guide.
	 *
	 * @param stage The window on which the pane will be displayed.
	 */
	SurvivalGuidePane(Stage stage) {
		this.stage = stage;
		getChildren().add(new VBox());
		getChildren().add(new HBox(){{
			getChildren().add(new Label("Left Page"));
			getChildren().add(new Label("Right Page"));
		}});
	}
}

// class SurvivalGuideBook {

// }

// class SurvivalGuideSection {
//	private String title;
//	private SurvivalGuideContent[] content;

//	SurvivalGuideSection(String title, SurvivalGuideContent[] content) {
//		this.title = title;
//		this.content = content;
//	}
// }

// class SurvivalGuideContent {
//	private String text;
//	private String type;

//	SurvivalGuideContent(String text, String type) {
//		this.text = text;
//		this.type = type;
//	}
// }
