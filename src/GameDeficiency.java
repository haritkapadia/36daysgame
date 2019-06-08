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

public class GameDeficiency extends Game {
	GameDeficiency(Scene scene, Path worldPath, Runnable onWin) {
		super(scene, worldPath, onWin);
	}

	public void initialiseQuests() {
		Quest findingBugs = new Quest (questManager,
					       "Finding Bugs",
					       "Find 3 ants",
					       3,
					       ResourceManager.getBlock(BlockKey.ANT),
					       null,
					       "Finding Bugs",
					       helpMenu);

		Quest pickABouquet3 = new Quest (questManager,
						 "Pick a Bouquet Part 3",
						 "Find and pick up two Indian Pipes.",
						 2,
						 ResourceManager.getBlock(BlockKey.INDIANPIPE),
						 new Quest[]{findingBugs},
						 null,
						 null);

		Quest pickABouquet2 = new Quest (questManager,
						 "Pick a Bouquet Part 2",
						 "Find and pick up an Elderberry.",
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
					       "Pick up ten wood items",
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
					       "Welcome Message",
					       helpMenu);

		questManager.addQuest(breakingTree);
		questManager.startQuest(breakingTree);
	}
}
