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

public class Player extends Entity implements java.io.Serializable {
	transient Image image;
	transient InventoryPane inventoryPane;
	long healthWait;
	long hungerWait;
	long exposureWait;
	long thirstWait;
	long prevElapsed;
	final long CYCLE_DURATION = 300;

	Player(World world, String s) {
		super(world);
		id = s;
		inventory[0] = ItemKey.KNIFE;
		inventory[1] = ItemKey.WATERBOTTLE;
		image = ResourceManager.getPlayerSprite(Direction.DOWN, 2);
		setCycleCount(Animation.INDEFINITE);
		setCycleDuration(new Duration(CYCLE_DURATION));
		prevElapsed = world.getStopwatch().getElapsed();
	}

	public void interpolate(double frac) {
		int frame = ((int)(frac * 3) + 1) % 3;
		image = ResourceManager.getPlayerSprite(getFacing(), frame);
	}

	public void stop() {
		image = ResourceManager.getPlayerSprite(getFacing(), 1);
		super.stop();
	}

	public void exist() {
		long now = world.getStopwatch().getElapsed();
		healthWait += now - prevElapsed;
		exposureWait += now - prevElapsed;
		hungerWait += now - prevElapsed;
		thirstWait += now - prevElapsed;
		prevElapsed = now;
		if(healthWait >= 10e9) {
			if(getHunger() < getMaxHunger()/2)
				takeDamage(1);
			healthWait -= (long)10e9;
		}
		if(exposureWait >= 1e9) {
			if (world.getLightLevel() <= 0.4)
				lowerExposure(1);
			if(getExposure() < getMaxExposure() * 0.4)
				takeDamage(1);
			exposureWait -= (long)1e9;
		}
		if(hungerWait >= 20e9) {
			eatFood(-1);
			if(getHunger() > getMaxHunger() * 0.8)
				takeDamage(-1);
			hungerWait -= (long)20e9;
		}
		if(thirstWait >= 10e9) {
			drink(-1);
			if(getThirst() > getMaxThirst() * 0.8)
				takeDamage(-1);
			thirstWait -= (long)10e9;
		}
	}

	public void destroy(int x, int y, int z) {
		world.destroyBlock(x, y, z);
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
