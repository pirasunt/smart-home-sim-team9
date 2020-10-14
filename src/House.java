import java.util.*;

public class House {
	ArrayList<Room> rooms = new ArrayList<Room>();
	//TO REMOVE
	public House() {
		UUID room1ID = UUID.randomUUID();
		UUID room2ID = UUID.randomUUID();
		UUID room3ID = UUID.randomUUID();
		
		Room room1 = new Room("Room1", new RoomWall(room3ID), new OutsideWall(), new Wall(), new RoomWall(room2ID), room1ID);
		Room room2 = new Room("Room2", new Wall(), new Wall(), new RoomWall(room1ID), new WindowWall(), room2ID);
		Room room3 = new Room("Room3", new WindowWall(), new RoomWall(room1ID), new WindowWall(), new Wall(), room3ID);
		
		rooms.add(room1);
		rooms.add(room2);
		rooms.add(room3);
		
//		Room room = new Room();
//		rooms.add(room);
	}
	
	public House(String houseJson) {
		//build house object from text file
	}

	public ArrayList<Room> getRooms() {
		return new ArrayList<Room>(rooms);
	}
	
	public Room getRoom(Room room) {
		for (Room r : rooms) {
			if(r.getId() == room.getId() && r.getName().equals(room.getName())) {
				return room;
			}
		}
		return null;
	}
	
	public House addRoom(Room room) {
		rooms.add(room);
		return this;
	}
	
	public House removeRoom(Room room) {
		rooms.remove(room);
		return this;
	}
}
