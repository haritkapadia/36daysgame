package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 */

import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.geometry.Pos;
import java.util.*;
import javafx.scene.image.*;
import java.util.concurrent.atomic.*;

/**
 * A pane displaying instructions on how to play the game
 *
 * @author Harit Kapadia, Jack Farley
 */
public class InstructionsPane extends VBox {
        private Scene scene;
        
        /**
         * The pane constructor.
         * The pane is initialised with a title, instructions, and buttons for navigating the instructions
         * 
         * Variables:
         * 
         * pages     -Stores each page of instructions as a VBox
         * sp        -A StackPane containing the instructions, used to easily add and remove pages from the screen
         * currentPage -An AtomicInteger variable that stores the page number that is currently being viewed
         *
         * @param scene The window on which the pane will be displayed.
         * @param stage The stage on which the scene is displayed
         */
        InstructionsPane(Scene scene, Stage stage) {
                ArrayList<VBox> pages = new ArrayList<VBox>();
                StackPane sp = new StackPane();
                AtomicInteger currentPage = new AtomicInteger(0);
                pages.add(new VBox(){{
                        setSpacing(10);
                        getChildren().add(instruction (scene, "To move, left click the tile that you want to move to.", "/img/clicktomove.gif", 1));
                        getChildren().add(instruction(scene, "To break an object (only some objects are breakable), left click the object that you want to break.", "/img/breaktree.gif", 1));
                        getChildren().add(instruction(scene, "To pick up an object, right click the object that you want to pick up.", "/img/pickupwood.gif", 2));
                }});
                pages.add(new VBox(){{
                        setSpacing(10);
                        getChildren().add(instruction(scene, "To open the survival guide click on the book icon at the top right corner of the screen.", "/img/openguide.gif", 1));
                        getChildren().add(instruction(scene, "To swap items in your inventory click on the item that you want to move then click on the slot that you'd like to move it to.", "/img/swapinventory.gif", 1));
                        getChildren().add(instruction(scene, "To discard an item from your inventory hold the ctrl key then press the key that corresponds with the item you want to discard.", "/img/dropitem.gif", 4));
                }});
                pages.add(new VBox(){{
                        setSpacing(10);
                        getChildren().add(instruction(scene, "To make flint and steel, place your knife on the ground, hover over the item with your mouse pointer, and press the key that corresponds with the flint item in your toolbar.", "/img/makeflintsteel.gif", 3)); 
                        getChildren().add(instruction(scene, "To start a fire, first create a flint and steel item and add it to your toolbar, then hover over a wood block with your mouse and press the key that corresponds with the flint and steel item in your toolbar.", "/img/makeafire.gif", 3)); 
                        getChildren().add(instruction(scene, "To find and eat food right click the food object (insects are invisible until you pick them up) and then press the key that corresponds with the food item in your toolbar.", "/img/eatfood.gif", 3));
                }});
                pages.add(new VBox(){{
                        setSpacing(10);
                        getChildren().add(instruction(scene, "To get water, hover your mouse pointer over a water block and press the key that corresponds with your water bottle in your toolbar. To purify the water place the water bottle on the ground, hover over it with your mouse point and press the key that corresponds with your water purification tablets in your toolbar. To drink the water pick up the water bottle and press the key that corresponds with the water bottle in your toolbar.", "/img/getwater.gif", 0)); 
                        getChildren().add(instruction(scene, "To build a shelter place seven palisade blocks in an open ring. To place a palisade hover your mouse over a grass block and press the key that corresponds with wood in your toolbar. To make a bed pick up a Cattail then hover your mouse over the empty spot in your shelter and press the key that corresponds with the Cattail in your toolbar. To sleep, right click the bed.", "/img/makeashelter.gif", 0)); 
                }});
                this.scene = scene;
                getChildren().add(new HBox(){{
                        getChildren().add(new Button(){{
                                setId("prevbutton");
                                setOnAction(e -> {
                                        if(currentPage.get()>0){
                                                currentPage.addAndGet(-1);
                                                sp.getChildren().add(pages.get(currentPage.get()));
                                                sp.getChildren().remove(pages.get(currentPage.get()+1));
                                        }
                                });
                        }});
                        getChildren().add(new VBox(){{
                                getChildren().add(new Label("Instructions"){{setId("bigtitle");}});
                                getChildren().add(sp);
                                sp.getChildren().add(pages.get(0));
                                getChildren().add(new Button("Return"){{
                                        setOnAction(e -> Main.setPane(scene, "Main Menu"));
                                }});
                                
                                setAlignment(Pos.TOP_CENTER);
                                
                                setPadding(new Insets(50, 100, 50, 100));
                                setSpacing(30);
                        }});
                        getChildren().add(new Button(){{
                                setId("nextbutton");
                                setOnAction(e -> {
                                        if(currentPage.get()<pages.size()-1){
                                                currentPage.addAndGet(1);
                                                sp.getChildren().add(pages.get(currentPage.get()));
                                                sp.getChildren().remove(pages.get(currentPage.get()-1));
                                        }
                                });
                        }});
                        setAlignment(Pos.CENTER);
                }});
                setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
                                
                getStylesheets().add(InstructionsPane.class.getResource("/stylesheet.css").toExternalForm());
        }
        
        /**
         * @returns an instruction in the form of an HBox containing text and an accompanying animation
         * @param scene is the Scene on which the instructions are displayed, used to calculate the relative size of the nodes
         * @param text is the text that explain how to accomplish a task
         * @param fileName is the name of the GIF file that accompanies the text
         * @param mouse is used to determine whether the screen recording should be accompanied by an animation of a mouse right clicking or left clicking
         */
        private HBox instruction (Scene scene, String text, String fileName, int mouse){
                return new HBox(){{
                        getChildren().add(new VBox(){{
                                setPrefHeight(scene.getHeight()/5);
                                setPrefWidth(scene.getWidth()/2);
                                setAlignment(Pos.CENTER_LEFT);
                                getChildren().add(new Label(text){{
                                        setWrapText(true);
                                }});
                        }});
                        getChildren().add(new StackPane(){{
                                ImageView click = null;
                                setHeight(scene.getHeight()/5);
                                getChildren().add(new ImageView(){{
                                    setImage(new Image(InstructionsPane.class.getResource(fileName).toExternalForm()));
                                        setPreserveRatio(true);
                                        setFitHeight (scene.getHeight()/5 );
                                }});
                                if(mouse==1){
                                        click = new ImageView(){{
                                            setImage(new Image(InstructionsPane.class.getResource("/img/leftclick.gif").toExternalForm()));
                                                setPreserveRatio(true);
                                                setFitHeight (scene.getHeight()/12);
                                        }};
                                }else if (mouse == 2){
                                        click = new ImageView(){{
                                            setImage(new Image(InstructionsPane.class.getResource("/img/rightclick.gif").toExternalForm()));
                                                setPreserveRatio(true);
                                                setFitHeight (scene.getHeight()/12);
                                        }};
                                }else if (mouse == 3){
                                        click = new ImageView(){{
                                            setImage(new Image(InstructionsPane.class.getResource("/img/key_q.png").toExternalForm()));
                                                setPreserveRatio(true);
                                                setFitHeight(scene.getHeight()/18);
                                        }};
                                }else if (mouse == 4){
                                        click = new ImageView(){{
                                            setImage(new Image(InstructionsPane.class.getResource("/img/ctrl_q.png").toExternalForm()));
                                                setPreserveRatio(true);
                                                setFitHeight(scene.getHeight()/18);
                                        }};
                                }
                                if(click != null){
                                        getChildren().add(click);
                                        setAlignment(click, Pos.BOTTOM_RIGHT);
                                }
                        }});
                        setAlignment(Pos.CENTER);
                        setSpacing(50);
                }};
        }
}
