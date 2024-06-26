
package discrete_behavior_simulator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import action.DiscreteActionInterface;


/**
 * The DiscreteActionSimulator class simulates the execution of discrete actions over time.
 * It manages a list of discrete actions and executes them based on their defined laps times.
 * Actions are executed sequentially, and the simulation can run for a finite or infinite number of loops.
 * <p>
 * This simulator provides functionalities to:
 * - Start and stop the simulation.
 * - Set the number of loops for the simulation.
 * - Add actions to the list of actions.
 * - Retrieve the current laps time before the next action.
 * <p>
 * The simulator uses a logger for logging simulation events and a global time instance to synchronize action execution.
 *
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 * @see DiscreteActionInterface
 * @see Clock
 */
public class DiscreteActionSimulator implements Runnable {


	private Thread t;
	private boolean running = false;
	
	private Clock globalTime;
	
	Vector<DiscreteActionInterface> actionsList = new Vector<>();
	
	private int nbLoop;
	private int step;
	
	private Logger logger;
	private FileHandler logFile; 
	private ConsoleHandler logConsole; 

	/**
	* Constructor for the DiscreteActionSimulator class,
	* starts a logger and a thread.
	*/
	public DiscreteActionSimulator(){
		
		// Start logger
		this.logger = Logger.getLogger("DAS");
		//this.logger = Logger.getLogger("APP");
		this.logger.setLevel(Level.ALL);
		this.logger.setUseParentHandlers(true);
		try{
			this.logFile = new FileHandler(this.getClass().getName() + ".log");
			//this.logFile.setFormatter(new SimpleFormatter());
			this.logFile.setFormatter(new LogFormatter());
			this.logConsole = new ConsoleHandler();
		}catch(Exception e){
			e.printStackTrace();
		}
		this.logger.addHandler(logFile);
		this.logger.addHandler(logConsole);
		

		this.globalTime = Clock.getInstance();
		
		this.t = new Thread(this);
		this.t.setName("Discrete Action Simulator");
	}
	
	/**
	 * Sets the number of loops for the simulation, which can be finite or infinite.
	 * 
	 * @param nbLoop defines the number of loop for the simulation, the simulation is infinite if nbLoop is negative or 0.
	 */
	public void setNbLoop(int nbLoop){
		if(nbLoop>0){
			this.nbLoop = nbLoop;
			this.step = 1;
		}else{ // running infinitely
			this.nbLoop = 0;
			this.step = -1;
		}
	}
	
	/**
	* Adds an action to the list of actions.
	*
	* @param  c  object implementing the DiscreteActionInterface
	*/
	public void addAction(DiscreteActionInterface c){

		if(c.hasNext()) {
			// add to list of actions, next is call to the action exist at the first time
			this.actionsList.add(c.next());

			// sort the list for ordered execution 
			Collections.sort(this.actionsList);
		}
	}
	
	/*public void addTemporalRule(TemporalRule r){
		
	}*/

	/**
	 * Returns the current laps time before the next action.
	 * 
	 * @return the laps time before the next action
	 */
	private int nextLapsTime() {
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		return currentAction.getCurrentLapsTime();
	}
	/**
	 * Runs the next action in the stored list and display its information.
	 * 
	 * @return laps time of the running action
	 */
	private int runAction(){
		// Run the first action
		int sleepTime = 0;


		// TODO Manage parallel execution !  
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		Object o = currentAction.getObject();
		Method m = currentAction.getMethod();
		sleepTime = currentAction.getCurrentLapsTime();
		
		try {
			//Thread.sleep(sleepTime); // Real time can be very slow
			Thread.yield();
			//Thread.sleep(1000); // Wait message bus sends
			if(this.globalTime!=null) {
				this.globalTime.increase(sleepTime);
			}
			m.invoke(o);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + " after " + sleepTime + " time units\n");
				System.out.println("[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + " after " + sleepTime + " time units\n");
			}else {
				this.logger.log(Level.FINE, "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " after " + sleepTime + " time units\n");
				System.out.println("[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " after " + sleepTime + " time units\n");
			
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		return sleepTime;
	}

	/**
	* Updates the time of all actions stored in the list and displays information about the reset action if present.
	*
	* @param  runningTimeOf1stCapsul  time for the simulator to spend
	*/
	void updateTimes(int runningTimeOf1stCapsul){
		
		// update time laps off all actions
		for(int i=1 ; i < this.actionsList.size(); i++){
			//int d = this.actionsList.get(i).getLapsTime();
			//this.actionsList.get(i).setLapsTemps(d- runningTimeOf1stCapsul);
			this.actionsList.get(i).spendTime(runningTimeOf1stCapsul);
		}

		// get new time lapse of first action
		/*if(this.globalTime == null) {
			this.actionsList.get(0).updateTimeLaps();
		}else {	
			this.actionsList.get(0).updateTimeLaps(this.globalTime.getTime());
		}
		
		// remove the action if no more lapse time is defined
		if(this.actionsList.get(0).getLastLapsTime() == null) {
			this.actionsList.remove(0);
		}else {
			// resort the list
			Collections.sort(this.actionsList);
		}*/

		DiscreteActionInterface a = this.actionsList.remove(0);
		if(a.hasNext()) {
			a = a.next();
			this.actionsList.addElement(a);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + " time units\n");
				System.out.println("[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + " time units\n");
			}else {
				this.logger.log(Level.FINE, "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + " time units\n");
				System.out.println("[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + " time units\n");
			}
			Collections.sort(this.actionsList);
		}
	}

	/**
	* Runs the simulation for the set amount of loops and displays the number of actions done and the end loop if earlier than the specified amount.
	*/
	public void run() {
		int count = this.nbLoop;
		boolean finished = false;

		System.out.println("LANCEMENT DU THREAD " + t.getName() + " \n");

		while(running && !finished){

			if(!this.actionsList.isEmpty()){
				System.out.println(this);
				this.globalTime.setNextJump(this.nextLapsTime());
				
				this.globalTime.lockWriteAccess();
				int runningTime = this.runAction();
				this.updateTimes(runningTime);
				this.globalTime.unlockWriteAccess();
				try {
					Thread.sleep(100);
				}catch(Exception e) {
					e.printStackTrace();
				}
				//TODO add global time synchronizer for action with list of date and avoid drift 
			}else{
				System.out.println("NOTHING TO DO\n");
				this.stop();
			}

			count -= this.step;
			if(count<=0) {
				finished = true;
			}
		}
		this.running = false;
		if(this.step>0) {
			System.out.println("DAS: " + (this.nbLoop - count) + " actions done for " + this.nbLoop + " turns asked.");
		}else {
			System.out.println("DAS: " + (count) + " actions done!");			
		}
	}

	/**
	* Starts the thread.
	*/
	public void start(){
		this.running = true;
		this.t.start();
	}

	/**
	* Stops the thread.
	*/
	public void stop(){
		System.out.println("STOP THREAD " + t.getName() + "obj " + this);
		this.running = false;
	}
	
	/**
	* Displays the actions stored in the list and their amount.
	*/
	public String toString(){
		StringBuffer toS = new StringBuffer("------------------\nTestAuto :" + this.actionsList.size());
		for(DiscreteActionInterface c : this.actionsList){
			toS.append(c.toString() + "\n");
		}
		toS.append("---------------------\n");
		return toS.toString();
	}

	/**
	* Assesses the run state of the simulator.
	* 
	* @return boolean representing whether the simulator is running or not.
	*/
	public boolean getRunning() {
		return this.running;
	}

}
