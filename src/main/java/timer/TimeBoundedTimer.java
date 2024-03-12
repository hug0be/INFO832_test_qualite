package timer;

/**
 * The TimeBoundedTimer class implements the Timer interface and represents a timer bounded by specific start and stop times.
 * It wraps another Timer implementation and provides time-bounded functionality based on the specified start and stop times.
 */
public class TimeBoundedTimer implements Timer {
	
	private Timer timer2bound;
	private Integer startTime;
	private Integer stopTime;
	
	private Integer next=0;
	private int time=0;
	private boolean hasNext;

	/**
	 * Construct a TimeBoundedTimer object.
	 *
	 * @param timer2bound base timer
	 * @param startTime time to start the timer
	 * @param stopTime time to stop the timer
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime, int stopTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.init();
	}

	/**
	 * Construct a TimeBoundedTimer object.
	 *
	 * @param timer2bound base timer
	 * @param startTime time to start the timer
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = Integer.MAX_VALUE;
		this.init();
	}

	/**
	 * Initialize the object
	 */
	private void init() {
		this.next = this.timer2bound.next();
		while (this.next < this.startTime) {
			this.next += this.timer2bound.next();
		}
		if(this.next<this.stopTime) {
			this.hasNext = true;
		}else {
			this.hasNext = false;
		}
	}

	/**
	 * return if there is a next time
	 *
	 * @return a boolean
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	/**
	 * Give the next time
	 *
	 * @return next time
	 */
	@Override
	public Integer next() {
		Integer next = this.next;
		this.time+=this.next;
		if(this.time < this.stopTime) {
			this.next = this.timer2bound.next();
		}else {
			next = null;
			this.hasNext=false;
		}
		return next;
	}

}
