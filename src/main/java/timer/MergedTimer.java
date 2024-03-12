package timer;

public class MergedTimer implements Timer{
	
	private Timer timer1;
	private Timer timer2;

	/**
	 * Constructs a new MergedTimer object.
	 *
	 * @param timer1
	 * @param timer2
	 */
	public MergedTimer(Timer timer1, Timer timer2) {
		this.timer1 = timer1;
		this.timer2 = timer2;
	}

	/**
	 * Verify if timers are empty
	 *
	 * @return timer1.hasNext() && timer2.hasNext() if both timers are finished
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
