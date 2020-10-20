package Models.Walls;

import Enums.WallType;

/** The type Window wall. */
public class WindowWall extends Wall {
  /** This attribute is a boolean value to know if the windows on this wall are open. */
  boolean windowOpen = false;
  /** This attribute is a boolean value to know if the windows are obstructed on this wall. */
  boolean windowObstructed = false;

  /** Instantiates a new Window wall. */
  public WindowWall() {
    super(WallType.WINDOWS);
  }

  /**
   * Use this value to verify if a window is obstructed or not.
   *
   * @return true if the window is obstructed, false if it is not.
   */
  public boolean isWindowObstructed() {
    return windowObstructed;
  }

  /**
   * Change the boolean value of the "windowObstructed" attribute
   *
   * @param windowObstructed is true to obstruct the windows, false to remove an obstruction.
   */
  public void setWindowObstructed(boolean windowObstructed) {
    this.windowObstructed = windowObstructed;
  }

  /**
   * Use this value to verify if the windows are opened or not.
   *
   * @return true if the windows are opened, false if they are not.
   */
  public boolean isWindowOpen() {
    return windowOpen;
  }

  /**
   * Change the boolean value of the "windowOpen" attribute
   *
   * @param windowOpen is true to open the windows, false to close them.
   */
  public void setWindowOpen(boolean windowOpen) {
    this.windowOpen = windowOpen;
  }
}
