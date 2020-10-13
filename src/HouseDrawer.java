import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.*;

public class HouseDrawer extends JPanel {
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	ArrayList<UUID> visitedRooms = new ArrayList<UUID>();
	House house;

	protected void paintComponent(Graphics g) {
		super.paintComponent(DrawHouse(g));
	}

	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	public HouseDrawer(House home) {
		this.house = home;
	}

	public Graphics DrawHouse(Graphics g) {
		if (house.getRooms().size() > 0) {
			return DrawRoom(house.getRooms().get(0), 300, 300, g);
		}
		return g;
	}

	private Graphics DrawRoom(Room room, int xCoord, int yCoord, Graphics g) {
		if (visitedRooms.contains(room.getId())) return g;
		
		visitedRooms.add(room.getId());
		
		g = DrawWall(room.getLeft(), "left", xCoord, yCoord, g);
		System.out.println("Currently drawing left wall: " + room.getName() + " at x: " + xCoord + ", y: " + yCoord);
		g = DrawWall(room.getRight(), "right", xCoord, yCoord, g);
		System.out.println("Currently drawing right wall: " + room.getName() + " at x: " + xCoord + ", y: " + yCoord);
		g = DrawWall(room.getBottom(), "bottom", xCoord, yCoord, g);
		System.out.println("Currently drawing bottom wall: " + room.getName() + " at x: " + xCoord + ", y: " + yCoord);
		g = DrawWall(room.getTop(), "top", xCoord, yCoord, g);
		System.out.println("Currently drawing top wall: " + room.getName() + " at x: " + xCoord + ", y: " + yCoord);

		return g;
	}

	public Graphics DrawWall(Wall wall, String direction, int xCoord, int yCoord, Graphics g) {
		if (wall.getType() == WallType.WALL) {
			if (direction.equals("top")) {
				g.drawLine(xCoord, yCoord, xCoord + 100, yCoord);
				return g;
			} else if (direction.equals("bottom")) {
				g.drawLine(xCoord, yCoord + 100, xCoord + 100, yCoord + 100);
				return g;
			} else if (direction.equals("left")) {
				g.drawLine(xCoord, yCoord, xCoord, yCoord + 100);
				return g;
			} else if (direction.equals("right")) {
				g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 100);
				return g;
			}
		}

		else if (wall.getType() == WallType.WINDOWS) {
			if (direction.equals("top")) {
				g.drawLine(xCoord, yCoord, xCoord + 100, yCoord);
				g.drawRect(xCoord + 35, yCoord - 2, 30, 4);
				return g;
			} else if (direction.equals("bottom")) {
				g.drawLine(xCoord, yCoord + 100, xCoord + 100, yCoord + 100);
				g.drawRect(xCoord + 35, yCoord + 98, 30, 4);
				return g;
			} else if (direction.equals("left")) {
				g.drawLine(xCoord, yCoord, xCoord, yCoord + 100);
				g.drawRect(xCoord - 2, yCoord + 35, 4, 30);
				return g;
			} else if (direction.equals("right")) {
				g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 100);
				g.drawRect(xCoord + 98, yCoord + 35, 4, 30);
				return g;
			}
		}

		else if (wall.getType() == WallType.OUTSIDE) {
			if (direction.equals("top")) {
				g.drawLine(xCoord, yCoord, xCoord + 35, yCoord);
				g.drawLine(xCoord + 65, yCoord, xCoord + 100, yCoord);
				return g;
			} else if (direction.equals("bottom")) {
				g.drawLine(xCoord, yCoord + 100, xCoord + 35, yCoord + 100);
				g.drawLine(xCoord + 65, yCoord + 100, xCoord + 100, yCoord + 100);
				return g;
			} else if (direction.equals("left")) {
				g.drawLine(xCoord, yCoord, xCoord, yCoord + 35);
				g.drawLine(xCoord, yCoord + 65, xCoord, yCoord + 100);
				return g;
			} else if (direction.equals("right")) {
				g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 35);
				g.drawLine(xCoord + 100, yCoord + 65, xCoord + 100, yCoord + 100);
				return g;
			}
		}

		else if (wall.getType() == WallType.DOOR) {
			UUID attachedRoom = ((RoomWall) wall).getConnectedRoom();

			if (visitedRooms.contains(attachedRoom)) {
				if (direction.equals("top")) {
					g.drawLine(xCoord, yCoord, xCoord + 35, yCoord);
					g.drawLine(xCoord + 65, yCoord, xCoord + 100, yCoord);
					return g;
				} else if (direction.equals("bottom")) {
					g.drawLine(xCoord, yCoord + 100, xCoord + 35, yCoord + 100);
					g.drawLine(xCoord + 65, yCoord + 100, xCoord + 100, yCoord + 100);
					return g;
				} else if (direction.equals("left")) {
					g.drawLine(xCoord, yCoord, xCoord, yCoord + 35);
					g.drawLine(xCoord, yCoord + 65, xCoord, yCoord + 100);
					return g;
				} else if (direction.equals("right")) {
					g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 35);
					g.drawLine(xCoord + 100, yCoord + 65, xCoord + 100, yCoord + 100);
					return g;
				}
			}

			else {
				for (Room r : house.getRooms()) {
					if (r.getId() == attachedRoom) {
						if (direction.equals("left")) {
							return DrawRoom(r, xCoord - 100, yCoord, g);
						}
						if (direction.equals("right")) {
							return DrawRoom(r, xCoord + 100, yCoord, g);
						}
						if (direction.equals("top")) {
							return DrawRoom(r, xCoord, yCoord - 100, g);
						}
						if (direction.equals("bottom")) {
							return DrawRoom(r, xCoord, yCoord + 100, g);
						}
					}
				}
			}
		}
		return g;
	}
}
