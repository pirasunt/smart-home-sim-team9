package Models;

import Custom.CustomXStream.CustomHouseXStream;
import Enums.WallType;
import Models.Walls.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

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
   * @param id the room to be returned
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
   * Lock and close doors and windows.
   *
   * @param shouldLock the shouldLock
   */
  public void lockAndClose(Boolean shouldLock) {
    for (Room r : rooms) {

      for (Wall w : r.getAllWalls()) {

        if (w.getType() == WallType.OUTSIDE) {
          ((OutsideWall) w).setDoorLocked(shouldLock);

        } else if (w.getType() == WallType.WINDOWS) {
          if (shouldLock) {
            ((WindowWall) w).closeWindow();
          } else {
            ((WindowWall) w).openWindow();
          }
        }
      }
    }
  }

  /**
   * Has obstruction boolean.
   *
   * @return the boolean
   */
  public Boolean hasOpenObstruction() {
    for (Room r : rooms) {
      for (Wall w : r.getAllWalls()) {
        if (w.getType() == WallType.WINDOWS) {
          if (((WindowWall) w).isWindowObstructed() && ((WindowWall) w).isWindowOpen()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /** Retrieves XML of the current house and saves to file. */
  public void getXML() {
    CustomHouseXStream stream = new CustomHouseXStream();
    try {
      stream.toXML(this, new FileOutputStream("House.xml"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
