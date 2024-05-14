package discrete_behavior_simulator;

/**
 * The ClockObserver interface represents an observer that can be notified about time changes in a Clock object.
 * Implementing classes can receive notifications when the clock time changes or when the next jump time is set.
 */
public interface ClockObserver {
	/**
	 * Called when the clock time changes.
	 *
	 * @param time the new time of the clock
	 */
	public void clockChange(int time);
	/**
	 * Called when the next jump time is set in the clock.
	 *
	 * @param nextJump the next jump time set in the clock
	 */
	public void nextClockChange(int nextJump);
}
