package Models;

import Custom.CustomXStream.CustomHouseXStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * The type House.
 */
public class House {
    /**
     * The Rooms in the house.
     */
    ArrayList<Room> rooms = new ArrayList<Room>();

//    public House() {
//        int room1ID = 1;
//        int room2ID = 2;
//        int room3ID = 3;
//        int room4ID = 4;
//        int room5ID = 5;
//        int room6ID = 6;
//        int room7ID = 7;
//        int room8ID = 8;
//        int room9ID = 9;
//
//        Room room1 = new Room("Entrance", new RoomWall(room4ID), new RoomWall(room2ID), new OutsideWall(1), new WindowWall(1), room1ID);
//        Room room2 = new Room("Living Room", new RoomWall(room3ID), new WindowWall(2), new RoomWall(room7ID), new RoomWall(room1ID), room2ID);
//        Room room3 = new Room("Bathroom", new WindowWall(3), new WindowWall(4), new RoomWall(room2ID), new Wall(), room3ID);
//        Room room4 = new Room("Dining Room", new WindowWall(5), new Wall(), new RoomWall(room1ID), new RoomWall(room5ID), room4ID);
//        Room room5 = new Room("Kitchen", new RoomWall(room6ID), new RoomWall(room4ID), new Wall(), new WindowWall(6), room5ID);
//        Room room6 = new Room("Garage", new WindowWall(7), new WindowWall(), new RoomWall(room5ID), new OutsideWall(2), room6ID);
//        Room room7 = new Room("Hall", new RoomWall(room2ID), new RoomWall(room9ID), new RoomWall(room8ID), new WindowWall(), room7ID);
//        Room room8 = new Room("Bedroom", new RoomWall(room7ID), new WindowWall(8), new WindowWall(9), new Wall(), room8ID);
//        Room room9 = new Room("Bedroom", new Wall(), new WindowWall(10), new Wall(), new RoomWall(room7ID), room9ID);
//
//        rooms.add(room1);
//        rooms.add(room2);
//        rooms.add(room3);
//        rooms.add(room4);
//        rooms.add(room5);
//        rooms.add(room6);
//        rooms.add(room7);
//        rooms.add(room8);
//        rooms.add(room9);
//    }

    /**
     * Gets rooms in the house.
     *
     * @return the rooms in the house
     */
    public ArrayList<Room> getRooms() {
        return this.rooms;
    }

    /**
     * Gets a single room in the house.
     *
     * @param room the room to be returned
     * @return the room requested
     */
    public Room getRoomById(int id) {
        for (Room r : rooms) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    /**
     * Add room to the house.
     *
     * @param room the room to add
     * @return the house with the updated rooms
     */
    public House addRoom(Room room) {
        rooms.add(room);
        return this;
    }

    /**
     * Remove room in the house.
     *
     * @param room the room to be removed
     * @return the house with the updated rooms
     */
    public House removeRoom(Room room) {
        rooms.remove(room);
        return this;
    }

    /**
     * Retrieves XML of the current house and saves to file.
     */
    public void getXML() {
        CustomHouseXStream stream = new CustomHouseXStream();
        try {
            stream.toXML(this, new FileOutputStream("House.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
