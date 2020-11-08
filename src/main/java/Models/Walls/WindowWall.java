package Models.Walls;

import Enums.WallType;
import Models.EnvironmentModel;
import Views.CustomConsole;

/** The type Window wall. */
public class WindowWall extends Wall {
  /** This attribute is a boolean value to know if the windows on this wall are open. */
  private boolean windowOpen = false;
  /** This attribute is a boolean value to know if the windows are obstructed on this wall. */
  private boolean windowObstructed = false;
  /**
   * This attribute is an integer to represent the id of the window so it can be referenced outside
   * of the class.
   */
  private int windowId;

  /** Instantiates a new Window wall. */
  public WindowWall() {
    super(WallType.WINDOWS);
  }

  /**
   * Instantiates a new Window wall with a set id.
   *
   * @param id the id
   */
  public WindowWall(int id) {
    super(WallType.WINDOWS);

    this.windowId = id;
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
    EnvironmentModel.getHouseGraphic().repaint();
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
    EnvironmentModel.getHouseGraphic().repaint();
  }

  /** Method called when window has to be opened by the system automatically. */
  public void openWindow() {
    if (windowObstructed) {
      CustomConsole.print("Window " + windowId + " is obstructed and cannot be opened.");
    } else if (!windowOpen) {
      windowOpen = true;
      CustomConsole.print("Window " + windowId + " has been opened.");
    }
    EnvironmentModel.getHouseGraphic().repaint();
  }

  /** Method called when the window has to be closed by the system automatically */
  public void closeWindow() {
    if (windowObstructed) {
      CustomConsole.print("Window " + windowId + " is obstructed and cannot be closed.");
    } else if (windowOpen){
      windowOpen = false;
      CustomConsole.print("Window " + windowId + " has been closed.");
    }
    EnvironmentModel.getHouseGraphic().repaint();
  }

  /** Method called when the window has to be obstructed by the system automatically */
  public void obstructWindow() {
    if (!windowObstructed) {
      windowObstructed = true;
      CustomConsole.print("Window " + windowId + " has been obstructed.");
    }
    EnvironmentModel.getHouseGraphic().repaint();
  }

  /** Method called when the window has to be unobstructed by the system automatically */
  public void unobstructWindow() {
    if (windowObstructed) {
      windowObstructed = false;
      CustomConsole.print("Window " + windowId + " has been unobstructed.");
    }
    EnvironmentModel.getHouseGraphic().repaint();
  }
}
