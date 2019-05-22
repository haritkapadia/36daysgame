/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 */

import java.util.*;
import java.util.concurrent.atomic.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.web.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.beans.*;
import javafx.beans.value.*;
import javafx.concurrent.*;

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
		WebView leftPage = new WebView();
		WebView rightPage = new WebView();
		WebView[] pages = new WebView[]{leftPage, rightPage};
		AtomicInteger pageCount = new AtomicInteger();
		for(int i : new int[]{0, 1}) {
			pages[i].setContextMenuEnabled(false);
			pages[i].getEngine().load(this.getClass().getResource("/guide.html").toString());
			pages[i].getEngine().getLoadWorker().stateProperty().addListener((ov, t, t1) -> {
					if(t1 == Worker.State.SUCCEEDED) {
						pages[i].getEngine().executeScript("showDiv(" + i + ")");
						pageCount.set(((Integer)leftPage.getEngine().executeScript("document.getElementById('b').children.length")).intValue());
					}
				});
		}
		getChildren().add(new VBox(){{
			AtomicInteger page = new AtomicInteger(0);
			getChildren().add(new Button("Return"){{
				setOnAction(e -> stage.setScene(Main.scenes.get("Main Menu")));
			}});
			getChildren().add(new Button("Next Page"){{
				setOnAction((e) -> {
						if(page.get() < pageCount.get() - 2) {
							page.addAndGet(2);
							leftPage.getEngine().executeScript("showDiv(" + (page.get()) + ")");
							rightPage.getEngine().executeScript("showDiv(" + (page.get() + 1) + ")");
						}
					});
			}});
			getChildren().add(new Button("Previous Page"){{
				setOnAction((e) -> {
						if(page.get() >= 2) {
							page.addAndGet(-2);
							leftPage.getEngine().executeScript("showDiv(" + (page.get()) + ")");
							rightPage.getEngine().executeScript("showDiv(" + (page.get() + 1) + ")");
						}
					});
			}});
		}});
		getChildren().add(new HBox(5){{
			setStyle("-fx-background-color: #000000");
			getChildren().add(leftPage);
			getChildren().add(rightPage);
		}});
	}
}
