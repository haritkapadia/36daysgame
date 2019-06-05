/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

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
import javafx.concurrent.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;

public class QuestManager {
	private List<Quest> quests = new LinkedList<Quest>();
	private World world;
	private VBox ui;

	public QuestManager(World world, VBox ui) {
		this.ui = ui;
		this.world = world;
		try {
			new File("world" + world.getSeed() + "/questlog").createNewFile();
			new File("world" + world.getSeed() + "/currentquest").createNewFile();
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}

	}

	public void addQuest(Quest q) {
		quests.add(q);
	}

	public void removeQuest(Quest q) {
		ui.getChildren().remove(q.getQuestPane());
		quests.remove(q);
		try {
			Files.write(Paths.get("world" + world.getSeed() + "/questlog"), (q.getQuestName() + "\n").getBytes("UTF-8"), StandardOpenOption.APPEND);
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}

	}

	public void updateCurrentQuest(Quest q) {
		try {
			Files.write(Paths.get("world" + world.getSeed() + "/currentquest"), Arrays.asList(new String[]{q.getQuestName(), q.getStepsTaken() + ""}), Charset.forName("UTF-8"));
			System.out.println("Current Quest written: " + q.getQuestName());

		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void resumeQuests() {
		try {
			List<String> currQuest = Files.readAllLines(Paths.get("world" + world.getSeed() + "/currentquest"), Charset.forName("UTF-8"));
			BufferedReader r = Files.newBufferedReader(Paths.get("world" + world.getSeed() + "/questlog"), Charset.forName("UTF-8"));
			String line = null;
			while((line = r.readLine()) != null) {
				List<Quest> _quests = new ArrayList<Quest>(quests);
				for(Quest v : _quests) {
					if(v.getQuestName().equals(line)) {
						System.out.println("Completed " + line);
						v.completeQuest();
						v.join();
						System.out.println("joined");

					}
				}
			}
			r.close();
			System.out.println("lines: " + currQuest.size());
			if(currQuest.size() == 2) {
				System.out.println("currQuest found");

				for(Quest v : quests) {
					if(v.getQuestName().equals(currQuest.get(0))) {
						System.out.println("Set steps: " + v.getQuestName());

						v.setStepsTaken(Integer.parseInt(currQuest.get(1)));
						break;
					}
				}
			}
		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void killQuests() {
		for(Quest q : quests)
			q.stop();
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public void startQuest(Quest q) {
		Platform.runLater(() -> ui.getChildren().add(q.getQuestPane()));
		q.start();
	}
}
