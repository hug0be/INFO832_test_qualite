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
//TODO Must be refactored to be generic
public class DiscreteActionDependent implements DiscreteActionInterface {

	/**
	 * The base action associated with this composite action.
	 */
	protected DiscreteAction baseAction;

	/**
	 * The set of dependent actions that need to be executed along with the base action.
	 */
	protected TreeSet<DiscreteAction> depedentActions;

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
	public DiscreteActionDependent(Object o, String baseMethodName, Timer timerBase){
		this.baseAction = new DiscreteAction(o, baseMethodName, timerBase);
		this.depedentActions = new TreeSet<DiscreteAction>();
		this.it = this.depedentActions.iterator();
		this.currentAction = this.baseAction;
	}

	/**
	 * Adds a dependence on the specified object, dependent method name, and timer.
	 *
	 * @param o the object on which the dependent method will be invoked
	 * @param depentMethodName the name of the dependent method to be invoked
	 * @param timerDependence the timer object providing the dependent laps time
	 */
	public void addDependence(Object o, String depentMethodName, Timer timerDependence) {
		this.depedentActions.add(new DiscreteAction(o, depentMethodName, timerDependence));
	}
	
	/*private void dates2Timalapse(TreeSet<Integer> datesOn, TreeSet<Integer> datesOff, Vector<Integer> timeLapseOn, Vector<Integer> timeLapseOff) {
		Vector<Integer> currentTimeLapse;
		TreeSet<Integer> currentDates;
		Integer date=0;
		if(datesOn.first()<datesOff.first()) {
			currentTimeLapse = timeLapseOn;
			currentDates = datesOn;
		}else {
			currentTimeLapse = timeLapseOff;	
			currentDates = datesOff;		
		}
		Integer nextDate;
		
		while(datesOn.size()>0 || datesOff.size()>0) {
			nextDate = currentDates.first();
		
			currentTimeLapse.add(nextDate - date);
			currentDates.remove(nextDate);
		
			date = nextDate;
			
			if(currentDates == datesOn) {
				currentDates = datesOff;
				currentTimeLapse = timeLapseOff;
			}else {
				currentDates = datesOn;
				currentTimeLapse = timeLapseOn;			
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	public DiscreteActionDependent(Wo o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){
		Vector<Integer> timeLapseOn = new Vector<Integer>();
		Vector<Integer> timeLapseOff = new Vector<Integer>();
		
		dates2Timalapse((TreeSet<Integer>)datesOn.clone(), (TreeSet<Integer>)datesOff.clone(), timeLapseOn, timeLapseOff);
		
		this.baseAction = new DiscreteAction(o, on, timeLapseOn);
		this.offAction = new DiscreteAction(o, off, timeLapseOff);
		
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.baseAction;
		}else{
			this.currentAction = this.offAction;
		}
	}
*/

	/**
	 * Reinitializes the dependent actions.
	 */
	private void reInit() {
		//this.baseAction.updateTimeLaps();
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    //element.updateTimeLaps();
		}		
	}

	/**
	 * Moves to the next method in the sequence.
	 */
	public void nextMethod(){
		if (this.currentAction == this.baseAction){
			this.it = this.depedentActions.iterator();
			this.currentAction = this.it.next();
		}else if(this.currentAction == this.depedentActions.last()){
			this.currentAction = this.baseAction;
			this.reInit();
		}else {
			this.currentAction = this.it.next();
		}
	}

	/**
	 * Subtracts a certain time from the laps time of all dependent actions.
	 *
	 * @param t time to remove
	 */
	public void spendTime(int t) {
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    element.spendTime(t);
		}
	}

	/**
	 * Updates the laps time.
	 */
	public void updateTimeLaps() {
		// time laps is updated at the re-initialization
		//this.currentAction.updateTimeLaps();	
		this.nextMethod();	
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
	 * Retrieves the next action in the sequence.
	 *
	 * @return the next action
	 */
	public DiscreteActionInterface next() {
		//Integer lapsTime = this.getNextLapsTime();
		Method method = this.getMethod();
		Object object = this.getObject();
		return this;
	}

	/**
	 * Checks if there is a next action available in the sequence.
	 *
	 * @return true if there is a next action, false otherwise
	 */
	public boolean hasNext() {
		return this.baseAction.hasNext() || !this.depedentActions.isEmpty();		
	}

}
