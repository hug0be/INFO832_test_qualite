package action;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import timer.Timer;

/**
 * Represents a discrete action that can be performed.
 * <p>
 * This class provides methods to manage discrete actions such as spending time, retrieving method, current laps time,
 * and comparison between actions.
 * </p>
 *
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 * @see DiscreteActionInterface
 */

public class DiscreteAction implements DiscreteActionInterface {
	private Object object;
	private Method method;
	private Timer timer;				// timer provides new lapsTime
	private Integer lapsTime; 			// waiting time (null if never used)
	private Logger logger;

	/**
	 * Constructs a new DiscreteAction object with the specified object, method name, and timer.
	 *
	 * @param o the object on which the method will be invoked
	 * @param m the name of the method to be invoked
	 * @param timer the timer object providing the laps time
	 */
	public DiscreteAction(Object o, String m, Timer timer){
		// Start logger
		this.logger = Logger.getLogger("DAS");
		this.logger.setLevel(Level.ALL);
		this.logger.setUseParentHandlers(true);

		this.object = o;
		try {
			this.method = o.getClass().getDeclaredMethod(m);
		} catch(Exception e){
			throw new IllegalArgumentException("Method " + m + " not found in class " + o.getClass().getName());
		}
		this.timer = timer;
	}
	
	// ATTRIBUTION
	/**
	 * Subtracts a certain time from the attribute lapsTime.
	 * display getCurrentLapsTime()
	 *
	 * @param t time to remove
	 */
	public void spendTime(int t) {
		Integer old = this.lapsTime;
		if(this.lapsTime != null) {
			this.lapsTime -= t;
		}
		this.logger.log(Level.FINE, "[DA] operate spendTime on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
	}

	// RECUPERATION
	/**
	 * Retrieves the method associated with this discrete action.
	 *
	 * @return the method associated with this action
	 */
	public Method getMethod(){
		return method;
	}

	/**
	 * Retrieves the current laps time.
	 *
	 * @return the current laps time
	 */
	public Integer getCurrentLapsTime(){
		return lapsTime;
	}

	/**
	 * Retrieves the object associated with this discrete action.
	 *
	 * @return the object associated with this action
	 */
	public Object getObject(){
		return object;
	}



	// COMPARAISON
	/**
	 * Compares this discrete action with the specified discrete action for order.
	 *
	 * @param other the discrete action to be compared
	 * @return a negative integer, zero, or a positive integer as this action is less than, equal to, or greater than the specified action
	 */
	public int compareTo(DiscreteActionInterface other) {
		Integer thisLapsTime = this.getCurrentLapsTime();
		Integer otherLapsTime = other.getCurrentLapsTime();

		if (thisLapsTime == null && otherLapsTime == null) return 0;
		else if (thisLapsTime == null) return 1;
		else if (otherLapsTime == null) return -1;
		return thisLapsTime.compareTo(otherLapsTime);
	}

	/**
	 * Returns a string representation of this discrete action.
	 *
	 * @return a string representation of this action
	 */
	public String toString(){
		return "Object : " + this.object.getClass().getName() + "\n Method : " + this.method.getName()+"\n Stat. : "+ this.timer + "\n delay: " + this.lapsTime;
	}


	/**
	 * Performs the next action.
	 *
	 * @return this the next action
	 */
	public DiscreteActionInterface next() {
		Integer old = this.lapsTime;
		this.lapsTime = this.timer.next();
		this.logger.log(Level.FINE, "[DA] operate next on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
		return this;
	}

	/**
	 * Checks if there is a next action available.
	 *
	 * @return true if there is a next action, false otherwise
	 */
	public boolean hasNext() {
		Boolean more=false;
		if (this.timer != null && this.timer.hasNext()) {
			more = true;
		}
		return more;		
	}
	

}
