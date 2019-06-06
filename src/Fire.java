/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/26
 */

import javafx.scene.image.Image;

/**
 * Runs the fire thread
 */
public class Fire{
        int x,y,z;
        World w;
        private int fuel;
        private int fireID;
        private Entity e;
        
        /**
         * Class constructor, calls the Block constructor using the fire image
         */
        Fire(Entity e, World w, int x, int y, int z, int n){
                fireID = n;
                fuel = 100;
                this.w = w;
                this.x = x;
                this.y = y;
                this.z = z;
                this.e = e;
        }
        
        /**
         * Makes the fire burn
         */
        public void updateFire(){
                fuel--;
                for (int wx = x-1; wx<= x+1; wx++){
                        for (int wy = y-1; wy <= y+1; wy++){
                                if (w.getBlock(wx,wy,z)!=null && (w.getBlock(wx,wy,z).equals(BlockKey.WOOD)||w.getBlock(wx,wy,z).equals(BlockKey.TREE))&& !((int)e.getX() == wx && (int)e.getY() == wy)){
                                        w.setBlockUnsafe(wx, wy, z, null);
                                        w.setBlock(wx, wy, z, BlockKey.FIRE);
                                        w.fires.add(new Fire(e, w, wx, wy, z, w.fires.size()));
                                        synchronized(this) {
                                                notifyAll();
                                        }
                                }
                        }
                }
                if(fuel <= 0)
                        endFire();
                if (playerNearby()&& e.getExposure()<e.getMaxExposure())
                        e.lowerExposure(-1);
        }
        
        private boolean playerNearby(){
                return (Math.abs(e.getX()-x) < 2 && Math.abs(e.getY()-y) < 2);
        }
        
        private void lowerID(){
                fireID--;
        }
        
        private void endFire(){
                w.setBlockUnsafe(x,y,z, null);
                w.fires.remove(fireID);
                for (Fire fire : w.fires)
                        fire.lowerID();
        }
}
