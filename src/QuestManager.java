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
import javafx.concurrent.*;

import java.util.*;

public class QuestManager {
	private List<Quest> quests = new LinkedList<Quest>();

	public void addQuest(Quest q) {
		quests.add(q);
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public void startQuest(VBox ui, Quest q) {
		ui.getChildren().add(q.getQuestPane());
		q.start();
		new Thread(() -> {
				try {
					q.join();
					Platform.runLater(() -> ui.getChildren().remove(q.getQuestPane()));
				} catch(Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
		}).start();
	}
}
