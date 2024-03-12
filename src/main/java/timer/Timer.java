package timer;

import java.util.Iterator;

/**
 * The Timer interface extends the Iterator interface and represents a timer that provides delay times as integers.
 * It defines a method to retrieve the next delay time.
 */
 public interface Timer extends Iterator<Integer>{
	/**
	 * return the delay time
	 * @see java.util.Iterator#next()
	 */
	public Integer next();
	/*
	 * return the delay time
	 * @see java.util.Iterator#next()
	 */
	//public Integer next(int now);
}
