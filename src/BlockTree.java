/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class BlockTree extends Block implements Destroyable {
        BlockTree() {
                super("Artwork/tree_small.png");
        }
        
        public boolean isTransparent() {
                return true;
        }
        
        public void onDestroy(World world, int x, int y, int z) {
                world.setBlock(x - 1, y, z, BlockKey.WOOD);
                world.setBlock(x + 1, y, z, BlockKey.WOOD);
                world.setBlock(x, y - 1, z, BlockKey.WOOD);
                world.setBlock(x, y + 1, z, BlockKey.WOOD);
                synchronized(this) {
                        notifyAll();
                }
        }
        
        public boolean isSolid() {
                return true;
        }
}
