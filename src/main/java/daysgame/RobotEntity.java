package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.util.*;

public abstract class RobotEntity extends Entity {
        public abstract boolean isBlockImportant(Block block);
        public abstract List<Entity> getEntitiesInView();
        
        RobotEntity(World world, int n) {
                super(world);
        }
}
