/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

import javafx.scene.image.Image;

/**
 * The super class for all the blocks that can be picked up by the player
 * 
 * Variables:
 * 
 * equivalentItem stores the ItemKey value of the equivalent item to the object so that it can be converted to be added to the inventory
 * 
 */
public class PortableBlock extends Block implements Interactable {
        private ItemKey equivalentItem;
        
        /**
         * The class constructor, calls the super class and initializes the equivalentItem variable
         */
        public PortableBlock(String file, ItemKey equivalentItem){
                super (file);
                this.equivalentItem = equivalentItem;
        }
        
        /**
         * @returns the Image of the block
         */
        public Image getImage() {
                return ResourceManager.getItem(equivalentItem).getImage();
        }
        
        /**
         * @returns true if the block has a transparent background
         */
        public boolean isTransparent() {
                return true;
        }
        
        /**
         * @returns false if the block can be walked through
         */
        public boolean isSolid() {
                return false;
        }
        
        /**
         * Adds the equivalent item to the player's inventory
         * 
         * @param e is the entity that uses the item
         * @param w is the World in which the entity is located
         * @param x is the x coordinate of the mouse
         * @param y is the y coordinate of the mouse
         * @param z is the z coordinate of the entity
         */
        public void onInteract(Entity e, World world, int x, int y, int z) {
                for (int i = 0; i < e.getInventory().length; i++){
                        if(e.getInventory(i) == null) {
                                e.setInventory(i, equivalentItem);
                                if(e instanceof Player)
                                        ((Player)e).updateInventoryPaneSlot(i);
                                world.setBlockUnsafe(x, y, z, null);
                                synchronized(this) {
                                        notifyAll();
                                }
                                return;
                        }
                }
        }
        
        /**
         * @returns how close the player must be to interact with the block
         */
        public double getInteractRadius() {
                return 2;
        }
}
