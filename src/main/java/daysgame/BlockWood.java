package daysgame;

/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * Creates a wood block which can be picked up and used to make a shelter
 */
public class BlockWood extends PortableBlock {
        
        /**Constructor. calls the superclass constructor*/
        BlockWood() {
                super("img/wood_small.png", ItemKey.WOOD);
        }      
}
