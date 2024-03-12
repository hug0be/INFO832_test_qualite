package action;

import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;

import timer.DateTimer;
import timer.Timer;

/**
 * Represents a discrete action with On-Off dependence.
 * <p>
 * This class manages an action sequence with On-Off dependence, where the first action called is On,
 * and then method nextMethod() is called to select the next action.
 * </p>
 *
 * @author flver
 * @see DiscreteActionInterface
 */
public class DiscreteActionOnOffDependent implements DiscreteActionInterface {

	/**
	 * The action to be executed when the entity is turned on.
	 * This action should implement the DiscreteActionInterface.
	 */
	protected DiscreteActionInterface onAction;

	/**
	 * The action to be executed when the entity is turned off.
	 * This action should implement the DiscreteActionInterface.
	 */
	protected DiscreteActionInterface offAction;

	/**
	 * The current action associated with the entity.
	 * This action can either be the onAction or offAction based on the current state of the entity.
	 */
	protected DiscreteActionInterface currentAction;
	private Integer currentLapsTime;
	private Integer lastOffDelay=0;

	/*public DiscreteActionOnOffDependent(Wo o, Method on, Timer timerOn, Method off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		
		this.currentAction = this.onAction;
	}*/

	/**
	 * Constructs an On-Off dependent action sequence based on the specified object, On method dates, Off method dates.
	 *
	 * @param o the object on which the methods will be invoked
	 * @param on the name of the On method
	 * @param timerOn the timer for the On method
	 * @param off the name of the Off method
	 * @param timerOff the timer for the Off method
	 */
	public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		
		this.currentAction = this.offAction;
		this.currentLapsTime = 0;
	}

	/**
	 * Constructs On and Off timelapses, On method dates, Off method dates.
	 *
	 * @param datesOn dates to put in timelapses
	 * @param datesOff  dates to put in timelapses
	 * @param timeLapseOn timelapse of On dates to fill
	 * @param timeLapseOff  timelapse of Off dates to fill
	 */
	private void dates2Timalapse(TreeSet<Integer> datesOn, TreeSet<Integer> datesOff, Vector<Integer> timeLapseOn, Vector<Integer> timeLapseOff) {
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

	/**
	 * Constructs an On-Off dependent action sequence based on the specified object, On method, On method dates, Off method, and Off method dates.
	 * <p>
	 * This constructor initializes the On and Off actions with the provided object and method names, associating them with the respective timers created from the given dates.
	 * The current action is determined based on the earliest date between On and Off dates.
	 * </p>
	 *
	 * @param o the object on which the methods will be invoked
	 * @param on the name of the On method
	 * @param datesOn the dates for the On method
	 * @param off the name of the Off method
	 * @param datesOff the dates for the Off method
	 * @see DateTimer
	 */
	public DiscreteActionOnOffDependent(Object o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){
		/*Vector<Integer> timeLapseOn = new Vector<Integer>();
		Vector<Integer> timeLapseOff = new Vector<Integer>();
		
		dates2Timalapse((TreeSet<Integer>)datesOn.clone(), (TreeSet<Integer>)datesOff.clone(), timeLapseOn, timeLapseOff);
		*/
		this.onAction = new DiscreteAction(o, on, new DateTimer(datesOn));
		this.offAction = new DiscreteAction(o, off, new DateTimer(datesOff));
		
		
		
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.onAction;
		}else{
			this.currentAction = this.offAction;
		}
	}

	/**
	 * Switches to the next action in the sequence.
	 */
	public void nextAction(){
		if (this.currentAction == this.onAction){
			this.currentAction = this.offAction;
			this.currentAction = this.currentAction.next();
			this.lastOffDelay = this.currentAction.getCurrentLapsTime();
		}else{
			this.currentAction = this.onAction;
			this.currentAction = this.currentAction.next();
			this.currentAction.spendTime(this.lastOffDelay);
		}
	}

	/**
	 * Decreases the time of the current action.
	 *
	 * @param t the time to decrease
	 */
	public	void spendTime(int t) {
		this.currentAction.spendTime(t);
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
	 * Retrieves the current laps time of the current action.
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
	 * Retrieves the next action in the sequence.
	 *
	 * @return the next action
	 */
	public DiscreteActionInterface next() {
		this.nextAction();
		return this;
	}

	/**
	 * Checks if there is a next action available in the sequence.
	 *
	 * @return true if there is a next action, false otherwise
	 */
	public boolean hasNext() {
		return this.onAction.hasNext() || this.offAction.hasNext();		
	}

	

}
