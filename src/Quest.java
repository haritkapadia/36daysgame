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

public class Quest extends Thread {
	private String questName;
	private String description;
	private int maxSteps;
	private int stepsTaken;
	private ProgressBar progressBar;
	private VBox questPane;
	private Quest[] nextQuests;
	private Object waitOn;
	private QuestManager questManager;

	Quest(QuestManager questManager, String questName, String description, int maxSteps, Object waitOn, Quest[] nextQuests) {
		this.questManager = questManager;
		this.questName = questName;
		this.description = description;
		this.maxSteps = maxSteps;
		this.waitOn = waitOn;
		this.nextQuests = nextQuests;
		stepsTaken = 0;
		progressBar = new ProgressBar(0);
		questPane = new VBox(){{
			getChildren().addAll(new Label(questName), new Label(description), progressBar);
		}};
	}

	public void run() {
		for(; stepsTaken < maxSteps; addStep()) {
			synchronized(waitOn) {
				try {
					waitOn.wait();
				} catch(Exception e) {
					System.out.println(questName);
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		Platform.runLater(() -> questManager.removeQuest(this));
		if(nextQuests != null)
			for(Quest q : nextQuests)
				Platform.runLater(() -> questManager.startQuest(q));
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
