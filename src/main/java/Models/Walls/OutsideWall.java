package Models.Walls;

import Enums.WallType;
import Views.Console;

/** The type Outside wall. */
public class OutsideWall extends Wall {
  private boolean doorLocked = false;
  private int doorId;

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
   * Set the value of the doorLocked attribute describing if this outside door is locked or not
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
}
