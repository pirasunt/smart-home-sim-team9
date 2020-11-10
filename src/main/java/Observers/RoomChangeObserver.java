package Observers;

import Models.Room;

public interface RoomChangeObserver {

    void update(int oldRoomID, int newRoomID);
}
