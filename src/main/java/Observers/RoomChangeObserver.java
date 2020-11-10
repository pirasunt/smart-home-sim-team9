package Observers;

import Models.Room;

/**
 * The interface Room change observer.
 */
public interface RoomChangeObserver {

    /**
     * Updates observers any time a user changes room location
     *
     * @param oldRoomID the old room id
     * @param newRoomID the new room id
     */
    void update(int oldRoomID, int newRoomID);
}
