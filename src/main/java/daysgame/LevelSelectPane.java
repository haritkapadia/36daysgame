package daysgame;

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
	 */
	LevelSelectPane(Scene scene, Path worldPath) {
		this.scene = scene;
		getChildren().add(new Label("Level Select"){{
			setId("title");
		}});
		getChildren().add(new HBox(){{
			getChildren().add(new VBox(){{
				getChildren().add(new Label("Deficiency"){{
					setId("heading");
				}});
				getChildren().add(new Label("Knowledge is wealth."));
				setSpacing(10);
				if(Paths.get(worldPath.toString(), "Deficiency").toFile().exists()) {
					getChildren().add(new Button("Enter"){{
						setOnAction(e -> {
							Main.setPane(scene, new GameDeficiency(scene, Paths.get(worldPath.toString(), "Deficiency"), () -> {
								try {
									writeScore(worldPath, "Deficiency");
									Main.copyPath(Paths.get(worldPath.toString(), "Deficiency"), Paths.get(worldPath.toString(), "Panic"));
								}
								catch (Throwable qwer) {
									System.out.println("Error " + qwer.getMessage());
									qwer.printStackTrace();
								}

							}){{
								start();
							}}.getPane());
						});
					}});
				} else {
					getChildren().add(new Label("Locked"){{
						setPadding(new Insets(5,0,0,0));
					}});
				}
				setMinWidth(250);
				setAlignment(Pos.TOP_CENTER);
			}});
			getChildren().add(new VBox(){{
				getChildren().add(new Label("Panic"){{
					setId("heading");
				}});
				getChildren().add(new Label("Stop and smell the roses."));
				setSpacing(10);
				if(Paths.get(worldPath.toString(), "Panic").toFile().exists()) {
					getChildren().add(new Button("Enter"){{
						setOnAction(e -> {
							Main.setPane(scene, new GamePanic(scene, Paths.get(worldPath.toString(), "Panic"), () -> {
								try {
									writeScore(worldPath, "Panic");
									Main.copyPath(Paths.get(worldPath.toString(), "Panic"), Paths.get(worldPath.toString(), "Escape"));
								}
								catch (Throwable qwer) {
									System.out.println("Error " + qwer.getMessage());
									qwer.printStackTrace();
								}

							}){{
								start();
							}}.getPane());
						});
					}});
				} else {
					getChildren().add(new Label("Locked"){{
						setPadding(new Insets(5,0,0,0));
					}});
				}
				setMinWidth(250);
				setAlignment(Pos.TOP_CENTER);
			}});
			getChildren().add(new VBox(){{
				getChildren().add(new Label("Escape"){{
					setId("heading");
				}});
				getChildren().add(new Label("Stop and smell the roses."));
				setSpacing(10);
				if(Paths.get(worldPath.toString(), "Escape").toFile().exists()) {
					getChildren().add(new Button("Enter"){{
						setOnAction(e -> {
							Main.setPane(scene, new GameEscape(scene, Paths.get(worldPath.toString(), "Escape"), () -> {
								try {
									writeScore(worldPath, "Escape");
								}
								catch (Throwable qwer) {
									System.out.println("Error " + qwer.getMessage());
									qwer.printStackTrace();
								}

							}){{
								start();
							}}.getPane());
						});
					}});
				} else {
					getChildren().add(new Label("Locked"){{
						setPadding(new Insets(5,0,0,0));
					}});
				}
				setMinWidth(250);
				setAlignment(Pos.TOP_CENTER);
			}});
			setAlignment(Pos.CENTER);
			setSpacing(50);
		}});
		getChildren().add(new Button("Return"){{
			setOnAction(e -> Main.setPane(scene, "Main Menu"));
		}});

		getStylesheets().add(LevelSelectPane.class.getResource("/stylesheet.css").toExternalForm());

		setAlignment(Pos.CENTER);
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));

		setPadding(new Insets(-50, 50, 50, 50));
		setSpacing(30);

	}

	/**
	 * Saves the high score to a file
	 *
	 * Variables:
	 *
	 * gameScore     -An array of strings that contains the data from the worldPath
	 * highScores    -A list of Strings that contains the high score data from worldPath
	 * names         -A LinkedList that contains all player names
	 * scores        -A LinkedList that stores all the scores
	 */
	private void writeScore(Path worldPath, String level) throws Throwable {
		String[] gameScore = new String(Files.readAllBytes(Paths.get(worldPath.toString(), level, "score")), Charset.forName("UTF-8")).split("\t");
		List<String> highScores = Files.readAllLines(Paths.get(Main.rootPath.toString(), "scores", level));
		System.out.println("high scores lol: " + Paths.get(worldPath.getParent().toString(), "scores", level));

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
		for(int i = 0; i < scores.size() - 1; i++) {
			System.out.println(names.get(i) + "\t" + scores.get(i));

			highScores.add(names.get(i) + "\t" + scores.get(i));
		}
		Files.write(Paths.get(Main.rootPath.toString(), "scores", level), highScores, Charset.forName("UTF-8"));
	}
}
