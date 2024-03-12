package timer;

/**
 * The PeriodicTimer class represents a timer that generates periodic time intervals.
 * It implements the Timer interface.
 * <p>
 * A PeriodicTimer object can be constructed with a fixed period, indicating the time interval between each occurrence.
 * Additionally, a PeriodicTimer object can be constructed with a more-or-less random timer, allowing for variations
 * in the occurrence time of each period.
 * <p>
 * Example usage:
 * <pre>
 * // Create a PeriodicTimer with a fixed period of 100 units
 * PeriodicTimer timer = new PeriodicTimer(100);
 * // Generate the next time interval
 * int interval = timer.next();
 * </pre>
 */
 public class PeriodicTimer implements Timer {

	private int period;
	private int next;
	private RandomTimer moreOrLess = null;

	/**
	 * Construct a PeriodicTimer object.
	 *
	 * @param at periodic time
	 */
	public PeriodicTimer(int at) {
		this.period = at;
		this.next = at;
	}
	
	/**
	 * set next to a time more or less a random timer
	 *
	 * @param at periodic time
	 * @param moreOrLess random number tu subtract or add
	 */
	@Deprecated
	public PeriodicTimer(int at, RandomTimer moreOrLess) {
		this.period = at;
		this.moreOrLess = moreOrLess;
		this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
	}

	/**
	 * Construct a PeriodicTimer object.
	 *
	 * @param period periodic time
	 * @param at next periodic time
	 */
	public PeriodicTimer(int period, int at) {
		this.period = period;
		this.next = at;
	}
	
	/**
	 * set next to a time more or less a random timer and set a period
	 *
	 * @param period next periodic time
	 * @param at periodic time
	 * @param moreOrLess random number tu subtract or add
	 */
	@Deprecated
	public PeriodicTimer(int period, int at, RandomTimer moreOrLess) {
		this.period = period;
		this.moreOrLess = moreOrLess;
		this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
	}

	/**
	 * return the period
	 *
	 * @return period
	 */
	public int getPeriod() {
		return this.period;
	}

	/**
	 * return the next number with a random parameter
	 *
	 * @return next the next number
	 */
	@Override
	public Integer next() {
		
		int next =  this.next;
		
		if(this.moreOrLess != null) {
			this.next = this.period + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
		}else {
			this.next = this.period;
		}
		
		return next;
	}
	
	/*@Override
	public Integer next(int since) {
		
		int next = (this.at - (since % this.period) + this.period) % this.period;
		
		if(this.moreOrLess != null) {
			next += this.moreOrLess.next() - this.moreOrLess.getMean();
			this.next = this.period * 2 - next;
		}else {
			this.next = this.period;
		}
		
		return next;
	}*/

	/**
	 * return if the timer has a next number
	 *
	 * @return true
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

}
