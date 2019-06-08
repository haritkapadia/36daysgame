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
public class Stopwatch implements java.io.Serializable {
        long elapsed;
        boolean running;
        long prevTime;
        double speed;
        double oldSpeed;
        
        /**
         * Class constructor, initializes the variables
         * @param offset is used in calculating the time that has elapsed
         */
        Stopwatch(long offset) {
                running = false;
                prevTime = -1;
                speed = 1;
                oldSpeed = speed;
                elapsed = offset;
        }
        
        /**
         * Starts the stopwatch
         */
        public void start() {
                prevTime = System.nanoTime();
                running = true;
        }
        
        /**
         * Stops the stopwatch
         */
        public void stop() {
                getElapsed();
                running = false;
        }
        
        /**
         * Pauses the stopwatch
         */
        public void pause() {
                setSpeed(0);
        }
        
        /**
         * Resumes the stopwatch
         */
        public void unpause() {
                speed = oldSpeed;
        }
        
        /**
         * @returns The amount of time that has elapsed
         */
        public long getElapsed() {
                long now = System.nanoTime();
                elapsed += (long)(speed * (now - prevTime));
                prevTime = now;
                return elapsed;
        }
        
        /**
         * @returns the stopwatch speed
         */
        public double getSpeed() {
                return speed;
        }
        
        /**
         * Sets the stopwatch speed
         */
        public void setSpeed(double speed) {
                oldSpeed = this.speed;
                this.speed = speed;
        }
}
