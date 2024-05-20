package action;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

import timer.Timer;

/**
 * Represents a series of dependent actions based on a base action.
 * <p>
 * This class manages a series of dependent actions associated with a base action.
 * </p>
 *
 * @author flver
 * @see DiscreteActionInterface
 */
public class DiscreteActionCycle implements DiscreteActionInterface {
	/**
	 * The set of dependent actions that need to be executed along with the base action.
	 */
	protected TreeSet<DiscreteActionInterface> actions;

	/**
	 * An iterator over the dependent actions set.
	 */
	private Queue<DiscreteActionInterface> it;

	/**
	 * The current action being executed.
	 */
	protected DiscreteActionInterface currentAction;



	/**
	 * Constructs a series of dependent actions based on a DiscreteAction with the specified object, base method name, and timer.
	 *
	 * @param o the object on which the base method will be invoked
	 * @param baseMethodName the name of the base method to be invoked
	 * @param timerBase the timer object providing the base laps time
	 */
	public DiscreteActionCycle(Object o, String baseMethodName, Timer timerBase){
		this(new DiscreteAction(o, baseMethodName, timerBase));
	}

	/**
	 * Constructs a series of dependent actions based on the specified object, base method name, and timer.
	 *
	 * @param action the base action
	 */
	public DiscreteActionCycle(DiscreteActionInterface action){
		this.actions = new TreeSet<>();
		this.addDependence(action);
		this.it = new PriorityQueue<>(this.actions);
		this.currentAction = this.it.poll();
	}

	/**
	 * Adds a specified dependence
	 *
	 * @param action the new dependent action
	 */
	public void addDependence(DiscreteActionInterface action) {
		if(!this.actions.add(action)) throw new IllegalArgumentException("Action already exists");
	}

	/**
	 * Moves to the next method in the sequence.
	 */
	public DiscreteActionInterface next() {
		// Reinitialize the iterator if the sequence is empty
		if(this.it.isEmpty()) {
			if(!this.hasNext()) throw new NoSuchElementException("No more actions");
			this.it = new PriorityQueue<>(this.actions);
		}
		this.currentAction = this.it.poll();
		return this.currentAction;
	}

	/**
	 * Subtracts a certain time from the laps time of all dependent actions.
	 *
	 * @param t time to remove
	 */
	public void spendTime(int t) {
		this.currentAction.spendTime(t);
		if(this.currentAction.getCurrentLapsTime() == null && this.hasNext()) this.next();
	}

	/**
	 * Retrieves the method associated with the current action.
	 *
	 * @return the method associated with the current action
	 */
	public Method getMethod() {
		return this.currentAction.getMethod();
	}

	/**
	 * Retrieves the current laps time.
	 *
	 * @return the current laps time
	 */
	public Integer getCurrentLapsTime() {
		return this.currentAction.getCurrentLapsTime();
	}

	/**
	 * Retrieves the object associated with the current action.
	 *
	 * @return the object associated with the current action
	 */
	public Object getObject() {
		return this.currentAction.getObject();
	}

	/**
	 * Compares this action with the specified action for order.
	 *
	 * @param c the action to be compared
	 * @return a negative integer, zero, or a positive integer as this action is less than, equal to, or greater than the specified action
	 */
	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}

	/**
	 * Checks if there is a next action available in the sequence.
	 *
	 * @return true if there is a next action, false otherwise
	 */
	public boolean hasNext() {
		for (DiscreteActionInterface it : this.actions) {
			if (it.hasNext()) return true;
		}
		return false;
	}
}
