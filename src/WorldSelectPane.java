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

public class WorldSelectPane extends VBox {
	WorldSelectPane(Scene scene, Path basePath) {
		getChildren().add(new Label("World Select"));
		File[] worlds = new File[0];
		if(basePath.resolve("worlds").resolve("scores").toFile().exists()) {
			 worlds = Paths.get(basePath.toString(), "worlds").toFile().listFiles();
			 System.out.println("worlds exists");

		} else {
			basePath.resolve("worlds").toFile().mkdirs();
			System.out.println("Made worlds");

		}
		VBox worldSelect = new VBox();
		for(File f : worlds) {
			if(f.getName().matches("\\d+"))
				worldSelect.getChildren().add(new WorldEnterPane(scene, worldSelect, f.toPath()));
		}
		getChildren().add(new ScrollPane(){{
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

		getStylesheets().add("stylesheet.css");
	}
}

class WorldEnterPane extends HBox {
	WorldEnterPane(Scene scene, VBox node, Path worldPath) {
		String worldName = "null";
		try {
			worldName = new String(Files.readAllBytes(worldPath.resolve("name")), Charset.forName("UTF-8"));
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
		getChildren().add(new Label(worldName){{
			// HBox.setHgrow(this, Priority.ALWAYS);
		}});
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

		getStylesheets().add("stylesheet.css");
	}
}

class WorldCreationPane extends VBox {
	WorldCreationPane(Scene scene, Path basePath) {
		long seed = (int)(Math.random() * Integer.MAX_VALUE);
		getChildren().add(new Label("Create New World"));
		TextField worldName = new TextField(){{
			setPromptText("Enter desired world name here...");
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
		setPadding(new Insets(50, 50, 50, 50));
		setSpacing(10);

		getStylesheets().add("stylesheet.css");
	}
}
