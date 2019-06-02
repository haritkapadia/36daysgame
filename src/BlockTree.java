public class BlockTree extends Block implements Destroyable {
        BlockTree() {
                super("Artwork/tree_small.png");
        }
        
        public boolean isTransparent() {
                return true;
        }
        
        public void onDestroy(World world, int x, int y, int z) {
                if(world.getBlock(x - 1, y, z) == null)
                        world.setBlock(x - 1, y, z, BlockKey.WOOD);
                if(world.getBlock(x + 1, y, z) == null)
                        world.setBlock(x + 1, y, z, BlockKey.WOOD);
                if(world.getBlock(x, y - 1, z) == null)
                        world.setBlock(x, y - 1, z, BlockKey.WOOD);
                if(world.getBlock(x, y + 1, z) == null)
                        world.setBlock(x, y + 1, z, BlockKey.WOOD);
                synchronized(this) {
                        notifyAll();
                }
        }
        
        public boolean isSolid() {
                return true;
        }
}
