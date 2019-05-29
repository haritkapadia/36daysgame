import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;

public abstract class Quest extends Thread {
	private String questName;
	private String description;
	private int maxSteps;
	private int stepsTaken;
	private ProgressBar progressBar;
	private VBox questPane;

	Quest(String questName, String description, int maxSteps) {
		this.questName = questName;
		this.description = description;
		this.maxSteps = maxSteps;
		stepsTaken = 0;
		progressBar = new ProgressBar(0);
		questPane = new VBox(){{
			getChildren().addAll(new Label(questName), new Label(description), progressBar);
		}};
	}

	public void addStep() {
		stepsTaken += 1;
		progressBar.setProgress((double)stepsTaken / maxSteps);
	}

	public String getQuestName() {
		return questName;
	}

	public String getDescription() {
		return description;
	}

	public int getMaxSteps() {
		return maxSteps;
	}

	public int getStepsTaken() {
		return stepsTaken;
	}

	public VBox getQuestPane() {
		return questPane;
	}
}
