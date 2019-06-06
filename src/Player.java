/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import java.util.*;
import javafx.util.*;
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
import javafx.animation.*;

public class Player extends Entity {
	transient Image image;
	transient InventoryPane inventoryPane;

	Player(World world, String s) {
		super(world);
		id = s;

		// try {
		//         sprites = new EnumMap<Direction, Image[]>(Direction.class){{
		//                 for(Direction d : Direction.values()) {
		//                         Image[] images = new Image[3];
		//                         System.out.print(d + "\t");
		//                         for(int i = 0; i < images.length; i++) {
		//                                 images[i] = new Image("Characters/sprite" + (i + 1 + d.ordinal() * 3) + ".png");
		//                                 System.out.print((i + 1 + d.ordinal() * 3) + " ");

		//                         }
		//                         System.out.println();

		//                         put(d, images);
		//                 }
		//         }};
		// } catch(Exception e) {
		//         e.printStackTrace();
		// }

		image = ResourceManager.getPlayerSprite(Direction.DOWN, 2);
		setCycleCount(Animation.INDEFINITE);
		setCycleDuration(new Duration(300));
	}



	public void interpolate(double frac) {
		int frame = ((int)(frac * 3) + 1) % 3;
		image = ResourceManager.getPlayerSprite(getFacing(), frame);
	}


	public void stop() {
		image = ResourceManager.getPlayerSprite(getFacing(), 1);
		super.stop();
	}

	public void destroy(int x, int y, int z) {
		world.destroyBlock(x, y, z);
	}

	public String getID() {
		return "player.save";
	}

	public void move(Point2D displacement) {
		setPosition(getPosition().add(displacement));
	}

	public Image getImage() {
		return image;
	}

	public boolean isTransparent() {
		return true;
	}

	public void useTool(int i, Point2D target) {
		super.useTool(i, target);
		updateInventoryPaneSlot(i);
	}

	public void dropItem(int i) {
		super.dropItem(i);
		updateInventoryPaneSlot(i);
	}

	public void setInventoryPane(InventoryPane p) {
		inventoryPane = p;
	}

	public void updateInventoryPaneSlot(int i) {
		inventoryPane.updateSlot(i);
	}
}
