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
        private VBox ui;
        
        public QuestManager(VBox ui) {
                this.ui = ui;
        }
        
        public void addQuest(Quest q) {
                quests.add(q);
        }
        
        public void removeQuest(Quest q) {
                ui.getChildren().remove(q.getQuestPane());
                quests.remove(q);
        }
        
        public void killQuests() {
                for(Quest q : quests)
                        q.stop();
        }
        
        public List<Quest> getQuests() {
                return quests;
        }
        
        public void startQuest(Quest q) {
                ui.getChildren().add(q.getQuestPane());
                q.start();
        }
}
