package Models;

import Custom.CustomXStream;
import Models.Walls.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/** The type House. */
public class House {
  /** The Rooms in the house. */
  ArrayList<Room> rooms = new ArrayList<Room>();

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
  public Room getRoom(Room room) {
    for (Room r : rooms) {
      if (r.getId() == room.getId() && r.getName().equals(room.getName())) {
        return room;
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

  public void getXML() {
    CustomXStream stream = new CustomXStream();
    try {
      stream.toXML(this, new FileOutputStream("House.xml"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
