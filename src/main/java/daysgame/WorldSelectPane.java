package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
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
 * A pane allowing the user to create a new world where their progress will be saved
 *
 * @param Harit Kapadia, Jack Farley
 *
 * Variables:
 *
 * worlds     -An array of files that stores the worlds
 */
public class WorldSelectPane extends VBox {
	/**
	 * Class constructor, creates a pane with a title, and buttons
	 * @param scene The Scene on which this is displayed
	 * @param baseBath The file path where the world files are saved
	 */
	WorldSelectPane(Scene scene, Path basePath) {
		getChildren().add(new Label("World Select"){{
			setId("title");
		}});
		File[] worlds = new File[0];
		if(basePath.resolve("worlds").toFile().exists()) {
			worlds = Paths.get(basePath.toString(), "worlds").toFile().listFiles();
			System.out.println("worlds exists");

		} else {
			basePath.resolve("worlds").toFile().mkdirs();
			System.out.println("Made worlds: " + basePath.resolve("worlds").toString());

		}
		VBox worldSelect = new VBox();
		for(File f : worlds) {
			if(f.getName().matches("\\d+"))
				worldSelect.getChildren().add(new WorldEnterPane(scene, worldSelect, f.toPath()));
		}
		getChildren().add(new ScrollPane(){{
			setMaxHeight(scene.getWidth()*0.6);
			setFitToWidth(true);
			setFitToHeight(false);
			setHbarPolicy(ScrollBarPolicy.NEVER);
			setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
			setContent(worldSelect);
		}});
		getChildren().add(new Button("New World"){{
			setOnAction(e -> Main.setPane(scene, new WorldCreationPane(scene, basePath.resolve("worlds"))));
		}});
		getChildren().add(new Button("Return"){{
			setOnAction(e -> Main.setPane(scene, "Main Menu"));
		}});
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setAlignment(Pos.CENTER);
		setPadding(new Insets(50, 50, 50, 50));
		setSpacing(10);
		getStylesheets().add(WorldSelectPane.class.getResource("/stylesheet.css").toExternalForm());
	}
}

/**
 * A pane allowing the user to enter an existing world
 */
class WorldEnterPane extends HBox {
	/**
	 * Class constructor, creates a pane with some buttons and labels
	 * @param scene The Scene object that everything is displayed on
	 * @param node The node that contains this pane
	 * @param worldPath The file path that contains the world save files
	 */
	WorldEnterPane(Scene scene, VBox node, Path worldPath) {
		String worldName = "null";
		try {
			worldName = new String(Files.readAllBytes(worldPath.resolve("name")), Charset.forName("UTF-8"));
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		getChildren().add(new Label(worldName));
		getChildren().add(new Region(){{
			// setPrefWidth(10000000); // large number
			HBox.setHgrow(this, Priority.ALWAYS);
		}});
		Button enter = new Button("Enter"){{
			setOnAction(e -> Main.setPane(scene, new LevelSelectPane(scene, worldPath)));
		}};
		getChildren().add(enter);
		Button delete = new Button("Delete");
		delete.setOnAction(f -> {
			WorldEnterPane.this.getChildren().remove(enter);
			WorldEnterPane.this.getChildren().remove(delete);
			Label confirmMessage = new Label("Are you sure you want to delete this world?");
			Button yes = new Button("Yes");
			Button no = new Button("No");
			WorldEnterPane.this.getChildren().add(confirmMessage);
			WorldEnterPane.this.getChildren().add(yes);
			WorldEnterPane.this.getChildren().add(no);
			no.setOnAction(g -> {
				WorldEnterPane.this.getChildren().remove(confirmMessage);
				WorldEnterPane.this.getChildren().remove(no);
				WorldEnterPane.this.getChildren().remove(yes);
				WorldEnterPane.this.getChildren().add(enter);
				WorldEnterPane.this.getChildren().add(delete);
			});
			yes.setOnAction(g -> {
				try {
					Main.deletePath(worldPath);
					node.getChildren().remove(WorldEnterPane.this);
				}
				catch (Throwable e) {
					System.out.println("Error " + e.getMessage());
					e.printStackTrace();
				}
			});
		});
		getChildren().add(delete);
		setAlignment(Pos.CENTER);
		setPadding(new Insets(10, 10, 10, 10));
		setSpacing(10);
		setBorder(new Border(new BorderStroke(Color.rgb(0, 0, 0, 0.5), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0))));
		getStylesheets().add(WorldSelectPane.class.getResource("/stylesheet.css").toExternalForm());
	}
}

/**
 * A pane allowing the user to enter the name of a new world
 */
class WorldCreationPane extends VBox {
	/**
	 * Class constructor, creates a pane with a title, label, and text field
	 *
	 * @param scene The scene on which everything is displayed
	 * @param basePath The file path to where the world files are saved
	 *
	 * Variables:
	 *
	 * seed    -The number used to randomly generate the World
	 *
	 */
	WorldCreationPane(Scene scene, Path basePath) {
		long seed = (int)(Math.random() * Integer.MAX_VALUE);
		getChildren().add(new Label("Create New World"){{
			setId("title");
		}});
		TextField worldName = new TextField(){{
			setPromptText("Enter desired world name here...");
			setMaxWidth(scene.getWidth()*0.6);
		}};
		getChildren().add(worldName);
		getChildren().add(new Button("Create"){{
			setOnAction(f -> {
				if(!worldName.getText().trim().equals("")) {
					Paths.get(basePath.toString(), "" + seed, "Deficiency").toFile().mkdirs();
					try {
						Files.write(basePath.resolve("" + seed).resolve("name"), worldName.getText().getBytes("UTF-8"));
					}
					catch (Throwable e) {
						System.out.println("Error " + e.getMessage());
						e.printStackTrace();
					}
					System.out.println("Made " + Paths.get(basePath.toString(), "" + seed, "Deficiency").toString());
					Main.setPane(scene, new LevelSelectPane(scene, basePath.resolve("" + seed)));
				}
			});
		}});
		setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
		setAlignment(Pos.CENTER);
		setPadding(new Insets(-50, 50, 50, 50));
		setSpacing(10);

		getStylesheets().add(WorldSelectPane.class.getResource("/stylesheet.css").toExternalForm());
	}
}
