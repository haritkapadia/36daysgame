/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

/**
 * This class is used to store the in game time
 * 
 * Variables:
 * 
 * startTime    -The time that the game is resumed
 * offset       -Used to calculate the time that has elapsed
 * running      -True if the clock is running, false if otherwise
 * paused       -True if the game is paused
 * pauseTime    -Stores the time when the game was paused
 * prevTime     -Stores the time when the game started
 */
public class Stopwatch {
        long startTime;
        long offset;
        boolean running;
        boolean paused;
        long pauseTime;
        long prevTime;
        
        /**
         * Class constructor, initializes the variables
         * @param offset is used in calculating the time that has elapsed
         */
        Stopwatch(long offset) {
                this.offset = offset;
                startTime = -1;
                running = false;
                paused = false;
                pauseTime = -1;
                prevTime = -1;
        }
        
        /**
         * Starts the timer
         */
        public void start() {
                startTime = System.nanoTime();
                prevTime = startTime;
                running = true;
        }
        
        /**
         * Stops the timer
         */
        public void stop() {
                startTime = System.nanoTime();
                running = false;
        }
        
        /**
         * Pauses the timer
         */
        public void pause() {
                pauseTime = System.nanoTime();
                paused = true;
        }
        
        /**
         * Resumes the timer
         * 
         * Variables:
         * 
         * now     -Stores the current time
         */
        public void unpause() {
                long now = System.nanoTime();
                startTime -= now - pauseTime;
        }
        
        /**
         * Calculates the amount of time that has elapsed
         * 
         * Variables:
         * 
         * now      -Stores the current time
         */
        public long getElapsed() {
                long now = System.nanoTime();
                if(running) {
                        if(paused)
                                return pauseTime - startTime + offset;
                        else
                                return now - startTime + offset;
                } else {
                        return 0;
                }
        }
        
        // public long getDelta() {
        // long now = System.nanoTime();
        // long temp = prevTime;
        // prevTime = now;
        // if(running) {
        //  if(paused)
        //   return pauseTime - startTime - temp + offset;
        //  else
        //   return now - startTime - temp + offset;
        // } else {
        //  return 0;
        // }
        // }
}
