package Views;

import Enums.WallType;
import Helpers.DrawingHelpers.OutsideDoorDrawingHelper;
import Helpers.DrawingHelpers.WindowDrawingHelper;
import Models.EnvironmentModel;
import Models.House;
import Models.Room;
import Models.UserProfileModel;
import Models.Walls.OutsideWall;
import Models.Walls.RoomWall;
import Models.Walls.Wall;
import Models.Walls.WindowWall;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/** The type House graphic. */
public class HouseGraphic extends JPanel {
  private static final int WIDTH = 1000;
  private static final int HEIGHT = 1000;
  EnvironmentModel environment;
  /** The rooms already visited by the recursive algorithm drawing the house. */
  ArrayList<Integer> visitedRooms = new ArrayList<Integer>();
  /** The House being drawn. */
  House house;

  /**
   * Instantiates a new House graphic.
   *
   * @param home the home
   */
  public HouseGraphic(House home) {
    this.house = home;
  }

  /** Init.
   * @param environment the environment
   * */
  public void init(EnvironmentModel environment) {
    this.environment = environment;
    JScrollPane scrollPane = new JScrollPane(this);
    scrollPane.getViewport().setPreferredSize(new Dimension(700, 700));
    JFrame frame = new JFrame("House Layout");
    frame.getContentPane().add(scrollPane);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    DrawHouse(g);
    visitedRooms.clear();
  }

  public Dimension getPreferredSize() {
    return new Dimension(WIDTH, HEIGHT);
  }

  /**
   * Draw house.
   *
   * @param g the g
   */
  private void DrawHouse(Graphics g) {
    if (house.getRooms().size() > 0) {
      DrawRoom(house.getRooms().get(0), 300, 300, g);
    }
  }

  private void DrawRoom(Room room, int xCoord, int yCoord, Graphics g) {
    if (visitedRooms.contains(room.getId())) return;

    visitedRooms.add(room.getId());

    if (room.getLightsOn() == true) {
      DrawRoomLight(xCoord, yCoord, g);
    }

    g.drawString(room.getName(), xCoord + 2, yCoord + 15);
    g.drawString(((Integer) room.getTemperature()).toString() + "\u00B0", xCoord + 2, yCoord + 30);
    DrawPeopleInRoom(room, xCoord, yCoord, g);

    DrawWallType(room.getLeftWall(), "left", xCoord, yCoord, g);
    DrawWallType(room.getBottomWall(), "bottom", xCoord, yCoord, g);
    DrawWallType(room.getRightWall(), "right", xCoord, yCoord, g);
    DrawWallType(room.getTopWall(), "top", xCoord, yCoord, g);
  }

  private void DrawWallType(Wall wall, String direction, int xCoord, int yCoord, Graphics g) {
    // The wall is just a normal wall
    if (wall.getType() == WallType.WALL) {
      DrawWall(direction, xCoord, yCoord, g);
    }
    // The wall is a wall that contains a window
    else if (wall.getType() == WallType.WINDOWS) {
      // Window is obstructed
      if (((WindowWall) wall).isWindowObstructed() == true) {
        // Window is opened
        if (((WindowWall) wall).isWindowOpen() == true) {
          WindowDrawingHelper.DrawOpenObstructedWindow(direction, xCoord, yCoord, g);
        }
        // Window is closed
        else if (((WindowWall) wall).isWindowOpen() == false) {
          WindowDrawingHelper.DrawClosedObstructedWindow(direction, xCoord, yCoord, g);
        }
      }
      // Window is not obstructed
      else if (((WindowWall) wall).isWindowObstructed() == false) {
        // Window is opened
        if (((WindowWall) wall).isWindowOpen() == true) {
          WindowDrawingHelper.DrawOpenWindow(direction, xCoord, yCoord, g);
        }
        // Window is closed
        else if (((WindowWall) wall).isWindowOpen() == false) {
          WindowDrawingHelper.DrawClosedWindow(direction, xCoord, yCoord, g);
        }
      }
    }
    // The wall is a door that leads to the outside
    else if (wall.getType() == WallType.OUTSIDE) {
      // Door has the light on (No need to draw anything if light is off)
      if (((OutsideWall) wall).getLightsOn() == true) {
        OutsideDoorDrawingHelper.DrawDoorLight(direction, xCoord, yCoord, g);
      }

      // Door is locked
      if (((OutsideWall) wall).getDoorLocked() == true) {
        OutsideDoorDrawingHelper.DrawLockedDoor(direction, xCoord, yCoord, g);
      }
      // Door is unlocked
      else if (((OutsideWall) wall).getDoorLocked() == false) {
        DrawDoor(direction, xCoord, yCoord, g);
      }
    }
    // The wall is a door that leads to another room
    else if (wall.getType() == WallType.DOOR) {
      int attachedRoom = ((RoomWall) wall).getConnectedRoom();

      if (visitedRooms.contains(attachedRoom)) {
        DrawDoor(direction, xCoord, yCoord, g);
      } else {
        for (Room r : house.getRooms()) {
          if (r.getId() == attachedRoom) {
            if (direction.equals("left")) {
              DrawRoom(r, xCoord - 100, yCoord, g);
            }
            if (direction.equals("right")) {
              DrawRoom(r, xCoord + 100, yCoord, g);
            }
            if (direction.equals("top")) {
              DrawRoom(r, xCoord, yCoord - 100, g);
            }
            if (direction.equals("bottom")) {
              DrawRoom(r, xCoord, yCoord + 100, g);
            }
          }
        }
      }
    }
  }

  private void DrawWall(String direction, int xCoord, int yCoord, Graphics g) {
    if (direction.equals("top")) {
      g.drawLine(xCoord, yCoord, xCoord + 100, yCoord);
    } else if (direction.equals("bottom")) {
      g.drawLine(xCoord, yCoord + 100, xCoord + 100, yCoord + 100);
    } else if (direction.equals("left")) {
      g.drawLine(xCoord, yCoord, xCoord, yCoord + 100);
    } else if (direction.equals("right")) {
      g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 100);
    }
  }

  private void DrawDoor(String direction, int xCoord, int yCoord, Graphics g) {
    if (direction.equals("top")) {
      g.drawLine(xCoord, yCoord, xCoord + 35, yCoord);
      g.drawLine(xCoord + 65, yCoord, xCoord + 100, yCoord);
    } else if (direction.equals("bottom")) {
      g.drawLine(xCoord, yCoord + 100, xCoord + 35, yCoord + 100);
      g.drawLine(xCoord + 65, yCoord + 100, xCoord + 100, yCoord + 100);
    } else if (direction.equals("left")) {
      g.drawLine(xCoord, yCoord, xCoord, yCoord + 35);
      g.drawLine(xCoord, yCoord + 65, xCoord, yCoord + 100);
    } else if (direction.equals("right")) {
      g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 35);
      g.drawLine(xCoord + 100, yCoord + 65, xCoord + 100, yCoord + 100);
    }
  }

  private void DrawRoomLight(int xCoord, int yCoord, Graphics g) {
    g.setColor(Color.yellow);
    g.fillRect(xCoord, yCoord, 100, 100);
    g.setColor(Color.black);
  }

  private void DrawPeopleInRoom(Room room, int xCoord, int yCoord, Graphics g) {
    int newX = xCoord + 2;
    int newY = yCoord + 45;
    for (UserProfileModel user : room.getAllUsersInRoom(environment)) {
      g.drawString("\u263A " + user.getName(), newX, newY);
      newY += 15;
    }
  }
}
