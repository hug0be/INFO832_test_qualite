package timer;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

/**
 * The DateTimer class implements the Timer interface and represents a timer based on dates or lap times.
 * It can be constructed with either a set of dates or a set of elapsed times between events.
 */
public class DateTimer  implements Timer {
	
	Vector<Integer> lapsTimes;
	Iterator<Integer> it;

	/**
	 * Constructs a new DateTimer object.
	 *
	 * @param dates a set of dates
	 */
	public DateTimer(TreeSet<Integer> dates) {
		this.lapsTimes = new Vector<Integer>();
		Integer last;
		Integer current=0;
		
		Iterator<Integer> itr = dates.iterator();
		while (itr.hasNext()) {
			last = current;
			current = itr.next();
			this.lapsTimes.add(current-last);
		}
		this.it = this.lapsTimes.iterator();

	}

	/**
	 * Constructs a new DateTimer object with the provided lap times.
	 *
	 * @param lapsTimes a set of elapsed times
	 */
	public DateTimer(Vector<Integer> lapsTimes) {
		this.lapsTimes = new Vector<Integer>(lapsTimes);
		this.it = this.lapsTimes.iterator();
	}

	/**
	 * return if the iterator has a next object
	 *
	 * @return it.hasNext()
	 */
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	/**
	 * return the next iterator
	 *
	 * @return it.next()
	 */
	@Override
	public Integer next() {
		return it.next();
	}

}
