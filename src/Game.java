/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Game extends AnimationTimer {
        private Scene scene;
        World world;
        Camera camera;
        StackPane gamePane;
        Canvas canvas;
        VBox questUI = null;
        HBox toolbar;
        InventoryPane inventoryPane = null;
        Player player;
        ProgressBar health;
        ProgressBar hunger;
        InputManager i;
        QuestManager questManager;
        Point2D prevPosition;
        HelpMenu helpMenu;
        public SurvivalGuidePane gameSurvivalGuide;
        long prevTime = -1;
        
        long prevTime = -1;
        
        Game(Scene scene) {
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                ToggleButton tb;
                int screenWidth = gd.getDisplayMode().getWidth();
                int screenHeight = gd.getDisplayMode().getHeight();
                gameSurvivalGuide = new SurvivalGuidePane(scene, this);
                helpMenu = new HelpMenu();
                this.scene = scene;
                world = new World();
                
                
                String s = null;
                try {
                        s = new String(Files.readAllBytes(Paths.get("player.save")));
                }
                catch (Throwable e) {
                        System.out.println("Error " + e.getMessage());
                        e.printStackTrace();
                }
                player = new Player(world, s);
                
                
                
                
                world.addEntity(player);
                camera = new Camera(scene, world, player.getPosition());
                
                gamePane = new StackPane();
                canvas = new Canvas((int)scene.getWidth(), (int)scene.getHeight());
                health = new ProgressIndicator((double)player.getHealth() / player.getMaximumHealth());
                questUI = new VBox(){{
                        getChildren().add(health);
                }};
                gamePane.getChildren().addAll(canvas, questUI);
                
                inventoryPane = new InventoryPane(scene, this, player){{
                        setMaxWidth(scene.getWidth() / 3);
                        setAlignment(Pos.BOTTOM_CENTER);
                }};
                gamePane.getChildren().add(inventoryPane);
                player.setInventoryPane(inventoryPane);
                
                gamePane.getChildren().add(helpMenu);
                helpMenu.relocate(screenWidth-650, screenHeight-430);
                
                tb = new ToggleButton("");
                tb.setId("helpbutton");
                tb.setOnAction(e -> helpMenu.setVisible(!helpMenu.isVisible()));
                gamePane.setMargin(tb, new Insets(0, 50, 20, 0));
                gamePane.getChildren().add(tb);
                gamePane.setAlignment(tb, Pos.BOTTOM_RIGHT);
                
                i = new InputManager(this, world, player);
                scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> i.keyPressed(e));
                scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> i.keyReleased(e));
                scene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> i.mouseClicked(e));
                scene.addEventHandler(MouseEvent.MOUSE_MOVED, e -> i.mouseMoved(e));
                
                gamePane.getChildren().add(Main.settingsMenu);
                
                Main.settingsMenu.addGameButtons(gamePane, this);
                Main.settingsMenu.relocate(scene.getWidth()-SettingsMenu.getMenuWidth(4),50);
                
                gamePane.getChildren().add(gameSurvivalGuide);
                gameSurvivalGuide.setVisible(false);
                
                
                gamePane.getStylesheets().add("gamestylesheet.css");
                
                
                
                questManager = new QuestManager(questUI);
                initialiseQuests();
        }
        
        public StackPane getPane() {
                return gamePane;
        }
        
        private void processInput(long time) {
                if(prevTime == -1) {
                        prevTime = time;
                        prevPosition = Main.point2d(player.getPosition());
                        return;
                }
                long dt = time - prevTime;
                Point2D displacement = i.getDesiredDisplacement();
                if(displacement.magnitude() > player.getSpeed() * dt / 1e9)
                        displacement = displacement.normalize().multiply(player.getSpeed() * dt / 1e9);
                player.move(displacement);
                
                Point p = World.blockCoordinate(i.getClickPosition());
                BlockKey c = world.getBlock((int)p.getX(), (int)p.getY(), 1);
                if(i.getClickButton() == MouseButton.PRIMARY &&
                   c != null &&
                   ResourceManager.getBlock(c) instanceof Destroyable &&
                   i.getClickPosition().subtract(player.getPosition()).magnitude() <= 2) {
                        player.destroy((int)p.getX(), (int)p.getY(), 1);
                        player.setPosition(prevPosition);
                        i.resetClick();
                } else if(i.getClickButton() == MouseButton.SECONDARY
                                  && c != null &&
                          ResourceManager.getBlock(c) instanceof Interactable &&
                          i.getClickPosition().subtract(player.getPosition()).magnitude() <= ((Interactable)ResourceManager.getBlock(c)).getInteractRadius()) {
                        player.interact((int)p.getX(), (int)p.getY(), 1);
                        player.setPosition(prevPosition);
                        i.resetClick();
                }
                
                
                p = World.blockCoordinate(player.getPosition());
                BlockKey b = world.getBlock((int)p.getX(), (int)p.getY(), 1);
                if(b != null && ResourceManager.getBlock(b).isSolid()) {
                        Point pp = World.blockCoordinate(prevPosition);
                        // player.setPosition(new Point2D(pp.getX() + 0.5, pp.getY() + 0.5));
                        player.setPosition(prevPosition);
                        i.resetClick();
                }
                
                if(displacement.getX() == 0 && displacement.getY() == 0)
                        player.stop();
                else
                        player.play();
                camera.setPosition(player.getPosition());
                prevTime = time;
                prevPosition = Main.point2d(player.getPosition());
        }
        
        public void start() {
                world.getStopwatch().start();
                super.start();
        }
        
        public void pause(){
                world.getStopwatch().pause();
                super.stop();
        }
        
        public void resume(PauseMenu pauseMenu){
                world.getStopwatch().unpause();
                super.start();
                gamePane.getChildren().remove (pauseMenu);
        }
        
        public void resume(){
                world.getStopwatch().unpause();
                super.start();
        }
        
        public void closeGuide(){
                gameSurvivalGuide.toBack();
                resume();
        }
        
        public void handle(long time) {
                processInput(time);
                health.setProgress((double)player.getHealth() / player.getMaximumHealth());
                hunger.setProgress((double)player.getHunger()/player.getMaxHunger());
                drawScreen();
                if (time % 10000 == 0){
                        if(player.getHunger()<player.getMaxHunger()/2)
                                player.takeDamage(1);
                }
                if (time % 100000 == 0){
                        player.eatFood(-1);
                        if(player.getHunger()>player.getMaxHunger()*0.8)
                                player.takeDamage(-1);
                }
        }
        
        public void drawScreen() {
                GraphicsContext g = canvas.getGraphicsContext2D();
                Rectangle2D r = camera.getViewBounds(); // works as intended
                double maxRatio = Math.max(scene.getWidth(), scene.getHeight()) / Math.max(r.getWidth(), r.getHeight());
                double maxS = Math.max(scene.getWidth(), scene.getHeight());
                double minS = Math.min(scene.getWidth(), scene.getHeight());
                Point sw = World.blockCoordinate(new Point2D(r.getMinX(), r.getMinY())); // These work
                Point se = World.blockCoordinate(new Point2D(r.getMaxX(), r.getMinY())); // These work
                Point nw = World.blockCoordinate(new Point2D(r.getMinX(), r.getMaxY())); // These work
                
                for(int z = 0; z < 2; z++) {
                        for(int i = (int)sw.getX(); i <= (int)se.getX(); i++) {
                                for(int j = (int)sw.getY(); j <= (int)nw.getY(); j++) {
                                        double screenX = (i - camera.getX()) * maxRatio + scene.getWidth()/2;
                                        double screenY = scene.getHeight() - ((j - camera.getY()) * maxRatio + scene.getHeight() / 2);
                                        double screenL = 1 / camera.getBlockFactor() * maxS;
                                        BlockKey b = world.getBlock(i, j, z);
                                        if(b != null)
                                                g.drawImage(ResourceManager.getBlock(b).getImage(),
                                                            screenX,
                                                            screenY - screenL,
                                                            screenL,
                                                            screenL);
                                }
                        }
                }
                
                for(Entity e : world.getEntities()) {
                        double screenX = (e.getX() - e.getRadius() - camera.getX()) * maxRatio + scene.getWidth()/2;
                        double screenY = scene.getHeight() - ((e.getY() - e.getRadius() - camera.getY()) * maxRatio + scene.getHeight() / 2);
                        double screenL = 2 * e.getRadius() / camera.getBlockFactor() * maxS;
                        g.drawImage(e.getImage(), screenX, screenY - screenL, screenL, screenL);
                }
                
                Point m = World.blockCoordinate(i.getWorldMouseCoordinates());
                double screenX = (m.getX() - camera.getX()) * maxRatio + scene.getWidth()/2;
                double screenY = scene.getHeight() - ((m.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2);
                double screenL = 1 / camera.getBlockFactor() * maxS;
                g.strokeRect(screenX, screenY - screenL, screenL, screenL);
                double playerX = (player.getX() - camera.getX()) * maxRatio + scene.getWidth()/2;
                double playerY = scene.getHeight() - ((player.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2);
                Point2D c = i.getClickPosition();
                double clickX = (c.getX() - camera.getX()) * maxRatio + scene.getWidth()/2;
                
                double clickY = scene.getHeight() - ((c.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2);
                g.strokeLine(playerX, playerY, clickX, clickY);
                
                
                
                g.setFill(new Color(0, 0, 0.2, 1 - world.getLightLevel()));
                g.fillRect(0, 0, scene.getWidth(), scene.getHeight());
        }
        
        public void killQuests() {
                questManager.killQuests();
        }
        private void initialiseQuests() {
                Quest touchingPoison = new Quest(questManager,
                                                 "Touching Poison",
                                                 "What happens when you touch poison? Nothing!",
                                                 1,
                                                 ResourceManager.getBlock(BlockKey.POISON),
                                                 null,
                                                 null,
                                                 null);
                
                Quest breakingHogweed = new Quest(questManager,
                                                  "Breaking Weeds",
                                                  "See the effects of breaking hogweed!",
                                                  5,
                                                  ResourceManager.getBlock(BlockKey.HOGWEED),
                                                  new Quest[]{touchingPoison},
                                                  null,
                                                  null);
                
                Quest pickABouquet3 = new Quest (questManager,
                                                 "Pick a Bouquet Part 3,
                                                         "Find and pick up two Indian Pipes",
                                                 2,
                                                 ResourceManager.getBlock(BlockKey.NORTHERNBLUEFLAG),
                                                 new Quest[]{breakingHogweed},
                                                 null,
                                                 null);
                
                Quest pickABouquet2 = new Quest (questManager,
                                                 "Pick a Bouquet Part 2",
                                                 "Find and pick up an Elderberry",
                                                 1,
                                                 ResourceManager.getBlock(BlockKey.ELDERBERRY),
                                                 new Quest[]{pickABouquet3},
                                                 null,
                                                 null);
                
                Quest pickABouquet1 = new Quest (questManager,
                                                 "Pick a Bouquet Part 1",
                                                 "Find and pick up 3 Northern Blue Flags. Consult the Survival Guide for identification information. You may need to clear some space in your inventory!",
                                                 3,
                                                 ResourceManager.getBlock(BlockKey.NORTHERNBLUEFLAG),
                                                 new Quest[]{pickABouquet2},
                                                 "Pick a Bouquet",
                                                 helpMenu);
                
                Quest pickUpSticks = new Quest(questManager,
                                               "Pick Up Sticks",
                                               "Break up ten wood items",
                                               10,
                                               ResourceManager.getBlock(BlockKey.WOOD),
                                               new Quest[]{pickABouquet1},
                                               null,
                                               null);
                
                Quest breakingTree = new Quest(questManager,
                                               "Break a Tree",
                                               "Break one tree",
                                               1,
                                               ResourceManager.getBlock(BlockKey.TREE),
                                               new Quest[]{pickUpSticks},
                                               null,
                                               null);
                
                questManager.addQuest(breakingTree);
                questManager.addQuest(breakingHogweed);
                questManager.addQuest(touchingPoison);
                questManager.addQuest(pickUpSticks);
                questManager.startQuest(breakingTree);
        }
        
        public Scene getScene() {
                return scene;
        }
        
        public Camera getCamera() {
                return camera;
        }
}
