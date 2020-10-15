package Main;

import com.thoughtworks.xstream.XStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

public class House {
    ArrayList<Room> rooms = new ArrayList<Room>();

//    TO REMOVE
	public House() {
		int room1ID = 1;
		int room2ID = 2;
		int room3ID = 3;
		int room4ID = 4;
		int room5ID = 5;
		int room6ID = 6;
		int room7ID = 7;
		int room8ID = 8;
		int room9ID = 9;

		Room room1 = new Room("Room 1", new RoomWall(room4ID), new RoomWall(room2ID), new OutsideWall(), new WindowWall(), room1ID);
		Room room2 = new Room("Room 2", new RoomWall(room3ID), new WindowWall(), new RoomWall(room7ID), new RoomWall(room1ID), room2ID);
		Room room3 = new Room("Room 3", new WindowWall(), new WindowWall(), new RoomWall(room2ID), new Wall(), room3ID);
		Room room4 = new Room("Room 4", new WindowWall(), new Wall(), new RoomWall(room1ID), new RoomWall(room5ID), room4ID);
		Room room5 = new Room("Room 5", new RoomWall(room6ID), new RoomWall(room4ID), new Wall(), new WindowWall(), room5ID);
		Room room6 = new Room("Room 6", new WindowWall(), new WindowWall(), new RoomWall(room5ID), new OutsideWall(), room6ID);
		Room room7 = new Room("Room 7", new RoomWall(room2ID), new RoomWall(room9ID), new RoomWall(room8ID), new WindowWall(), room7ID);
		Room room8 = new Room("Room 8", new RoomWall(room7ID), new WindowWall(), new WindowWall(), new Wall(), room8ID);
		Room room9 = new Room("Room 9", new Wall(), new WindowWall(), new Wall(), new RoomWall(room7ID), room9ID);

		rooms.add(room1);
		rooms.add(room2);
		rooms.add(room3);
		rooms.add(room4);
		rooms.add(room5);
		rooms.add(room6);
		rooms.add(room7);
		rooms.add(room8);
		rooms.add(room9);
	}

    public ArrayList<Room> getRooms() {
        return new ArrayList<Room>(rooms);
    }

    public Room getRoom(Room room) {
        for (Room r : rooms) {
            if (r.getId() == room.getId() && r.getName().equals(room.getName())) {
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

    public void getXML() {
        XStream stream = new XStream();
        stream.alias("OutsideWall",OutsideWall.class);
		stream.alias("Room",Room.class);
		stream.alias("RoomWall",RoomWall.class);
		stream.alias("Wall",Wall.class);
		stream.alias("WindowWall",WindowWall.class);
		try {
            stream.toXML(this, new FileOutputStream("House0.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
