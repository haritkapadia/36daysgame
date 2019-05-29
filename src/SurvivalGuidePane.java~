/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 * This class is used to display the survival guide
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
import javafx.geometry.*;

/**
 * A pane displaying the survival guide.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class SurvivalGuidePane extends StackPane {
	private Scene scene;

	/**
	 * The class constructor.
	 * The pane is initialised with placeholders for the survival guide.
	 *
	 * @param scene The window on which the pane will be displayed.
	 */
	SurvivalGuidePane(Scene scene) {
		this.scene = scene;
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
		getChildren().add(new HBox(){{
			double margin = scene.getWidth() * 50.0 / 1095;
			for(WebView w : pages) {
				getChildren().add(w);
				HBox.setMargin(w, new Insets(margin, margin, margin, margin));
			}
			setSpacing(scene.getWidth() * 60.0 / 1095);
		}});
		getChildren().add(new BorderPane(){{
			setBottom(new HBox(){{
				BorderPane.setAlignment(this, Pos.BOTTOM_CENTER);
				AtomicInteger page = new AtomicInteger(0);
				getChildren().add(new Button(""){{
					setId("prevbutton");
					setOnAction((e) -> {
							if(page.get() >= 2) {
								page.addAndGet(-2);
								leftPage.getEngine().executeScript("showDiv(" + (page.get()) + ")");
								rightPage.getEngine().executeScript("showDiv(" + (page.get() + 1) + ")");
							}
						});
				}});
				getChildren().add(new Button(""){{
					setId("returnbutton");
					setOnAction(e -> Main.setPane(scene, "Main Menu"));
					HBox.setMargin(this, new Insets(0, 30, 0, 30));
				}});
				getChildren().add(new Region(){{
					setPrefWidth(10000000); // large number
					HBox.setHgrow(this, Priority.ALWAYS);
				}});
				getChildren().add(new Button(""){{
					setId("nextbutton");
					setOnAction((e) -> {
							if(page.get() < pageCount.get() - 2) {
								page.addAndGet(2);
								leftPage.getEngine().executeScript("showDiv(" + (page.get()) + ")");
								rightPage.getEngine().executeScript("showDiv(" + (page.get() + 1) + ")");
							}
						});
				}});
			}});
			StackPane.setMargin(this, new Insets(50, 50, 50, 50));
		}});
		getStylesheets().addAll(this.getClass().getResource("Survival Guide Stylesheet.css").toExternalForm());
	}
}
