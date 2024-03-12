package timer;

public class PeriodicTimer implements Timer {

	private int period;
	private int next;
	private RandomTimer moreOrLess = null;

	/**
	 * Construct a PeriodicTimer object.
	 *
	 * @param at
	 */
	public PeriodicTimer(int at) {
		this.period = at;
		this.next = at;
	}
	
	/**
	 * set next to a time more or less a random timer
	 *
	 * @param at
	 * @param moreOrLess
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
	 * @param period
	 * @param at
	 */
	public PeriodicTimer(int period, int at) {
		this.period = period;
		this.next = at;
	}
	
	/**
	 * set next to a time more or less a random timer and set a period
	 *
	 * @param period
	 * @param at
	 * @param moreOrLess
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
