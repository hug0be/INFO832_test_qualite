package action;

import java.lang.reflect.Method;
import java.util.Iterator;
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
	 * The base action associated with this composite action.
	 */
	protected DiscreteAction firstAction;

	/**
	 * The set of dependent actions that need to be executed along with the base action.
	 */
	protected TreeSet<DiscreteAction> otherActions;

	/**
	 * An iterator over the dependent actions set.
	 */
	private Iterator<DiscreteAction> it;

	/**
	 * The current action being executed.
	 */
	protected DiscreteAction currentAction;



	/**
	 * Constructs a series of dependent actions based on the specified object, base method name, and timer.
	 *
	 * @param o the object on which the base method will be invoked
	 * @param baseMethodName the name of the base method to be invoked
	 * @param timerBase the timer object providing the base laps time
	 */
	public DiscreteActionCycle(Object o, String baseMethodName, Timer timerBase){
		this.firstAction = new DiscreteAction(o, baseMethodName, timerBase);
		this.otherActions = new TreeSet<>();
		this.it = this.otherActions.iterator();
		this.currentAction = this.firstAction;
	}

	/**
	 * Adds a dependence on the specified object, dependent method name, and timer.
	 *
	 * @param o the object on which the dependent method will be invoked
	 * @param depentMethodName the name of the dependent method to be invoked
	 * @param timerDependence the timer object providing the dependent laps time
	 */
	public void addDependence(Object o, String depentMethodName, Timer timerDependence) {
		this.otherActions.add(new DiscreteAction(o, depentMethodName, timerDependence));
	}
	

	/**
	 * Reinitializes the dependent actions.
	 */
	private void reInit() {
		for (Iterator<DiscreteAction> iter = this.otherActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		}
	}

	/**
	 * Moves to the next method in the sequence.
	 */
	public DiscreteActionInterface next(){
		if (this.currentAction == this.firstAction){
			this.it = this.otherActions.iterator();
			this.currentAction = this.it.next();
		}else if(this.currentAction == this.otherActions.last()){
			this.currentAction = this.firstAction;
			this.reInit();
		}else {
			this.currentAction = this.it.next();
		}
		return this;
	}

	/**
	 * Subtracts a certain time from the laps time of all dependent actions.
	 *
	 * @param t time to remove
	 */
	public void spendTime(int t) {
		for (Iterator<DiscreteAction> iter = this.otherActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    element.spendTime(t);
		}
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
	 * Checks if the series of dependent actions is empty.
	 *
	 * @return true if there are no dependent actions, false otherwise
	 */
	public Boolean isEmpty() {
		return !this.hasNext();
	}

	/**
	 * Checks if there is a next action available in the sequence.
	 *
	 * @return true if there is a next action, false otherwise
	 */
	public boolean hasNext() {
		return this.firstAction.hasNext() || !this.otherActions.isEmpty();
	}

}
