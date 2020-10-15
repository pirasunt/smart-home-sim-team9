package Main;

import java.util.*;

public class House {
	ArrayList<Room> rooms = new ArrayList<Room>();

	//TO REMOVE
//	public Main.House() {
//		int room1ID = 1;
//		int room2ID = 2;
//		int room3ID = 3;
//		int room4ID = 4;
//		int room5ID = 5;
//		int room6ID = 6;
//		int room7ID = 7;
//		int room8ID = 8;
//		int room9ID = 9;
//
//		Main.Room room1 = new Main.Room("Main.Room 1", new Main.RoomWall(room4ID), new Main.RoomWall(room2ID), new Main.OutsideWall(), new Main.WindowWall(), room1ID);
//		Main.Room room2 = new Main.Room("Main.Room 2", new Main.RoomWall(room3ID), new Main.WindowWall(), new Main.RoomWall(room7ID), new Main.RoomWall(room1ID), room2ID);
//		Main.Room room3 = new Main.Room("Main.Room 3", new Main.WindowWall(), new Main.WindowWall(), new Main.RoomWall(room2ID), new Main.Wall(), room3ID);
//		Main.Room room4 = new Main.Room("Main.Room 4", new Main.WindowWall(), new Main.Wall(), new Main.RoomWall(room1ID), new Main.RoomWall(room5ID), room4ID);
//		Main.Room room5 = new Main.Room("Main.Room 5", new Main.RoomWall(room6ID), new Main.RoomWall(room4ID), new Main.Wall(), new Main.WindowWall(), room5ID);
//		Main.Room room6 = new Main.Room("Main.Room 6", new Main.WindowWall(), new Main.WindowWall(), new Main.RoomWall(room5ID), new Main.OutsideWall(), room6ID);
//		Main.Room room7 = new Main.Room("Main.Room 7", new Main.RoomWall(room2ID), new Main.RoomWall(room9ID), new Main.RoomWall(room8ID), new Main.WindowWall(), room7ID);
//		Main.Room room8 = new Main.Room("Main.Room 8", new Main.RoomWall(room7ID), new Main.WindowWall(), new Main.WindowWall(), new Main.Wall(), room8ID);
//		Main.Room room9 = new Main.Room("Main.Room 9", new Main.Wall(), new Main.WindowWall(), new Main.Wall(), new Main.RoomWall(room7ID), room9ID);
//
//		rooms.add(room1);
//		rooms.add(room2);
//		rooms.add(room3);
//		rooms.add(room4);
//		rooms.add(room5);
//		rooms.add(room6);
//		rooms.add(room7);
//		rooms.add(room8);
//		rooms.add(room9);
//	}

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
