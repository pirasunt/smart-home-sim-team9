package Observers;

/**
 * The interface Time change observer.
 */
public interface TimeChangeObserver {

    /**
     * Updates the time of the observer
     *
     * @param newTime String representation of the new Time Value
     */
    void update(String newTime);
}
