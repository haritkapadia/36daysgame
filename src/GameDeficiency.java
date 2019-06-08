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
 * This class is used to set up the deficiency stage of the game, it initializes all the deficiency quests and overrides
 * the handle method
 */
public class GameDeficiency extends Game {
                
                /**
                 * Class constructor, calls the super class constructor
                 * @param scene The scene on which the game is displayed
                 * @param worldPath The file path to the world save files
                 * @param onWin The code that will be executed once the stage has been completed
                 */
                GameDeficiency(Scene scene, Path worldPath, Runnable onWin) {
                super(scene, worldPath, onWin);
        } 
        
        /**
         * Overrides the super class handle method so that the player does not die from exposure in the deficiency stage
         */
        @Override
        public void handle(long time) {
                super.handle(time);
                player.setExposure(player.getMaxExposure());
        }
        
        /**
         * Initializes the quests for the deficiency stage
         * 
         * Variables:
         * 
         * findingBugs       -Quest object, quest is completed once the player has picked up 3 ants
         * pickABouquet3     -Quest object, quest is completed once the player has picked up 2 Indian Pipe items
         * pickABouquet2     -Quest object, quest is completed once the player has picked up an Elderberry item
         * pickABouquet1     -Quest object, quest is completed once the player has picked up 3 Northern Blue Flag items
         * pickUpSticks      -Quest object, quest is completed once the player has picked up 10 wood items
         * breakingTree      -Quest object, quest is completed once the player has broke a tree
         */
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
                                                 "Find and pick up two Indian Pipe items.",
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

