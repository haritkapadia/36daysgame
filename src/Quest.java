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

/**
 * This class creates a quest which is a set of instructions for the user to carry out
 *
 * Variables:
 *
 * questName     -Stores the name of the quest
 * description   -Stores a description of what the user must do
 * maxSteps      -Stores the amount of times the user must accomplish a task
 * stepsTaken    -Stores the amount of times the user has accomplished the task
 * progressBar   -Used to display the amount of progress for a given quest
 * questPane     -The StackPane upon which the quests are displayed
 * nextQuests    -An array of Quest objects that are to follow the quest
 * waitOn        -The quest waits for this object to call the notifyAll method before marking a task as complete
 * questManager  -The QuestManager object that manages all of the quests
 * sequence      -The SequentialTransition object used to animate the questPane
 * instr         -The name of the instructions that accompany the quest
 * helpMenu      -A reference to the HelpMenu object that displays the instructions accompanying the quests
 */
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
        private String instr;
        private HelpMenu helpMenu;
        
        /**
         * @returns Creates an animation of the text appearing on the screen
         * @param str is the text that is to be animated
         * @param text is the label upon which the text is displayed
         * 
         * Variables:
         * 
         * i             -An IntegerProperty object used to increment the frames
         * timeLine      -A TimeLine object which stores the animation
         * 
         */
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
        
        /**
         * Class constructor used to initialize the quests
         * @param questtManager is the QuestManager object that manages all of the quests
         * @param questName is the name of the quest
         * @param description is the set of instructions that the user must follow
         * @param maxSteps is the amount of times that the user must accomplish a task
         * @param waitOn is the object that will call notifyAll when the task has been accomplished
         * @param nextQuests is an array that contains the quest which is to follow
         * @param instr is the name of the instructions which accompany the quest
         * @param helpMenu is the pane which displays the instructions that accompany the quest
         */
        Quest(QuestManager questManager, String questName, String description, int maxSteps, Object waitOn, Quest[] nextQuests, String instr, HelpMenu helpMenu) {
                sequence = new SequentialTransition();
                this.questManager = questManager;
                this.questName = questName;
                this.description = description;
                this.maxSteps = maxSteps;
                this.waitOn = waitOn;
                this.nextQuests = nextQuests;
                this.instr = instr;
                this.helpMenu = helpMenu;
                stepsTaken = 0;
                progressBar = new ProgressBar(0);
                questPane = new StackPane(){{
                        setMaxWidth(450);
                        setMinHeight(350);
                        setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
                        setMargin(this, new Insets(50,50,50,50));
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
        
        /**
         * Continuously updates the quest as the user accomplishes the tasks
         */
        public void run() {
                questManager.updateCurrentQuest(this);
                sequence.play();
                for(; stepsTaken < maxSteps; addStep()) {
                        synchronized(waitOn) {
                                try {
                                        System.out.println(waitOn + " steps before: " + stepsTaken);
                                        progressBar.setProgress((double)stepsTaken / maxSteps);
                                        questManager.updateCurrentQuest(this);
                                        waitOn.wait();
                                        System.out.println(waitOn + " steps after: " + stepsTaken + "\n");
                                        
                                } catch(Exception e) {
                                        System.out.println(questName);
                                        e.printStackTrace();
                                        System.exit(0);
                                }
                        }
                }
                System.out.println(questName + " removed");
                if(nextQuests != null) {
                        for(Quest q : nextQuests) {
                                System.out.println("Started " + q.getQuestName());
                                questManager.addQuest(q);
                                questManager.startQuest(q);
                        }
                }
                Platform.runLater(() -> questManager.removeQuest(this));
                System.out.println(questName + " reached end");
                
        }
        
        /**
         * Increments the stepsTaken variable by 1
         */
        public void addStep() {
                stepsTaken += 1;
                // progressBar.setProgress((double)stepsTaken / maxSteps);
        }
        
        /**
         * @returns the name of the quest
         */
        public String getQuestName() {
                return questName;
        }
        
        /**
         * @returns the description of the quest
         */
        public String getDescription() {
                return description;
        }
        
        /**
         * @returns the name of the instructions which accompany the quest
         */
        public String getInstr(){
                return instr;
        }
        
        /**
         * @returns the number of times the user must accomplish a task
         */
        public int getMaxSteps() {
                return maxSteps;
        }
        
        /**
         * @returns the number of times the user has accomplished the task
         */
        public int getStepsTaken() {
                return stepsTaken;
        }
        
        /**
         * @returns the HelpMenu pane which displays the quest instructions
         */
        public HelpMenu getHelpMenu(){
                return helpMenu;
        }
        
        /**
         * Sets the number of times the user has accomplished the task
         * @param i is the number of times that stepsTaken will be set to
         */
        public void setStepsTaken(int i) {
                // addStep();
                synchronized(waitOn) {
                        stepsTaken = i;
                        waitOn.notifyAll();
                        System.out.println("text");
                }
        }
        
        /**
         * @returns the pane on which the quests are displayed
         */
        public StackPane getQuestPane() {
                return questPane;
        }
        
        // Fast-forwards the quest to completion
        public void completeQuest() {
                stepsTaken = maxSteps;
                synchronized(waitOn) {
                        waitOn.notifyAll();
                }
        }

    public Quest[] getNextQuests() {
        return nextQuests;
    }
}
