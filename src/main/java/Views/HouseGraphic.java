package Views;

import Enums.WallType;
import Models.House;
import Models.Room;
import Models.Walls.*;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The type House graphic.
 */
public class HouseGraphic extends JPanel {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    /**
     * The Visited rooms.
     */
    ArrayList<Integer> visitedRooms = new ArrayList<Integer>();
    /**
     * The House.
     */
    House house;

    /**
     * Instantiates a new House graphic.
     *
     * @param home the home
     */
    public HouseGraphic(House home) {
        this.house = home;
    }

    /**
     * Init.
     */
    public void init() {
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.getViewport().setPreferredSize(new Dimension(700, 700));
        JFrame frame = new JFrame("SOEN 343");
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
    public void DrawHouse(Graphics g) {
        if (house.getRooms().size() > 0) {
            DrawRoom(house.getRooms().get(0), 300, 300, g);
        }
    }

    private void DrawRoom(Room room, int xCoord, int yCoord, Graphics g) {
        if (visitedRooms.contains(room.getId())) return;

        visitedRooms.add(room.getId());

        g.drawString(room.getName(), xCoord + 2, yCoord + 15);
        g.drawString(((Integer) room.getTemperature()).toString() + "\u00B0", xCoord + 2, yCoord + 30);

        DrawWallType(room.getLeft(), "left", xCoord, yCoord, g);
        DrawWallType(room.getBottom(), "bottom", xCoord, yCoord, g);
        DrawWallType(room.getRight(), "right", xCoord, yCoord, g);
        DrawWallType(room.getTop(), "top", xCoord, yCoord, g);
    }

    private void DrawWallType(Wall wall, String direction, int xCoord, int yCoord, Graphics g) {
        if (wall.getType() == WallType.WALL) {
            DrawWall(direction, xCoord, yCoord, g);
        } else if (wall.getType() == WallType.WINDOWS) {
            DrawWindow(direction, xCoord, yCoord, g);
        } else if (wall.getType() == WallType.OUTSIDE) {
            DrawDoor(direction, xCoord, yCoord, g);
        } else if (wall.getType() == WallType.DOOR) {
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

    private void DrawWindow(String direction, int xCoord, int yCoord, Graphics g) {
        if (direction.equals("top")) {
            g.drawLine(xCoord, yCoord, xCoord + 100, yCoord);
            g.drawRect(xCoord + 35, yCoord - 2, 30, 4);
        } else if (direction.equals("bottom")) {
            g.drawLine(xCoord, yCoord + 100, xCoord + 100, yCoord + 100);
            g.drawRect(xCoord + 35, yCoord + 98, 30, 4);
        } else if (direction.equals("left")) {
            g.drawLine(xCoord, yCoord, xCoord, yCoord + 100);
            g.drawRect(xCoord - 2, yCoord + 35, 4, 30);
        } else if (direction.equals("right")) {
            g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 100);
            g.drawRect(xCoord + 98, yCoord + 35, 4, 30);
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
}
