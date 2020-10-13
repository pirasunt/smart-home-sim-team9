import java.util.UUID;

public class RoomWall extends Wall{
	UUID connectedRoom;
	
	public RoomWall(UUID room) {
		super(WallType.DOOR);
		connectedRoom = room;
	}
	public UUID getConnectedRoom() {
		return connectedRoom;
	}
}
