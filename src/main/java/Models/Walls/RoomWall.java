package Models.Walls;

import Enums.WallType;

/**
 * The type Room wall.
 */
public class RoomWall extends Wall{
	/**
	 * The Connected rooms id.
	 */
	int connectedRoom;

	/**
	 * Instantiates a new Room wall.
	 *
	 * @param room the room
	 */
	public RoomWall(int room) {
		super(WallType.DOOR);
		connectedRoom = room;
	}

	/**
	 * Gets connected room.
	 *
	 * @return the connected room
	 */
	public int getConnectedRoom() {
		return connectedRoom;
	}
}
