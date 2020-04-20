package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
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
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import javafx.util.*;
import java.awt.Point;

/**
 * This class sets up the escape stage of the game
 */
public class GameEscape extends Game {
	private boolean planeFlown;
	SequentialTransition planeTransition;

	/**
	 * Class constructor, calls the super class constructor
	 * @param scene The scene on which the game is displayed
	 * @param worldPath The file path to the world save files
	 * @param onWin The code that will be executed once the stage has been completed
	 */
	GameEscape(Scene scene, Path worldPath, Runnable onWin) {
		super(scene, worldPath, onWin);
		try {
			planeFlown = Paths.get(worldPath.toString(), "plane").toFile().exists();
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void handle(long time) {
		super.handle(time);
		if(getWorld().getStopwatch().getElapsed() > 600 * 36 * 1e9 && !planeFlown) {
			try {
				worldPath.resolve("plane").toFile().createNewFile();
			}
			catch (Throwable e) {
				System.out.println("Error " + e.getMessage());
				e.printStackTrace();
			}
			ImageView plane = new ImageView(GameEscape.class.getResource("/img/bushplane.png").toExternalForm());
			plane.setTranslateX(scene.getWidth());
			gamePane.getChildren().add(plane);
			Rectangle blackBox = new Rectangle(0, 0, scene.getWidth(), scene.getHeight());
			blackBox.setFill(Color.BLACK);
			blackBox.setOpacity(0);
			gamePane.getChildren().add(blackBox);
			VBox congratulations = new VBox(){{
				getChildren().add(new Label("Congratulations."){{
					setStyle("-fx-font-size: 30px; -fx-padding: 15px; -fx-border-insets: 15px; -fx-background-insets: 15px;");
				}});
				getChildren().add(new Label("You survived 36 days"){{
					setStyle("-fx-font-size: 30px; -fx-padding: 15px; -fx-border-insets: 15px; -fx-background-insets: 15px;");
				}});
				getChildren().add(new Label("in the wilderness."){{
					setStyle("-fx-font-size: 30px; -fx-padding: 15px; -fx-border-insets: 15px; -fx-background-insets: 15px;");
				}});
				getChildren().add(new Label("You are rescued."){{
					setStyle("-fx-font-size: 30px; -fx-padding: 15px; -fx-border-insets: 15px; -fx-background-insets: 15px;");
				}});
				getChildren().add(new Button("Return to Main Menu"){{
					setOnAction(g -> {
							killQuests();
							stop();
							getWorld().write();
							scene.setCursor(null);
							Main.setPane(scene, "Main Menu");
							Main.settingsMenu.removeGameButtons();
						});
				}});
				setAlignment(Pos.CENTER);
				getStylesheets().add(GameEscape.class.getResource("/stylesheet.css").toExternalForm());
				setOpacity(0);
			}};
			gamePane.getChildren().add(congratulations);
			TranslateTransition tt = new TranslateTransition(new Duration(3000), plane);
			tt.setFromX(scene.getWidth());
			tt.setToX(100);
			tt.setCycleCount(1);
			FadeTransition ft1 = new FadeTransition(new Duration(1000), blackBox);
			ft1.setFromValue(0);
			ft1.setToValue(1);
			FadeTransition ft2 = new FadeTransition(new Duration(1000), congratulations);
			ft2.setFromValue(0);
			ft2.setToValue(1);
			planeTransition = new SequentialTransition(new ZoomTransition(this), tt, new PauseTransition(new Duration(500)), ft1, ft2);
			planeTransition.play();
			planeFlown = true;
			synchronized(this) {
				notifyAll();
			}
		}
	}

	/**
	 * Initializes the quests for the escape stage
	 *
	 * Variables:
	 *
	 * survive      -This is a Quest object that contains the sole quest for the escape stage
	 *
	 */
	public void initialiseQuests() {
		Quest survive = new Quest (questManager,
					   "Survive",
					   "You will need to survive 36 days to win the game, don't move too far and keep a signal fire going to increase the chances of the plane finding you.",
					   1,
					   this,
					   null,
					   null,
					   null);
		questManager.addQuest(survive);
		questManager.startQuest(survive);
	}
}

class ZoomTransition extends Transition {
	Camera camera = null;
	Game game;

	ZoomTransition(Game game) {
		this.game = game;
		camera = game.getCamera();
		setCycleDuration(new Duration(5000));
		setCycleCount(1);
	}

	public void interpolate(double t) {
		camera.setBlockFactor(t * 200 + 30);
		if(t == 1)
			game.stop();
	}
}
