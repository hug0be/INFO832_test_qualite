package timer;

/**
 * The MergedTimer class represents a timer that merges two other timers.
 * It implements the Timer interface.
 * <p>
 * A MergedTimer object is constructed with two timers. When iterating over a MergedTimer,
 * it returns the sum of the next intervals from both timers until either one of the timers
 * has no more intervals left.
 * <p>
 * Example usage:
 * <pre>
 * // Create two timers
 * Timer timer1 = new OneShotTimer(10);
 * Timer timer2 = new OneShotTimer(20);
 * // Create a MergedTimer by merging the two timers
 * MergedTimer mergedTimer = new MergedTimer(timer1, timer2);
 * // Retrieve the next merged time interval
 * int mergedInterval = mergedTimer.next(); // This will return 30
 * </pre>
 */
public class MergedTimer implements Timer{
	
	private Timer timer1;
	private Timer timer2;

	/**
	 * Constructs a new MergedTimer object.
	 *
	 * @param timer1 first timer to merge
	 * @param timer2 second timer to merge
	 */
	public MergedTimer(Timer timer1, Timer timer2) {
		this.timer1 = timer1;
		this.timer2 = timer2;
	}

	/**
	 * Verify if timers are empty
	 *
	 * @return if both timers are finished
	 */
	@Override
	public boolean hasNext() {
		return (this.timer1.hasNext() && this.timer2.hasNext());
	}

	/**
	 * return the addition of both timers next iterators
	 *
	 * @return next iterator
	 */
	@Override
	public Integer next() {
		if(this.hasNext()) {
			return this.timer1.next() + this.timer2.next();
		}
		return null;
	}

}
