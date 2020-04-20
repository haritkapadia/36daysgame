package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 * Driver class
 */

import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.nio.file.attribute.*;
import java.util.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.*;
import javax.sound.sampled.*;
import java.awt.Point;

/**
 * The main class, creating the window, scenes, and starting the program.
 *
 * Variables:
 * panes       -A HashMap used to store all of the panes
 * mediaPlayer -A MediaPlayer used to play the background music
 * muteControl -A BooleanControl variable that is used to mute the background music
 *
 * @author Harit Kapadia, Jack Farley
 */
public class Main extends Application {
	public static HashMap<String, Parent> panes;
	public static MediaPlayer mediaPlayer;
	public static BooleanControl muteControl;
	public static HashMap<String, Parent> stuff;
	public static SettingsMenu settingsMenu;
	public static final Path rootPath = Paths.get(System.getProperty("user.home"), "Desktop", "36daysfiles");

	/**
	 * Changes the pane being displayed on the scene
	 */
	public static void setPane(Scene scene, String name) {
		scene.setRoot(panes.get(name));

		if (!name.equals("Splash Screen")&&!name.equals("Survival Guide")){
			try{
				((Pane)panes.get(name)).getChildren().add(Main.settingsMenu);
				Main.settingsMenu.relocate(scene.getWidth()-SettingsMenu.getMenuWidth(2),50);
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}

	/**
	 * @param key The name of the pane
	 * @return The associated pane of the key
	public static Parent getPane(String key) {
		return panes.get(key);
	}

	/**
	 * Changes the pane being displayed on the scene
	 */
	public static void setPane(Scene scene, Parent node) {
		scene.setRoot(node);
		System.out.println(node);
	}

	/**
	 * Copies a Point2D to another Point2D
	 * @param p The Point2D that is to be copied
	 * @return A copied Point2D object
	 */
	public static Point2D point2d(Point2D p) {
		return new Point2D(p.getX(), p.getY());
	}

	/**
	 * Converts Point to Point2D
	 * @param p The Point that is to be converted
	 * @return A Point2D object equivalent to the Point
	 */
	public static Point2D toPoint2D(Point p) {
		return new Point2D(p.getX(), p.getY());
	}

	/**
	 * Initialises the scene map in preparation of the full program.
	 */
	@Override
	public void init() {
		panes = new HashMap<String, Parent>();
		Font.loadFont(getClass().getResourceAsStream("/ChicagoFLF.ttf"), 16);
		try {
			String out = "Tier 1\t1000\n" +
				"Tier 2\t900\n" +
				"Tier 3\t800\n" +
				"Tier 4\t700\n" +
				"Tier 5\t600\n" +
				"Tier 6\t500\n" +
				"Tier 7\t400\n" +
				"Tier 8\t300\n" +
				"Tier 9\t200\n" +
				"Tier 10\t100";
			if(!rootPath.resolve("worlds").toFile().exists())
				rootPath.resolve("worlds").toFile().mkdirs();
			if(!rootPath.resolve("scores").toFile().exists())
				rootPath.resolve("scores").toFile().mkdirs();
			if(!rootPath.resolve("scores").resolve("Deficiency").toFile().exists())
				Files.write(rootPath.resolve("scores").resolve("Deficiency"), out.getBytes("UTF-8"));
			if(!rootPath.resolve("scores").resolve("Panic").toFile().exists())
				Files.write(rootPath.resolve("scores").resolve("Panic"), out.getBytes("UTF-8"));
			if(!rootPath.resolve("scores").resolve("Escape").toFile().exists())
				Files.write(rootPath.resolve("scores").resolve("Escape"), out.getBytes("UTF-8"));

		}
		catch (Throwable e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Starts the program.
	 *
	 * Variables:
	 *
	 * MUSIC        -A File variable that stores the background music file
	 * clip         -A Clip variable that is used to play the audio
	 *
	 * @param primaryStage The window on which the program will run.
	 */
	@Override
	public void start(Stage primaryStage) {
        
        InputStream musicResource = Main.class.getResourceAsStream("/Walking-dreamy-bass-synth-loop.wav");
        InputStream bufferedMusic = new BufferedInputStream(musicResource);
        
		Clip clip;
		try{
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedMusic);

			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
			Main.muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}

		Scene main = new Scene(new Pane());// placeholder
		SettingsMenu.initButtons(main, primaryStage);


		primaryStage.setFullScreenExitHint("");
		primaryStage.setFullScreenExitKeyCombination(null);
		primaryStage.setTitle("36 Days - Wilderness Survival Game");
		primaryStage.setScene(main);
		primaryStage.setFullScreen(true);
		primaryStage.show();

		panes.put("Survival Guide", new SurvivalGuidePane(main, null));
		panes.put("Splash Screen", new SplashPane(main));
		panes.put("Main Menu", new MainMenuPane(main, primaryStage));
		panes.put("About", new AboutPane(main, primaryStage));
		panes.put("Instructions", new InstructionsPane(main, primaryStage));


		Main.settingsMenu = new SettingsMenu(main, primaryStage, true);

		Main.setPane(main, "Splash Screen");
		((SplashPane)panes.get("Splash Screen")).getft().play();
	}

	// https://docs.oracle.com/javase/8/docs/api/java/nio/file/FileVisitor.html
	/**
	 * Copies the files to a new destination
	 * @param start The original file path
	 * @param dest The destination file path
	 */
	public static void copyPath(Path start, Path dest) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>(){
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					Path target = dest.resolve(start.relativize(dir));
					Files.copy(dir, target);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.copy(file, dest.resolve(start.relativize(file)));
					return FileVisitResult.CONTINUE;
				}
			});
	}

	/**
	 * Deletes the files at a given path
	 * @param start The path that is to be deleted
	 */
	public static void deletePath(Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>(){
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
					if(e == null)
						Files.delete(dir);
					else
						throw e;
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}
			});
	}

	/**
	 * The main method.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		launch();
	}
}
