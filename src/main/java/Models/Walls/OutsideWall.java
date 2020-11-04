package Models.Walls;

import Enums.WallType;
import Views.Console;

/** The type Outside wall. */
public class OutsideWall extends Wall {
  private boolean doorLocked = false;
  private int doorId;
  private boolean lightsOn = false;

  /** Instantiates a new Outside wall. */
  public OutsideWall() {
    super(WallType.OUTSIDE);
  }

  /** Instantiates a new Outside wall with an id. */
  public OutsideWall(int id) {
    super(WallType.OUTSIDE);

    this.doorId = id;
  }

  /**
   * Returns the value of the attribute doorLocked which is true if the door is locked, false otherwise.
   * @return if the door is locked or not.
   */
  public boolean getDoorLocked(){
    return this.doorLocked;
  }

  /**
   * Returns the value of the attribute lightsOn which is true if the light is on, false otherwise.
   * @return if the light is on or not.
   */
  public boolean getLightsOn(){
    return this.lightsOn;
  }

  /**
   * Set the value of the doorLocked attribute describing if this outside door is locked or not.
   * @param doorLocked the value you wish the attribute to have after this method call.
   */
  public void setDoorLocked(boolean doorLocked) {
    this.doorLocked = doorLocked;
  }

  /**
   * Method to be used when a door is being locked automatically be the system.
   */
  public void lockDoor(){
    Console console = new Console();

    this.doorLocked = true;
    console.print("Door with outside access " + doorId + " has been locked.");
  }

  /**
   * Method to be used when a door is being locked automatically be the system.
   */
  public void unlockDoor(){
    Console console = new Console();

    this.doorLocked = false;
    console.print("Door with outside access " + doorId + " has been unlocked.");
  }

  /**
   * Method to set if the lights at this door are on or off.
   * @param lightsOn the value you wish the attribute lightsOn to take
   */
  public void setLightsOn(boolean lightsOn) {
    this.lightsOn = lightsOn;
  }

  /**
   * Method to be used when a door is being locked automatically be the system.
   */
  public void turnLightsOn(){
    Console console = new Console();

    this.lightsOn = true;
    console.print("Door with outside access " + doorId + " lights have been turned on.");
  }

  /**
   * Method to be used when a door is being locked automatically be the system.
   */
  public void turnsLightsOff(){
    Console console = new Console();

    this.lightsOn = false;
    console.print("Door with outside access " + doorId + " lights have been turned off.");
  }
}
