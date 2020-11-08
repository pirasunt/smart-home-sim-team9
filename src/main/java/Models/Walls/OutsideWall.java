package Models.Walls;

import Enums.WallType;
import Models.EnvironmentModel;
import Views.CustomConsole;

/** The type Outside wall. */
public class OutsideWall extends Wall {
  private boolean doorLocked = true;
  private int doorId;
  private boolean lightsOn = false;

  /** Instantiates a new Outside wall. */
  public OutsideWall() {
    super(WallType.OUTSIDE);
  }

  /**
   * Instantiates a new Outside wall with an id.
   *
   * @param id the id
   */
  public OutsideWall(int id) {
    super(WallType.OUTSIDE);

    this.doorId = id;
  }

  /**
   * Returns the value of the attribute doorLocked which is true if the door is locked, false
   * otherwise.
   *
   * @return if the door is locked or not.
   */
  public boolean getDoorLocked() {
    return this.doorLocked;
  }

  /**
   * Set the value of the doorLocked attribute describing if this outside door is locked or not.
   *
   * @param doorLocked the value you wish the attribute to have after this method call.
   */
  public void setDoorLocked(boolean doorLocked) {
    this.doorLocked = doorLocked;
    EnvironmentModel.getHouseGraphic().repaint();
  }

  /**
   * Returns the value of the attribute lightsOn which is true if the light is on, false otherwise.
   *
   * @return if the light is on or not.
   */
  public boolean getLightsOn() {
    return this.lightsOn;
  }

  /**
   * Method to set if the lights at this door are on or off.
   *
   * @param lightsOn the value you wish the attribute lightsOn to take
   */
  public void setLightsOn(boolean lightsOn) {
    this.lightsOn = lightsOn;
    EnvironmentModel.getHouseGraphic().repaint();
  }

  /** Method to be used when a door is being locked automatically be the system. */
  public void lockDoor() {
    if (!doorLocked) {
      this.doorLocked = true;
      EnvironmentModel.getHouseGraphic().repaint();
      CustomConsole.print("Door with outside access " + doorId + " has been locked.");
    }
  }

  /** Method to be used when a door is being locked automatically be the system. */
  public void unlockDoor() {
    if (doorLocked) {
      this.doorLocked = false;
      EnvironmentModel.getHouseGraphic().repaint();
      CustomConsole.print("Door with outside access " + doorId + " has been unlocked.");
    }
  }

  /** Method to be used when a door is being locked automatically be the system. */
  public void turnLightsOn() {
    if (!lightsOn) {
      this.lightsOn = true;
      EnvironmentModel.getHouseGraphic().repaint();
      CustomConsole.print("Door with outside access " + doorId + " lights have been turned on.");
    }
  }

  /** Method to be used when a door is being locked automatically be the system. */
  public void turnLightsOff() {
    if (lightsOn) {
      this.lightsOn = false;
      EnvironmentModel.getHouseGraphic().repaint();
      CustomConsole.print("Door with outside access " + doorId + " lights have been turned off.");
    }
  }
}
