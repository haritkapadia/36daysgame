/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * The interface for all things in the game that can be interacted with
 */
public interface Interactable {
        
        /**
         * @returns How far the player has to be to interact with it
         */
        public double getInteractRadius();
        
        /**
         * What happens when the player interacts with it
         * 
         * @param e The player
         * @param w The world that it is part of
         * @param x The x coordinate
         * @param y The y coordinate
         * @param z The z coordinate
         */
        public void onInteract(Entity e, World w, int x, int y, int z);
}
