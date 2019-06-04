/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

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
import javafx.beans.property.*;
import javafx.util.*;
import java.util.*;

public class Quest extends Thread {
        private String questName;
        private String description;
        private int maxSteps;
        private int stepsTaken;
        private ProgressBar progressBar;
        private StackPane questPane;
        private Quest[] nextQuests;
        private Object waitOn;
        private QuestManager questManager;
        private SequentialTransition sequence;
        
        private Timeline textAnimation (String str, Label text){
                final IntegerProperty i = new SimpleIntegerProperty(0);
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().add(new KeyFrame(
                                                         Duration.seconds(0.05),
                                                         event -> {
                        if (i.get() > str.length()) {
                                super.stop();
                        } else {
                                text.setText(str.substring(0, i.get()));
                                i.set(i.get() + 1);
                        }
                }));
                timeline.setCycleCount(str.length()+1);
                return timeline;
        }
        
        Quest(QuestManager questManager, String questName, String description, int maxSteps, Object waitOn, Quest[] nextQuests) {
                sequence = new SequentialTransition();
                this.questManager = questManager;
                this.questName = questName;
                this.description = description;
                this.maxSteps = maxSteps;
                this.waitOn = waitOn;
                this.nextQuests = nextQuests;
                stepsTaken = 0;
                progressBar = new ProgressBar(0);
                questPane = new StackPane(){{
                        setMargin(this, new Insets(50,50,50,50));
                        getChildren().add(new Rectangle(){{
                                setManaged(false);
                                setWidth(450);
                                setHeight(300);
                                setOpacity(0.5);
                        }});
                        getChildren().add(new VBox(){{
                                //getChildren().addAll(new Label(questName), new Label(description), progressBar);
                                Label qname = new Label(){{
                                        setStyle("-fx-font-size: 18px;");
                                }};
                                Label desc = new Label(){{
                                        setMaxWidth(300);
                                        setWrapText(true);
                                }};
                                FadeTransition ft = new FadeTransition(Duration.millis(500), progressBar);
                                ft.setFromValue(0);
                                ft.setToValue(1);
                                getChildren().add(new Label("Quest"){{
                                        setStyle("-fx-font-size: 24px;");
                                }});
                                getChildren().add(qname);
                                getChildren().add(desc);
                                getChildren().add(progressBar);
                                progressBar.setOpacity(0);
                                sequence.getChildren().add(textAnimation(questName, qname));
                                sequence.getChildren().add(textAnimation(description, desc));
                                sequence.getChildren().add(ft);
                                getStylesheets().add("gamestylesheet.css");
                                setPadding(new Insets(50,50,50,50));
                                setSpacing(10);
                        }});
                }};
        }
        
        public void run() {
                sequence.play();
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
        
        public StackPane getQuestPane() {
                return questPane;
        }
}
