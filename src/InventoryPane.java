/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/03
 */

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.geometry.*;
import java.util.*;
import javafx.animation.*;
import java.util.concurrent.atomic.*;
import javafx.util.Duration;
/**
 * A pane displaying the main menu.
 *
 * @author Harit Kapadia, Jack Farley
 */
public class InventoryPane extends VBox {
        private Scene scene;
        private Game game;
        private Player player;
        private Button[] slot;
        private GridPane toolbar;
        private ToggleButton moreInventoryButton;
        private GridPane hiddenInventory;
        private boolean inventoryHidden;
        private ItemKey toMove;
        private int moveSource;
        
        InventoryPane(Scene scene, Game game, Player player) {
                StackPane[] slotPane = new StackPane[player.getInventory().length];
                final String[] KEYS = {"Q", "W", "E", "R", "T"};
                this.scene = scene;
                this.game = game;
                this.player = player;
                inventoryHidden = true;
                slot = new Button[player.getInventory().length];
                for(int j = 0; j < slot.length; j++) {
                        final int i = j;
                        slot[i] = new Button("");
                        slot[i].setPadding(Insets.EMPTY);
                        // HBox.setHgrow(slot[i], Priority.ALWAYS);
                        slot[i].setMaxWidth(100000000);
                        slot[i].prefHeightProperty().bind(slot[i].widthProperty());
                        slot[i].setStyle("-fx-background-color: rgba(128, 128, 128, 0.5); -fx-border-color: black;");
                        slot[i].setGraphic(new ImageView(){{
                                if(player.getInventory(i) != null) {
                                        setImage(ResourceManager.getItem(player.getInventory(i)).getImage());
                                        // fitWidthProperty().bind(slot[i].widthProperty());
                                        // fitHeightProperty().bind(slot[i].heightProperty());
                                }
                                setFitWidth(64);
                                setFitHeight(64);
                        }});
                        
                        
                        
                        slot[i].setOnAction(e-> {
                                if(toMove == null) {
                                        toMove = player.getInventory(i);
                                        if(toMove != null) {
                                                moveSource = i;
                                                ((ImageView)slot[i].getGraphic()).setImage(null);
                                                scene.setCursor(new ImageCursor(ResourceManager.getItem(toMove).getImage(), 32, 32));
                                        }
                                } else {
                                        ItemKey temp = player.getInventory(i);
                                        player.setInventory(i, player.getInventory(moveSource));
                                        player.setInventory(moveSource, temp);
                                        updateSlot(i);
                                        updateSlot(moveSource);
                                        toMove = null;
                                        scene.setCursor(null);
                                }
                        });
                }
                
                for (int i = 0; i < 5; i++){
                        final int J = i;
                        slotPane[i] = new StackPane(){{
                                getChildren().add(slot[J]);
                                if(J < 5){
                                        Label l = new Label(KEYS[J]);
                                        getChildren().add(l);
                                        setAlignment(l, Pos.BOTTOM_RIGHT);
                                        setMargin(l, new Insets(5,5,5,5));
                                }
                        }};
                }
                
                toolbar = new GridPane(){{
                        for(int i = 0; i < 5; i++)
                        getColumnConstraints().add(new ColumnConstraints(){{
                                setPercentWidth(100/5d);
                        }});
                        for(int i = 0; i < 5; i++)
                        add(slotPane[i], i, 0);
                }};
                
                hiddenInventory = new GridPane(){{
                        for(int i = 0; i < 5; i++)
                        getColumnConstraints().add(new ColumnConstraints(){{
                                setPercentWidth(100/5d);
                        }});
                        for(int i = 5; i < slot.length; i++) {
                                add(slot[i], i % 5, i / 5 - 1);
                        }
                        setVisible(false);
                }};
                
                moreInventoryButton = new ToggleButton("") {{
                        setId("upbutton");
                        setMaxWidth(1000000000);
                        setOnAction(e -> {
                                if(inventoryHidden) {
                                        hiddenInventory.setVisible(true);
                                        inventoryHidden = false;
                                } else {
                                        hiddenInventory.setVisible(false);
                                        inventoryHidden = true;
                                }
                        });
                }};
                
                getStylesheets().add("gamestylesheet.css");
                getChildren().addAll(hiddenInventory, moreInventoryButton, toolbar);
        }
        
        public void updateSlot(int i) {
                if(player.getInventory(i) == null)
                        ((ImageView)slot[i].getGraphic()).setImage(null);
                else
                        ((ImageView)slot[i].getGraphic()).setImage(ResourceManager.getItem(player.getInventory(i)).getImage());
        }
}
