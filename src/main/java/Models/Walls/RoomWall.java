package Models.Walls;

import Enums.WallType;

/**
 * The type Room wall.
 */
public class RoomWall extends Wall{
	/**
	 * The id of the room that this wall is attached to.
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
	 * Gets connected rooms id.
	 *
	 * @return the connected rooms id
	 */
	public int getConnectedRoom() {
		return connectedRoom;
	}
}
