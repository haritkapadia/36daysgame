public class PortableBlock extends Block implements Interactable {
        private ItemKey equivalentItem;
        
        public PortableBlock(String file, ItemKey equivalentItem){
                super (file);
                this.equivalentItem = equivalentItem;
        }        
        
        public boolean isTransparent() {
                return true;
        }
        
        public boolean isSolid() {
                return false;
        }
                
        
        public void onInteract(Entity e, World world, int x, int y, int z) {
                for (int i = 0; i< ((Player)e).getToolbar().length; i++){
                        if(((Player)e).getToolbar()[i] == null){
                                ((Player)e).setToolbar(i,equivalentItem);
                                world.setBlock(x, y, z, null);
                                break;
                        }
                }
                synchronized(this) {
                        notifyAll();
                }
        }
        
        public double getInteractRadius() {
                return 2;
        }  
}
