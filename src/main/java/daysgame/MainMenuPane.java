package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 * This class is used to display the main menu
 */

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.nio.file.attribute.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.util.concurrent.atomic.*;
import javafx.util.Duration;
/**
 * A pane displaying the main menu.
 *
 * @author Harit Kapadia, Jack Farley
 *
 * Variables:
 *
 * scene     -Stores a reference to the scene on which everything is displayed
 * stage     -Stores a reference to the stage which contains the scene
 * pt        -Contains all the transitions used in this class
 */
public class MainMenuPane extends VBox {
	private Scene scene;
	private Stage stage;
	private ParallelTransition pt;

	/**
	 * The class constructor.
	 * The pane is initialised with a list of buttons allowing the user to travel to the other panes of the program.
	 *
	 * @param scene The window on which the pane will be displayed.
	 * @param stage The stage on which the scene is displayed
	 *
	 * Variables:
	 *
	 * n   -An AtomicInteger object used to stagger the TranslateTransitions
	 */
	MainMenuPane(Scene scene, Stage stage) {
		AtomicInteger n = new AtomicInteger();

		this.scene = scene;
		this.stage = stage;
		pt = new ParallelTransition();


		getChildren().add(new Label("36 Days - Wilderness Survival Game"){{
			FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
			ft.setFromValue(0);
			ft.setToValue(1);
			setId("bigtitle");
			pt.getChildren().add(ft);

		}});
		//world select button
		getChildren().add(new Button("World Select"){{
			TranslateTransition t = new TranslateTransition(Duration.millis(1000), this);
			t.setFromX(getLayoutX()-1000);
			t.setToX(getLayoutX());
			t.setDelay(Duration.millis(n.get()));
			pt.getChildren().add(t);
			setCache(true);
			setCacheHint(CacheHint.SPEED);
			setOnAction(e -> Main.setPane(scene, new WorldSelectPane(scene, Main.rootPath)));
		}});
		n.addAndGet(500);

		//world select button
		getChildren().add(new Button("High Scores"){{
			TranslateTransition t = new TranslateTransition(Duration.millis(1000), this);
			t.setFromX(getLayoutX()-1000);
			t.setToX(getLayoutX());
			t.setDelay(Duration.millis(n.get()));
			pt.getChildren().add(t);
			setCache(true);
			setCacheHint(CacheHint.SPEED);
			setOnAction(e -> Main.setPane(scene, new HighScoresPane(scene, Main.rootPath.resolve("worlds"))));
		}});
		n.addAndGet(500);

		for(String s : new String[]{"Survival Guide", "About", "Instructions"}) {
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
		n.addAndGet(500);


		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setAlignment(Pos.CENTER);
		setPadding(new Insets(50, 50, 50, 50));
		setSpacing(10);

		getStylesheets().add(MainMenuPane.class.getResource("/stylesheet.css").toExternalForm());
	}

	/**
	 * @returns The ParallelTransition object that stores all the transitions used in the class
	 */
	public ParallelTransition getParallelTransition() {
		return pt;
	}
}
