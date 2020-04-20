package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates an invisible grasshopper block that when clicked will turn into a grasshopper item
 */
public class BlockGrasshopper extends InsectBlock {
        
        /**Constructor. calls the superclass constructor*/
        BlockGrasshopper() {
                super("img/insect.png", ItemKey.GRASSHOPPER);
        }      
}
