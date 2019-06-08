import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.nio.file.attribute.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.animation.*;
import java.awt.Point;

public class GamePanic extends Game {
	GamePanic(Scene scene, Path worldPath, Runnable onWin) {
		super(scene, worldPath, onWin);
	}

	public void initialiseQuests() {
		Quest makeAFire = new Quest (questManager,
					     "Make a Fire",
					     "Use the flint and steel to start a fire",
					     1,
					     ResourceManager.getItem(ItemKey.FLINTSTEEL),
					     null,
					     "Make a Fire",
					     helpMenu);

		Quest makeFlintSteel = new Quest(questManager,
						 "Make Flint and Steel",
						 "Follow the instructions to make a flint and steel item",
						 1,
						 ResourceManager.getItem(ItemKey.FLINT),
						 new Quest[]{makeAFire},
						 "Make FlintSteel",
						 helpMenu);

		Quest findTheFlint = new Quest (questManager,
						"Find the Flint",
						"Locate and pick up a flint item",
						1,
						ResourceManager.getBlock(BlockKey.FLINT),
						new Quest[]{makeFlintSteel},
						null,
						null);
		questManager.addQuest(findTheFlint);
		questManager.startQuest(findTheFlint);
	}
}
