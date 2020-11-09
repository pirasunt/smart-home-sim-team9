package Controllers;

import Models.Room;

public interface Observer {

    void update(int oldRoomID, int newRoomID);
}
