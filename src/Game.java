import java.util.*;
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
import javafx.embed.swing.SwingFXUtils;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Game extends AnimationTimer {
        private Scene scene;
        World world;
        Camera camera;
        StackPane gamePane;
        Canvas canvas;
        Player player;
        ProgressIndicator health;
        InputManager i;
        Point2D prevPosition;
        
        long prevTime = -1;
        
        Game(Scene scene, Stage stage) {
                this.scene = scene;
                world = new World();
                player = new Player(world);
                world.addEntity(player);
                camera = new Camera(scene, world, player.getPosition());
                
                gamePane = new StackPane();
                canvas = new Canvas((int)scene.getWidth(), (int)scene.getHeight());
                health = new ProgressIndicator((double)player.getHealth() / player.getMaximumHealth());
                gamePane.getChildren().add(canvas);
                gamePane.getChildren().add(new VBox(){{
                        getChildren().add(health);
                }});
                
                i = new InputManager(this, world, player);
                scene.addEventHandler(KeyEvent.KEY_PRESSED, e -> i.keyPressed(e));
                scene.addEventHandler(KeyEvent.KEY_RELEASED, e -> i.keyReleased(e));
                // scene.addEventHandler(QuestEvent.QUEST_COMPLETED, e -> );
                gamePane.getChildren().add(new SettingsMenu(scene, stage));
        }
        
        public StackPane getPane() {
                return gamePane;
        }
        
        private void processInput(long time) {
                if(prevTime == -1) {
                        prevTime = time;
                        prevPosition = player.getPosition();
                        return;
                }
                
                long dt = time - prevTime;
                player.move(i.getDisplacement().multiply(dt / 1e9));
                Point2D p = player.getPosition();
                Point blockPos = World.blockCoordinate(p);
                int b = world.getBlock((int)blockPos.getX(), (int)blockPos.getY(), 1);
                if(b != 0 && ResourceManager.getBlock(b).isSolid()) {
                        player.setPosition(prevPosition);
                }
                camera.setPosition(player.getPosition());
                
                if(player.getPosition().equals(prevPosition))
                        player.stop();
                else
                        player.play();
                
                
                prevTime = time;
                prevPosition = player.getPosition();
        }
        
        public void handle(long time) {
                processInput(time);
                drawScreen();
        }
        
        public void drawScreen() {
                GraphicsContext g = canvas.getGraphicsContext2D();
                // g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                Rectangle2D r = camera.getViewBounds(); // works as intended
                Point sw = Chunk.globalToChunkPoint(new Point2D(r.getMinX(), r.getMinY())); // These work
                Point se = Chunk.globalToChunkPoint(new Point2D(r.getMaxX(), r.getMinY())); // These work
                Point nw = Chunk.globalToChunkPoint(new Point2D(r.getMinX(), r.getMaxY())); // These work
                
                double maxRatio = Math.max(scene.getWidth(), scene.getHeight()) / Math.max(r.getWidth(), r.getHeight());
                double maxS = Math.max(scene.getWidth(), scene.getHeight());
                double minS = Math.min(scene.getWidth(), scene.getHeight());
                
                for(int i = (int)sw.getX(); i <= (int)se.getX(); i++) {
                        for(int j = (int)sw.getY(); j <= (int)nw.getY(); j++) {
                                Point chunkPoint = new Point(i, j);
                                Point2D chunkPos = Chunk.chunkToGlobalPoint(chunkPoint);
                                Chunk c = world.getChunk(chunkPoint);
                                int screenX = (int)((chunkPos.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
                                int screenY = (int)(scene.getHeight() - ((chunkPos.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
                                int screenW = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * maxS);
                                int screenH = (int)(Chunk.CHUNK_SIDE_LENGTH / camera.getBlockFactor() * maxS);
                                if(c == null) {
                                        world.generateChunk(chunkPoint);
                                        c = world.getChunk(chunkPoint);
                                }
                                g.drawImage(SwingFXUtils.toFXImage(c.getChunkImage(), null), screenX, screenY - screenH, screenW, screenH);
                        }
                }
                
                for(Entity e : world.getEntities()) {
                        Point2D p = e.getPosition();
                        Dimension2D d = e.getDimension();
                        int screenX = (int)((p.getX() - camera.getX()) * maxRatio + scene.getWidth()/2);
                        int screenY = (int)(scene.getHeight() - ((p.getY() - camera.getY()) * maxRatio + scene.getHeight() / 2));
                        int screenW = (int)(1 / camera.getBlockFactor() * maxS);
                        int screenH = (int)(1 / camera.getBlockFactor() * maxS);
                        g.drawImage(SwingFXUtils.toFXImage((BufferedImage)e.getImage(), null), screenX, screenY - screenH, screenW, screenH);
                }
        }
        
        public Scene getScene() {
                return scene;
        }
}
