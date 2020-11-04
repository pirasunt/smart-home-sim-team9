package DrawingHelpers;

import java.awt.*;

public class WindowDrawingHelper {

    public static void DrawOpenObstructedWindow(String direction, int xCoord, int yCoord, Graphics g) {
        if (direction.equals("top")) {
            g.drawLine(xCoord, yCoord, xCoord + 100, yCoord);
            g.setColor(Color.red);
            g.drawRect(xCoord + 35, yCoord - 2, 30, 4);
        } else if (direction.equals("bottom")) {
            g.drawLine(xCoord, yCoord + 100, xCoord + 100, yCoord + 100);
            g.setColor(Color.red);
            g.drawRect(xCoord + 35, yCoord + 98, 30, 4);
        } else if (direction.equals("left")) {
            g.drawLine(xCoord, yCoord, xCoord, yCoord + 100);
            g.setColor(Color.red);
            g.drawRect(xCoord - 2, yCoord + 35, 4, 30);
        } else if (direction.equals("right")) {
            g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 100);
            g.setColor(Color.red);
            g.drawRect(xCoord + 98, yCoord + 35, 4, 30);
        }
        g.setColor(Color.black);
    }

    public static void DrawOpenWindow(String direction, int xCoord, int yCoord, Graphics g) {
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

    public static void DrawClosedObstructedWindow(String direction, int xCoord, int yCoord, Graphics g) {
        if (direction.equals("top")) {
            g.drawLine(xCoord, yCoord, xCoord + 100, yCoord);
            g.setColor(Color.red);
            g.fillRect(xCoord + 35, yCoord - 2, 30, 4);
        } else if (direction.equals("bottom")) {
            g.drawLine(xCoord, yCoord + 100, xCoord + 100, yCoord + 100);
            g.setColor(Color.red);
            g.fillRect(xCoord + 35, yCoord + 98, 30, 4);
        } else if (direction.equals("left")) {
            g.drawLine(xCoord, yCoord, xCoord, yCoord + 100);
            g.setColor(Color.red);
            g.fillRect(xCoord - 2, yCoord + 35, 4, 30);
        } else if (direction.equals("right")) {
            g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 100);
            g.setColor(Color.red);
            g.fillRect(xCoord + 98, yCoord + 35, 4, 30);
        }
        g.setColor(Color.BLACK);
    }

    public static void DrawClosedWindow(String direction, int xCoord, int yCoord, Graphics g) {
        if (direction.equals("top")) {
            g.drawLine(xCoord, yCoord, xCoord + 100, yCoord);
            g.fillRect(xCoord + 35, yCoord - 2, 30, 4);
        } else if (direction.equals("bottom")) {
            g.drawLine(xCoord, yCoord + 100, xCoord + 100, yCoord + 100);
            g.fillRect(xCoord + 35, yCoord + 98, 30, 4);
        } else if (direction.equals("left")) {
            g.drawLine(xCoord, yCoord, xCoord, yCoord + 100);
            g.fillRect(xCoord - 2, yCoord + 35, 4, 30);
        } else if (direction.equals("right")) {
            g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 100);
            g.fillRect(xCoord + 98, yCoord + 35, 4, 30);
        }
    }
}
