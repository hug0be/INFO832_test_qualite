package timer;

/**
 * The OneShotTimer class represents a timer that generates a single time interval.
 * It implements the Timer interface.
 * <p>
 * A OneShotTimer object is constructed with a specific time value, after which it
 * produces that value once and then stops.
 * <p>
 * Example usage:
 * <pre>
 * // Create a OneShotTimer set to trigger at time 100
 * OneShotTimer timer = new OneShotTimer(100);
 * // Retrieve the next time interval
 * int interval = timer.next();
 * </pre>
 */
 public class OneShotTimer  implements Timer {
	
	private Integer at;
	private boolean hasNext;

	/**
	 * Constructs a new OneShotTimer object.
	 *
	 * @param at next time
	 */
	public OneShotTimer(int at) {
		this.at = at;
		this.hasNext = true;
	}

	/**
	 * return if it has a next iterator
	 *
	 * @return hasNext
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	/**
	 * return the next number
	 *
	 * @return next corresponding to at
	 */
	@Override
	public Integer next() {
		Integer next=this.at;
		this.at=null;
		this.hasNext = false;
		return next;
	}

}
