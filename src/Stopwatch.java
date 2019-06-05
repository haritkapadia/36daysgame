/**
 * Harit Kapadia, Jack Farley
 * Ms. Krasteva
 * 2019/June/02
 */

public class Stopwatch {
	long startTime;
	long offset;
	boolean running;
	boolean paused;
	long pauseTime;
	long prevTime;

	Stopwatch(long offset) {
		this.offset = offset;
		startTime = -1;
		running = false;
		paused = false;
		pauseTime = -1;
		prevTime = -1;
	}

	public void start() {
		startTime = System.nanoTime();
		prevTime = startTime;
		running = true;
	}

	public void stop() {
		startTime = System.nanoTime();
		running = false;
	}

	public void pause() {
		pauseTime = System.nanoTime();
		paused = true;
	}

	public void unpause() {
		long now = System.nanoTime();
		startTime -= now - pauseTime;
	}

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
	//	long now = System.nanoTime();
	//	long temp = prevTime;
	//	prevTime = now;
	//	if(running) {
	//		if(paused)
	//			return pauseTime - startTime - temp + offset;
	//		else
	//			return now - startTime - temp + offset;
	//	} else {
	//		return 0;
	//	}
	// }
}
