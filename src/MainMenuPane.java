/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 * This class is used to display the main menu
 */

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.util.*;
import javafx.animation.*;
import java.util.concurrent.atomic.*;
import javafx.util.Duration;
/**
 * A pane displaying the main menu.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class MainMenuPane extends VBox {
	Scene scene;
	Stage stage;
	ParallelTransition pt;

	/**
	 * The class constructor.
	 * The pane is initialised with a list of buttons allowing the user to travel to the other panes of the program.
	 *
	 * @param scene The window on which the pane will be displayed.
	 */
	MainMenuPane(Scene scene, Stage stage) {
		this.scene = scene;
		this.stage = stage;
		pt = new ParallelTransition();

		getChildren().add(new Label("36 Days - Wilderness Survival Game"));
		AtomicInteger n = new AtomicInteger();
		for(String s : new String[]{"Level Select", "Survival Guide", "High Scores", "About"}) {
			getChildren().add(new Button(s){{
				TranslateTransition t = new TranslateTransition(Duration.millis(1000), this);
				t.setFromX(getLayoutX()-1000);
				t.setToX(getLayoutX());
				t.setDelay(Duration.millis(n.get()));
				pt.getChildren().add(t);
				setCache(true);
				setCacheHint(CacheHint.SPEED);
				setOnAction(e -> Main.setPane(scene, s));
			}});
			n.addAndGet(500);
		}

		getChildren().add(new ToggleButton(){{
			setId("mutebutton");
			TranslateTransition t = new TranslateTransition(Duration.millis(1000), this);
			t.setFromX(getLayoutX()-1000);
			t.setToX(getLayoutX());
			t.setDelay(Duration.millis(n.get()));
			pt.getChildren().add(t);
			setCache(true);
			setCacheHint(CacheHint.SPEED);
			System.out.println(1-Main.mediaPlayer.getVolume());
			setOnAction(e -> Main.mediaPlayer.setVolume(1-Main.mediaPlayer.getVolume()));
		}});

		n.addAndGet(500);
		//exit button
		getChildren().add(new Button("Exit"){{
			TranslateTransition t = new TranslateTransition(Duration.millis(1000), this);
			t.setFromX(getLayoutX()-1000);
			t.setToX(getLayoutX());
			t.setDelay(Duration.millis(n.get()));
			pt.getChildren().add(t);

			setCache(true);
			setCacheHint(CacheHint.SPEED);
			setOnAction(e -> stage.close());
		}});

		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setAlignment(Pos.CENTER);
		setPadding(new Insets(50, 50, 50, 50));
		setSpacing(10);

		getStylesheets().add("stylesheet.css");
	}

	public ParallelTransition getParallelTransition() {
		return pt;
	}
}
