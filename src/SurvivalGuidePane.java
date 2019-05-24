/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 * This class is used to display the survival guide
 */

import java.util.*;
import java.util.concurrent.atomic.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.web.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.beans.*;
import javafx.beans.value.*;
import javafx.concurrent.*;
import javafx.geometry.Insets;

/**
 * A pane displaying the survival guide.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class SurvivalGuidePane extends HBox {
 private Stage stage;

 /**
  * The class constructor.
  * The pane is initialised with placeholders for the survival guide.
  * 
  * Variables:
  * stackPane        -A StackPane which allows nodes to be stacked on top of eachother, contains the buttons Pane and the guide HBox
  * buttons          -A Pane containing the buttons
  * leftPage         -A WebView used to display the left page in the survival guide
  * rightPage        -A Webview used to display the right page in the survival guide
  * pages            -An array of WebViews used to store all of the pages in the survival guide
  * pageCount        -An AtomicInteger used to store the total number of pages in the survival guide
  * page             -An AtomicInteger used to store the current page number that the user is viewing
  * guide            -An HBox used to contain the survival guide pages
  * returnButton     -A Button which when pressed will return the user to the main menu/game
  * nextButton       -A Button which when pressed will display the next pages
  * prevButton       -A Button which when pressed will display the previous pages
  *
  * @param stage The window on which the pane will be displayed.
  */
 SurvivalGuidePane(Stage stage) {
   
   StackPane stackPane = new StackPane();
   Pane buttons = new Pane();
   WebView leftPage = new WebView();
   WebView rightPage = new WebView();
   WebView[] pages = new WebView[]{leftPage, rightPage};
   AtomicInteger pageCount = new AtomicInteger();
   AtomicInteger page = new AtomicInteger(0);
   HBox guide = new HBox(5);
  
   this.stage = stage;
  
   for(int i : new int[]{0, 1}) {
     pages[i].setContextMenuEnabled(false);
     pages[i].getEngine().load(this.getClass().getResource("/guide.html").toString());
     pages[i].getEngine().getLoadWorker().stateProperty().addListener((ov, t, t1) -> {
     if(t1 == Worker.State.SUCCEEDED) {
       pages[i].getEngine().executeScript("showDiv(" + i + ")");
        pageCount.set(((Integer)leftPage.getEngine().executeScript("document.getElementById('b').children.length")).intValue());
     }
    });
  }
  
  rightPage.setMaxSize(MainMenuPane.SWIDTH/2.8, MainMenuPane.SHEIGHT*0.9);
  leftPage.setMaxSize(MainMenuPane.SWIDTH/2.8, MainMenuPane.SHEIGHT*0.85);
  guide.getChildren().add(leftPage);
  guide.getChildren().add(rightPage);
  guide.setMargin(leftPage, new Insets(20, 50, 20, 50));
  guide.setMargin(rightPage, new Insets(20, 50, 20, 150));
  
  stackPane.getChildren().add(guide);
  
  Button returnButton = new Button(""){{
    setId("returnbutton");
    setLayoutX(100);
    setLayoutY(MainMenuPane.SHEIGHT-100);
    setOnAction(e -> stage.setScene(Main.scenes.get("Main Menu")));
  }};
  
  Button nextButton = new Button(""){{
    setId("nextbutton");
    setLayoutX(MainMenuPane.SWIDTH-100);
    setLayoutY(MainMenuPane.SHEIGHT-100);
    setOnAction((e) -> {
      if(page.get() < pageCount.get() - 2) {
        page.addAndGet(2);
        leftPage.getEngine().executeScript("showDiv(" + (page.get()) + ")");
        rightPage.getEngine().executeScript("showDiv(" + (page.get() + 1) + ")");
        if (page.get()==pageCount.get()-2)
          setVisible(false);
      }
     });
   }};
  
  Button prevButton = new Button(""){{
    setId("prevbutton");
    setLayoutX(30);
    setLayoutY(MainMenuPane.SHEIGHT-100);
    setOnAction((e) -> {
      if(page.get() >= 2) {
       page.addAndGet(-2);
       leftPage.getEngine().executeScript("showDiv(" + (page.get()) + ")");
       rightPage.getEngine().executeScript("showDiv(" + (page.get() + 1) + ")");
       nextButton.setVisible(true);
      }
     });
   }};
  
  buttons.getChildren().add(nextButton);
  buttons.getChildren().add(prevButton);
  buttons.getChildren().add(returnButton);
  stackPane.getChildren().add(buttons);
  getChildren().add(stackPane);
 }
}
