import java.util.UUID;

public class RoomWall extends Wall{
	int connectedRoom;
	
	public RoomWall(int room) {
		super(WallType.DOOR);
		connectedRoom = room;
	}
	public int getConnectedRoom() {
		return connectedRoom;
	}
}
