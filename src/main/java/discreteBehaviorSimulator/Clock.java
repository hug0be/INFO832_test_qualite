package discreteBehaviorSimulator;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * The Clock class represents a virtual clock used in simulations.
 * It manages time and notifies observers about time changes.
 * <p>
 * Clock is implemented as a singleton pattern.
 * <p>
 * The clock can be set as virtual or real time.
 * <p>
 * Clock supports adding and removing observers to get notified about time changes.
 *
 * @author Flavien Vernier
 */
public class Clock {
	private static Clock instance = null;
	
	private int time;
	private int nextJump;
	private ReentrantReadWriteLock lock;
	private boolean virtual;
	
	
	private Set<ClockObserver> observers;
	
	/**
	* Constructor for the Clock class, initializing time and nextJump at 0.
	*/
	private Clock() {
		this.time = 0;
		this.nextJump=0;
		this.lock = new ReentrantReadWriteLock();
		this.virtual = true;
		this.observers = new HashSet<ClockObserver>();
	}
	
	/**
	* Returns the instance of the Clock class.
	*
	* @return      the Clock instance
	*/
	public static Clock getInstance() {
		if (Clock.instance == null) {
			Clock.instance = new Clock();
		}
		return Clock.instance;
	}
	
	/**
	* Adds an observer to the Clock.
	*
	* @param  o  the ClockObserver to add
	*/
	public void addObserver(ClockObserver o) {
		this.observers.add(o);
	}
	
	/**
	* Removes an observer from the Clock.
	*
	* @param  o  the ClockObserver to remove
	*/
	public void removeObserver(ClockObserver o) {
		this.observers.remove(o);
	}
	
	/**
	* Sets the virtual status.
	*
	* @param  virtual  Boolean to set the virtual status to
	*/
	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}
	
	/**
	* Returns True if the Clock is virtual, False if not.
	*
	* @return      boolean representing the virtual state.
	*/
	public boolean isVirtual() {
		return this.virtual;
	}
	
	/**
	* Sets the next jump and informs all the observers.
	*
	* @param  nextJump  Integer representing the next jump time.
	*/
	public void setNextJump(int nextJump) {
		this.nextJump = nextJump;
		for(ClockObserver o:this.observers) {
			o.nextClockChange(this.nextJump);
		}
	}
	/*public void setTime(int time) throws IllegalAccessException {
		this.lock.lock();
		if (this.time < time) {
			this.time = time;
			for(ClockObserver o:this.observers) {
				o.ClockChange();
			}
		}else{
			this.lock.unlock();
			throw new IllegalAccessException("Set time error, cannot go back to the past !!!");
		}
		this.lock.unlock();
	}*/
	
	/**
	* Increases the time of the Clock to the next jump.
	*
	* @param  time  the time to increase to
	* @throws Exception time change if the time parameter is different from the next jump
	*/
	public void increase(int time) throws Exception {

		this.lockWriteAccess();

		if(time != this.nextJump) {
			throw new Exception("Unexpected time change");
		}
		this.time += time;
		for(ClockObserver o:this.observers) {
			o.clockChange(this.time);
		}
		this.unlockWriteAccess();
	}
	
	/**
	* Returns the current time of the Clock.
	*
	* @return      current time of the Clock in type long
	*/
	public long getTime() {
		if(this.virtual) {
			return this.time;
		}else {
			return new Date().getTime();
		}
	}
	
	/**
	* Locks the read access to the Clock.
	*/
	public void lockReadAccess() {
		this.lock.readLock().lock();
	}
	
	/**
	* Unlocks the read access to the Clock.
	*/
	public void unlockReadAccess() {
		this.lock.readLock().unlock();
	}
	
	/**
	* Locks the write access to the Clock.
	*/
	public void lockWriteAccess() {
		this.lock.writeLock().lock();
	}
	
	/**
	* Unlocks the write access to the Clock.
	*/
	public void unlockWriteAccess() {
		this.lock.writeLock().unlock();		
	}
	
	/**
	* Returns the current time of the clock.
	*
	* @return   current time of the clock in type String
	*/
	public String toString() {
		return ""+this.time;
	}
}
