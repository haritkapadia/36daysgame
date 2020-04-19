/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/26
 */
import javafx.scene.image.Image;
import javafx.geometry.Point2D;
import java.awt.Point;

/**
 * This class creates a fire entity
 * 
 * Variables:
 * 
 * burnWait      -Stores how long the fire has been burning for
 * prevElapsed   -Stores the world time when the fire was lit
 */
public class EntityFire extends Entity implements java.io.Serializable {
        private long burnWait;
        private long prevElapsed;
        
        /**
         * Class constructor, calls the super class constructor and initializes the variables
         * 
         * @param world The world that the fire is part of
         * @param x The x coordinate of the fire
         * @param y The y coordinate of the fire
         */
        EntityFire(World world, int x, int y) {
                super("fire", // id
                      new ItemKey[0], // inventory
                      x + 0.5,       // x
                      y + 0.5,       // y
                      0.5,       // radius
                      Direction.DOWN, // facing
                      10,       // health
                      10,       // max_health
                      0,       // hunger
                      0,       // max_hunger
                      0,       // exposure
                      0,       // max_exposure
                      0,       // thirst
                      0,       // max_thirst
                      world,       // world
                      0);       // speed
                prevElapsed = world.getStopwatch().getElapsed();
        }
        
        /**
         * Overrides the abstract interpolate method in the Entity class
         * @param d Required due to the abstract method in the super class
         */
        public void interpolate(double d) {}
        
        /**
         * Updates the fire, this method is called every cycle from the game method
         * 
         * Variables:
         * 
         * now     -Stores the current game time
         */
        public void exist() {
                long now;
                
                for(double i = x - 1; i <= x + 1; i++) {
                        for(double j = y - 1; j <= y + 1; j++) {
                                if(i != x || j != y) {
                                        Point p = World.blockCoordinate(new Point2D(i, j));
                                        BlockKey b = world.getBlock((int)p.getX(), (int)p.getY(), 1);
                                        if(b != null && (b == BlockKey.WOOD || b == BlockKey.TREE)) {
                                                world.setBlockUnsafe((int)p.getX(), (int)p.getY(), 1, null);
                                                world.startFire((int)p.getX(), (int)p.getY());
                                        }
                                }
                        }
                }
                now = world.getStopwatch().getElapsed();
                burnWait += now - prevElapsed;
                prevElapsed = now;
                if(burnWait >= 1e9) {
                        takeDamage(1);
                        burnWait -= (long)1e9;
                }
                for(Entity e : world.getEntities()) {
                        if(!(e instanceof EntityFire)) {
                                if(e.getPosition().distance(getPosition()) <= 3)
                                        e.setExposure(e.getMaxExposure());
                                if(e.getPosition().distance(getPosition()) <= 0.5)
                                        e.takeDamage(1);
                        }
                }
        }
        
        /**
         * @returns True since the fire has a transparent background
         */
        public boolean isTransparent() {
                return true;
        }
        
        /**
         * @returns the image of the fire
         */
        public Image getImage() {
                return ResourceManager.getFireSprite();
        }
}
