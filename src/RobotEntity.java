import java.util.*;

public abstract class RobotEntity extends Entity {
        public abstract boolean isBlockImportant(Block block);
        public abstract List<Entity> getEntitiesInView();
        
        RobotEntity(World world) {
                super(world);
        }
}
