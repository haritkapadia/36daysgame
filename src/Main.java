import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.stage.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("36 Days: Wilderness Survival Game");
		Button b = new Button() {{
			setText("Start");
			setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						System.out.println("started");
					}
				});
		}};
		StackPane root = new StackPane();
		root.getChildren().add(b);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
