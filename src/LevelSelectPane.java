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
import javafx.stage.*;
import javafx.geometry.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.nio.file.attribute.*;

/**
 * A pane allowing the selection of the playing of three levels.
 *
 * @param Harit Kapadia, Jack Farley
 */
public class LevelSelectPane extends VBox {
	private Scene scene;

	/**
	 * The pane constructor.
	 * The pane is initialised with the level description and entry for each level.
	 *
	 * @param scene The window on which the pane will be displayed.
	 * @param stage The stage on which the scene is displayed
	 */
	LevelSelectPane(Scene scene, Stage stage, Path worldPath) {
		this.scene = scene;
		getChildren().add(new Label("Level Select"){{
			setId("title");
		}});
		getChildren().add(new HBox(){{
			getChildren().add(new LevelPane(scene, worldPath, "Deficiency", "Knowledge is wealth.", stage, () -> {
						try {
							writeScore(worldPath, "Deficiency");
							Main.copyPath(Paths.get(worldPath.toString(), "Deficiency"), Paths.get(worldPath.toString(), "Panic"));
						}
						catch (Throwable qwer) {
							System.out.println("Error " + qwer.getMessage());
							qwer.printStackTrace();
						}

			}){{
				setAlignment(Pos.TOP_CENTER);
			}});
			getChildren().add(new LevelPane(scene, worldPath, "Panic", "Stop and smell the roses.", stage, () -> {
						try {
							writeScore(worldPath, "Panic");
							Main.copyPath(Paths.get(worldPath.toString(), "Panic"), Paths.get(worldPath.toString(), "Escape"));
						}
						catch (Throwable qwer) {
							System.out.println("Error " + qwer.getMessage());
							qwer.printStackTrace();
						}

			}){{
				setAlignment(Pos.TOP_CENTER);
			}});
			getChildren().add(new LevelPane(scene, worldPath, "Escape", "Stay for a while.", stage, () -> {
						try {
							writeScore(worldPath, "Escape");
						}
						catch (Throwable e) {
							System.out.println("Error " + e.getMessage());
							e.printStackTrace();
						}

			}){{
				setAlignment(Pos.TOP_CENTER);
			}});
			setAlignment(Pos.CENTER);
			setPadding(new Insets(0, 0, 0, -50));
			setSpacing(50);
		}});
		getChildren().add(new Button("Return"){{
			setOnAction(e -> Main.setPane(scene, "Main Menu"));
		}});

		getStylesheets().add("stylesheet.css");

		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));

		setPadding(new Insets(-50, 50, 50, 50));
		setSpacing(30);

	}

	private void writeScore(Path worldPath, String level) throws Throwable {
		String[] gameScore = new String(Files.readAllBytes(Paths.get(worldPath.toString(), level, "score")), Charset.forName("UTF-8")).split("\t");
		List<String> highScores = Files.readAllLines(Paths.get(worldPath.getParent().toString(), "scores", level));
		List<String> names = new LinkedList<String>();
		List<Integer> scores = new LinkedList<Integer>();
		for(String s : highScores) {
			String[] entry = s.split("\t");
			names.add(entry[0]);
			scores.add(Integer.parseInt(entry[1]));
		}
		names.add(gameScore[0]);
		scores.add(Integer.parseInt(gameScore[1]));

		for(int i = 0; i < scores.size(); i++) {
			int max = i;
			for(int j = i + 1; j < scores.size(); j++)
				if(scores.get(j).compareTo(scores.get(max)) > 0)
					max = j;
			Collections.swap(names, i, max);
			Collections.swap(scores, i, max);
		}

		highScores = new ArrayList<String>(highScores.size());
		for(int i = 0; i < highScores.size(); i++)
			highScores.add(names.get(i) + "\t" + scores.get(i));
		Files.write(Paths.get(worldPath.getParent().toString(), "scores", level), highScores, Charset.forName("UTF-8"));
	}
}

/**
 * A class representing a level selection pane.
 *
 * @author Harit Kapadia, Jack Farley
 * @see LevelSelectPane
 */
class LevelPane extends VBox {
	private Scene scene;

	/**
	 * The class constructor.
	 * The pane is initialised with a level name, description, and a button to play the level.
	 *
	 * @param scene The window on which the pane will be displayed.
	 * @param name The name of the level.
	 * @param description The description of the level.
	 * @param playable The state of playability of the level.
	 */
	LevelPane(Scene scene, Path worldPath, String name, String description, Stage stage, Runnable onWin) {
		getChildren().add(new Label(name){{
			setId("heading");
		}});
		getChildren().add(new Label(description));
		setSpacing(10);
		if(Paths.get(worldPath.toString(), name).toFile().exists()) {
			getChildren().add(new Button("Enter"){{
				setOnAction(e -> {
						Main.setPane(scene, new Game(scene, Paths.get(worldPath.toString(), name), onWin){{
							start();
						}}.getPane());
					});
			}});
		} else {
			getChildren().add(new Label("Locked"){{
				setPadding(new Insets(5,0,0,0));
			}});
		}
	}
}
