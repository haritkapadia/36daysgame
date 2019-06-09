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
import javafx.animation.*;
import javafx.util.Duration;
import javafx.event.*;

/**
 * A pane that displays the in game instructions
 * 
 * Variables:
 * 
 * helpPane      -Contains the in game instructions
 * instructionsColor  -Stores the color of the text in the in game instructions
 * nextButton    -When clicked it will display the next page of instructions
 * instructions  -Stores each page of in game instructions
 *
 * @author Harit Kapadia, Jack Farley
 */
public class HelpMenu extends StackPane {
        private static VBox helpPane;
        private Color instructionsColor = Color.BLACK;
        private Button nextButton;
        private HashMap<String, VBox> instructions;
        
        /**
         * The class constructor, sets up the StackPane
         */
        HelpMenu () {
                instructions = new HashMap<String, VBox>();
                
                nextButton = new Button(){{
                        setId("nextbutton");
                }};
                initInstructions();
                
                setMaxWidth(450);
                setMaxHeight(300);
                setBackground(new Background(new BackgroundFill(new Color(0.961, 0.961, 0.863, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));
                setPadding(new Insets(50,50,50,50));
                
                getStylesheets().add("gamestylesheet.css");
        }
        
        /**
         * Removes the current page of instructions and adds a new one
         */
        public void setInstr(String next){
                getChildren().clear();
                getChildren().add(instructions.get(next));
                if(next.equals("Welcome Message")){
                        getChildren().add(nextButton);
                        nextButton.setOnAction(e -> setInstr("First Instructions"));
                        setAlignment(nextButton, Pos.BOTTOM_RIGHT);
                }
        }
        
        /**
         * Initializes the instructions HashMap
         */
        private void initInstructions(){
                instructions.put("First Instructions", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Label("Instructions"){{
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("To move click the tile that you'd like to move to"){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        getChildren().add(new Text(
                                                   "To break an object left click the object that you'd like to break"){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                                //setStyle("-fx-font-family: \"ChicagoFLF\";  -fx-font-size: 14px;  -fx-text-fill: white;");
                        }});
                        getChildren().add(new Text(
                                                   "To pick up an object right click the object that you'd like to pick up"){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        setSpacing(10);
                }});
                
                instructions.put("Welcome Message", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Label("Welcome"){{
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("Welcome to 36 Days - Wilderness Survival Game"){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        getChildren().add(new Text(
                                                   "This game is designed to simulate getting lost in the woods"){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        getChildren().add(new Text(
                                                   "Your tasks are displayed in the top left corner of the screen, press the arrow to continue"){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        setSpacing(10);
                }});
                
                instructions.put("Pick a Bouquet", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Text("Arranging Inventory and Consulting the Survival Guide"){{
                                setWrappingWidth(270);
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("To swap items in your inventory click the item that you want to move and then click the slot you want to move it to."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To discard an item hold the control key and then press the key that corresponds with the item you want to discard, this will only work if you're standing on a grass block."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To consult the Survival Guide, click the book icon in the top right corner of the screen."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        setSpacing(10);
                }});
                
                instructions.put("Finding Bugs", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Text("Looking for Insects"){{
                                setWrappingWidth(270);
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("To find bugs right click the ground until a bug appears in your inventory."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To eat an item press the key that corresponds with the item that you want to eat."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        setSpacing(10);
                }});
                
                instructions.put("Make FlintSteel", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Text("Making Flint and Steel"){{
                                setWrappingWidth(270);
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("To make flint and steel first place your knife on the ground."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("Then put your mouse pointer over the knife and press the key that corresponds with flint in your toolbar."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        setSpacing(10);
                }});
                
                instructions.put("Make a Fire", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Text("Making a Fire"){{
                                setWrappingWidth(270);
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("To stay warm at night you need to start a fire."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To start a fire hover your mouse pointer over a wood block and press the key that corresponds with the flint and steel item in your toolbar"){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        setSpacing(10);
                }});
                
                instructions.put("Fetch Water", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Text("Fetching and Purifying Water"){{
                                setWrappingWidth(270);
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("To get water hover your mouse pointer over a water block and press the key that corresponds with the water bottle in your toolbar."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To purify water place your water bottle on the ground, hover over it with your mouse pointer, and press the key that corresponds with the water purification tablets in your toolbar."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("You only have 15 water purification tablets, use them wisely."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To drink water press the key that corresponds with the water bottle in your toolbar."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        setSpacing(10);
                }});
                
                instructions.put("Make a Shelter", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Text("Making a Shelter/Bed"){{
                                setWrappingWidth(270);
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("To make a shelter you need to place 7 palisades in an open ring."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To place a palisade hover your mouse pointer over where you want to place it and press the key that corresponds with a wood item in your toolbar."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To make a bed pick a Cattail and then hover your mouse over the empty space in the middle of your shelter and press the key that corresponds with the Cattail in your toolbar."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("To sleep, right click the bed while standing in your shelter."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        setSpacing(10);
                }});
                
                instructions.put("Survive", new VBox(){{
                        setAlignment(Pos.TOP_CENTER);
                        getChildren().add(new Text("Survive"){{
                                setWrappingWidth(270);
                                setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
                        }});
                        
                        getChildren().add(new Text("To surive you must last 36 days."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("If you are struggling consult the Survival Guide or the Instruction Manual"){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        
                        getChildren().add(new Text("You can sleep in your shelter to skip nights and use the fast forward button in the top right corner of the screen to speed up the clock."){{
                                setWrappingWidth(300);
                                setFill(instructionsColor);
                                setId("instruction");
                        }});
                        setSpacing(10);
                }});
        }
}
