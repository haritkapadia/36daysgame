/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/21
 */

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

/**
 * This class sets up the Panic stage
 */
public class GamePanic extends Game {
        
        /**
         * Class constructor, calls the super class constructor
         * @param scene The scene on which the game is displayed
         * @param worldPath The file path to the world save files
         * @param onWin The code that will be executed once the stage has been completed
         */
        GamePanic(Scene scene, Path worldPath, Runnable onWin) {
                super(scene, worldPath, onWin);
        }
        
        /**
         * Initializes the quests for the Panic stage
         * 
         * Variables:
         * 
         * makeAFire      -Quest object, quest is completed once the player has made a fire
         * makeFlintSteel -Quest object, quest is completed once the player has made a flint and steel item
         * findTheFlint   -Quest object, quest is completed once the player has found a flint item
         */
        public void initialiseQuests() {                
                Quest makeBed = new Quest (questManager,
                                             "Make the Bed",
                                             "Make a bed out of cattails",
                                             1,
                                             ResourceManager.getItem(ItemKey.CATTAIL),
                                             null,
                                             "Make s Shelter",
                                             helpMenu);
                
                Quest makeShelter = new Quest (questManager,
                                             "Make a Shelter",
                                             "Make a shelter out of palisades.",
                                             1,
                                             ResourceManager.getItem(ItemKey.CATTAIL),
                                              new Quest[] {makeBed},
                                             "Make a Shelter",
                                             helpMenu);
                
                Quest purifyWater = new Quest (questManager,
                                               "Purify Water",
                                               "Use your water purification tablets to clear your water.",
                                               1,
                                               ResourceManager.getItem(ItemKey.WATERPURIFICATIONTABLETS),
                                               new Quest[] {makeShelter},
                                               "Fetch Water",
                                               helpMenu);
                
                Quest fetchWater = new Quest (questManager,
                                             "Fetch Water",
                                             "Fill up your water bottle",
                                             1,
                                             ResourceManager.getItem(ItemKey.WATERBOTTLE),
                                             new Quest[] {purifyWater},
                                             "Fetch Water",
                                             helpMenu);
                
                Quest makeAFire = new Quest (questManager,
                                             "Make a Fire",
                                             "Use the flint and steel to start a fire",
                                             1,
                                             ResourceManager.getItem(ItemKey.FLINTSTEEL),
                                             new Quest[]{fetchWater},
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
                                                "Make FlintSteel",
                                                helpMenu);
                
                questManager.addQuest(findTheFlint);
                questManager.startQuest(findTheFlint);
        }
}
