/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/May/26
 */

import javafx.scene.image.Image;

/**
 * Runs the fire thread
 * 
 * Variables:
 * 
 * x,y,z     -The x,y, and z coordinates of the fire respectively
 * w         -The game world that the fire is part of
 * fuel      -The amount of fuel that the fire has left
 * fireID    -The fire's position in the fires ArrayList
 * e         -The entity that the fire effects
 */
public class Fire{
        int x,y,z;
        World w;
        private int fuel;
        private int fireID;
        private Entity e;
        
        /**
         * Class constructor, initializes all the variables
         * @param e is the Entity that is affected by this object
         * @param w is the game world that this object is part of
         * @param x is the x coordinate of the fire
         * @param y is the y coordinate of the fire
         * @param z is the z coordinate of the fire
         * @param n is the fire's number within the fires ArrayList
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
        
        /**
         * @returns true if the player is within two blocks of the fire
         */
        private boolean playerNearby(){
                return (Math.abs(e.getX()-x) < 2 && Math.abs(e.getY()-y) < 2);
        }
        
        /**
         * Changes the fires identification number to be one lower than it currently is
         * This method is used when a fire is removed from the fires ArrayList
         */
        private void lowerID(){
                fireID--;
        }
        
        /**
         * Causes the fire to burn out and removes the fire from the fires ArrayList
         */
        private void endFire(){
                w.setBlockUnsafe(x,y,z, null);
                w.fires.remove(fireID);
                for (Fire fire : w.fires)
                        fire.lowerID();
        }
}
