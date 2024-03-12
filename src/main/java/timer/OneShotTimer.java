package timer;

public class OneShotTimer  implements Timer {
	
	private Integer at;
	private boolean hasNext;

	/**
	 * Constructs a new OneShotTimer object.
	 *
	 * @param at
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
