package Models;

import Models.Walls.*;
import Views.CustomConsole;

import java.util.ArrayList;

/** The type Room. */
public class Room {
  private final Wall leftWall, rightWall, topWall, bottomWall;
  private final String name;
  private final int id;
  private int temperature = 20;
  private boolean lightsOn = false;
  private boolean isInHeatingZone = false;

  /**
   * Instantiates a new Room.
   *
   * @param name the name of the room
   * @param left the left wall
   * @param bottom the bottom wall
   * @param right the right wall
   * @param top the top wall
   * @param id the id of the room
   */
  public Room(String name, Wall left, Wall bottom, Wall right, Wall top, int id) {
    this.name = name;
    this.leftWall = left;
    this.rightWall = right;
    this.topWall = top;
    this.bottomWall = bottom;
    this.id = id;
  }

  /**
   * Gets left wall.
   *
   * @return the left wall
   */
  public Wall getLeftWall() {
    return leftWall;
  }

  /**
   * Gets right wall.
   *
   * @return the right wall
   */
  public Wall getRightWall() {
    return rightWall;
  }

  /**
   * Gets top wall.
   *
   * @return the top wall
   */
  public Wall getTopWall() {
    return topWall;
  }

  /**
   * Gets bottom wall.
   *
   * @return the bottom wall
   */
  public Wall getBottomWall() {
    return bottomWall;
  }

  /**
   * Gets name wall.
   *
   * @return the name wall
   */
  public String getName() {
    return name;
  }

  /**
   * Gets id of the room.
   *
   * @return the id of the room
   */
  public int getId() {
    return id;
  }

  /**
   * Gets temperature in the room.
   *
   * @return the temperature of the room
   */
  public int getTemperature() {
    return temperature;
  }

  /**
   * Sets temperature of the room.
   *
   * @param temperature the temperature of the room
   */
  public void setTemperature(int temperature) {
    this.temperature = temperature;
  }

  /**
   * Get's a boolean value representing if the lights are on or not
   *
   * @return lightsOn the value of the lights being on or off
   */
  public boolean getLightsOn() {
    return lightsOn;
  }

  /**
   * Sets a new value for if the lights are on or off
   *
   * @param value true if trying to turn lights on, false if off
   */
  public void setLightsOn(boolean value) {
    this.lightsOn = value;
    Context.repaintHouseGraphic();
  }

  /** Method to be used when lights are turned off while the simulator is running */
  public void turnOffLights() {
    if (lightsOn) {
      lightsOn = false;
      CustomConsole.print("Lights in room: " + name + " have been turned off.");
    }
    Context.repaintHouseGraphic();
  }

  /** Method to be used when lights are turned on while the simulator is running */
  public void turnOnLights() {
    if (!lightsOn) {
      lightsOn = true;
      CustomConsole.print("Lights in room: " + name + " have been turned on.");
    }
    Context.repaintHouseGraphic();
  }

  /**
   * Returns which users are located in the room represented by this instance of Room.
   *
   * @param environment The EnvironmentModel singleton which stores all user profiles that exist in
   *     the simulation.
   * @return an array containing all the UserProfileModel objects with a room id equal to the one of
   *     this Room instance.
   */
  public ArrayList<UserProfileModel> getAllUsersInRoom(EnvironmentModel environment) {
    ArrayList<UserProfileModel> users = new ArrayList<UserProfileModel>();

    for (UserProfileModel user : environment.getAllUserProfiles()) {
      if (user.getRoomID() == this.id) {
        users.add(user);
      }
    }

    return users;
  }

  public Wall[] getAllWalls(){
    return new Wall[]{leftWall, rightWall, bottomWall, topWall};
  }

  public boolean isRoomInHeatingZone() {
    return this.isInHeatingZone;
  }

  public void setIsInHeatingZone(boolean value) {
    this.isInHeatingZone = value;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
