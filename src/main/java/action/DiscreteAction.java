package action;

import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import discreteBehaviorSimulator.LogFormatter;
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

// TODO must inherit from Action
public class DiscreteAction implements DiscreteActionInterface {
	private Object object;
	private Method method;
	
	
	private Timer timmer;				// timer provides new lapsTime
	//private TreeSet<Integer> dates;	// obsolete, managed in timer 
	//private Vector<Integer> lapsTimes;// obsolete, managed in timer
	private Integer lapsTime; 			// waiting time (null if never used)
	
	private Logger logger;

	// Constructor
	/**
	 * Constructs a new DiscreteAction object.
	 */
	private DiscreteAction() {
		// Start logger
			this.logger = Logger.getLogger("DAS");
			//this.logger = Logger.getLogger("APP");
			this.logger.setLevel(Level.ALL);
			this.logger.setUseParentHandlers(true);
			
			/*FileHandler logFile; 
			ConsoleHandler logConsole; 
			try{
				this.logFile = new FileHandler(this.getClass().getName() + ".log");
				//this.logFile.setFormatter(new SimpleFormatter());
				this.logFile.setFormatter(new LogFormatter());
				this.logConsole = new ConsoleHandler();
			}catch(Exception e){
				e.printStackTrace();
			}
			this.logger.addHandler(logFile);
			this.logger.addHandler(logConsole);*/
	}

	/**
	 * Constructs a new DiscreteAction object with the specified object, method name, and timer.
	 *
	 * @param o the object on which the method will be invoked
	 * @param m the name of the method to be invoked
	 * @param timmer the timer object providing the laps time
	 */
	public DiscreteAction(Object o, String m, Timer timmer){
		this();
		this.object = o;
		try{	
			this.method = o.getClass().getDeclaredMethod(m, new Class<?>[0]);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.timmer = timmer;
		//this.updateTimeLaps();
	}
	
	// ATTRIBUTION
	/**
	 * Subtracts a certain time from the attribute lapsTime.
	 *
	 * @param t time to remove
	 * @see #getCurrentLapsTime()
	 */
	public void spendTime(int t) {
		Integer old = this.lapsTime;
		if(this.lapsTime != null) {
			this.lapsTime -= t;
		}
		this.logger.log(Level.FINE, "[DA] operate spendTime on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
		//System.out.println(         "[DA] operate spendTime on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime() + "\n");
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
	 * @param c the discrete action to be compared
	 * @return a negative integer, zero, or a positive integer as this action is less than, equal to, or greater than the specified action
	 */
	public int compareTo(DiscreteActionInterface c) {
		if (this.lapsTime == null) { // no lapstime is equivalent to infinity 
			return 1;
		}
		if (c.getCurrentLapsTime() == null) {// no lapstime is equivalent to infinity 
			return -1;
		}
    	if(this.lapsTime > c.getCurrentLapsTime()){
    		return 1;
    	}
    	if(this.lapsTime < c.getCurrentLapsTime()){
    		return -1;
    	}
		if(this.lapsTime == c.getCurrentLapsTime()){
			return 0;
		}
		return 0;
	}

	/**
	 * Returns a string representation of this discrete action.
	 *
	 * @return a string representation of this action
	 */
	public String toString(){
		return "Object : " + this.object.getClass().getName() + "\n Method : " + this.method.getName()+"\n Stat. : "+ this.timmer + "\n delay: " + this.lapsTime;

	}


	/**
	 * Performs the next action.
	 *
	 * @return this the next action
	 */
	public DiscreteActionInterface next() {
		Integer old = this.lapsTime;
		this.lapsTime = this.timmer.next();
		this.logger.log(Level.FINE, "[DA] operate next on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
		//System.out.println("[DA] operate 'next' on " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime() + "\n");
		return this;
	}

	/**
	 * Checks if there is a next action available.
	 *
	 * @return true if there is a next action, false otherwise
	 */
	public boolean hasNext() {
		Boolean more=false;
		if (this.timmer != null && this.timmer.hasNext()) {
			more = true;
		}/*else if (this.dates != null) {
			more = !this.dates.isEmpty();
		}else if (this.lapsTimes != null) {
			more = !this.lapsTimes.isEmpty();
		}*/
		return more;		
	}
	

}
