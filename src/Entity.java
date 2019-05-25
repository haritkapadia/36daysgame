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

public abstract class Entity {
	Point2D position;
	int health;
	int stomachFullness;
	int maxStomach;
	public static final int STOMACH_REDUCTION_TIME = 1000;
	World world;
	Item hand;
	public abstract void move();
	public abstract void useHand();
	public abstract void interact(Block block);

	// Entity(World world) {
	//	this.world = world;
	// }
}
