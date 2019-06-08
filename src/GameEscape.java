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

public class GameEscape extends Game {
	GameEscape(Scene scene, Path worldPath, Runnable onWin) {
		super(scene, worldPath, onWin);
	}

	public void initialiseQuests() {
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

		questManager.addQuest(breakingHogweed);
		questManager.startQuest(breakingHogweed);
	}
}
