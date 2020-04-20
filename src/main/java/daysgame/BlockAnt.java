package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates an invisible block that when clicked turns into an ant item
 */
public class BlockAnt extends InsectBlock {
        
        /**
         * Constructor, calls the super class constructor to create an ant object
         */
        BlockAnt() { 
                super("img/insect.png", ItemKey.ANT);
        }      
}
