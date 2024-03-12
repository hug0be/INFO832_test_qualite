package action;

import java.lang.reflect.Method;
import java.util.Iterator;

/*
 * TODO must implement Iterator<Action>
 */
public interface DiscreteActionInterface extends Comparable<DiscreteActionInterface>, Iterator<DiscreteActionInterface>{

	/**
	 * Decreases the time of the discrete action.
	 *
	 * @param t the time to decrease
	 */
	public	void spendTime(int t);

	/**
	 * Retrieves the method to execute.
	 *
	 * @return the method to execute
	 */
	public Method getMethod();

	/**
	 * Retrieves the current laps time without updating.
	 *
	 * @return the current laps time
	 */
	public Integer getCurrentLapsTime();

	/**
	 * Retrieves the object on which the method must be invoked.
	 *
	 * @return the object on which the method must be invoked
	 */
	public Object getObject();

	// COMPARAISON
	/**
	 * Compares this discrete action with the specified discrete action for order.
	 *
	 * @param c the discrete action to be compared
	 * @return a negative integer, zero, or a positive integer as this action is less than, equal to, or greater than the specified action
	 */
	public int compareTo(DiscreteActionInterface c);

	/**
	 * Returns the next discrete action.
	 *
	 * @return the next discrete action
	 */
	public DiscreteActionInterface next();
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	//public boolean hasNext();
}
