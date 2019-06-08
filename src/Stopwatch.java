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

	public void start() {
		prevTime = System.nanoTime();
		getElapsed();
		running = true;
	}

	public void stop() {
		getElapsed();
		running = false;
	}

	public void pause() {
		setSpeed(0);
		getElapsed();
	}

	public void unpause() {
		speed = oldSpeed;
	}

	public long getElapsed() {
		long now = System.nanoTime();
		elapsed += (long)(speed * (now - prevTime));
		prevTime = now;
		return elapsed;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		oldSpeed = this.speed;
		this.speed = speed;
	}
}
