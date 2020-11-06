package Helpers.DrawingHelpers;

import java.awt.*;

public class OutsideDoorDrawingHelper {

  public static void DrawLockedDoor(String direction, int xCoord, int yCoord, Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    Stroke ogStroke = g2d.getStroke();
    float[] dash1 = {2f, 0f, 2f};
    g2d.setStroke(
        new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash1, 2f));

    if (direction.equals("top")) {
      g2d.drawLine(xCoord + 35, yCoord, xCoord + 65, yCoord);
      g2d.setStroke(ogStroke);
      g2d.drawLine(xCoord, yCoord, xCoord + 35, yCoord);
      g2d.drawLine(xCoord + 65, yCoord, xCoord + 100, yCoord);
    } else if (direction.equals("bottom")) {
      g2d.drawLine(xCoord + 35, yCoord + 100, xCoord + 65, yCoord + 100);
      g2d.setStroke(ogStroke);
      g2d.drawLine(xCoord, yCoord + 100, xCoord + 35, yCoord + 100);
      g2d.drawLine(xCoord + 65, yCoord + 100, xCoord + 100, yCoord + 100);
    } else if (direction.equals("left")) {
      g2d.drawLine(xCoord, yCoord + 35, xCoord, yCoord + 65);
      g2d.setStroke(ogStroke);
      g2d.drawLine(xCoord, yCoord, xCoord, yCoord + 35);
      g2d.drawLine(xCoord, yCoord + 65, xCoord, yCoord + 100);
    } else if (direction.equals("right")) {
      g2d.drawLine(xCoord + 100, yCoord + 35, xCoord + 100, yCoord + 65);
      g2d.setStroke(ogStroke);
      g2d.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 35);
      g2d.drawLine(xCoord + 100, yCoord + 65, xCoord + 100, yCoord + 100);
    }
  }

  public static void DrawDoorLight(String direction, int xCoord, int yCoord, Graphics g) {
    if (direction.equals("top")) {
      g.drawLine(xCoord, yCoord, xCoord + 100, yCoord);
      g.setColor(Color.yellow);
      g.fillRect(xCoord + 35, yCoord, 30, 10);
    } else if (direction.equals("bottom")) {
      g.drawLine(xCoord, yCoord + 100, xCoord + 100, yCoord + 100);
      g.setColor(Color.yellow);
      g.fillRect(xCoord + 35, yCoord + 100, 30, 10);
    } else if (direction.equals("left")) {
      g.drawLine(xCoord, yCoord, xCoord, yCoord + 100);
      g.setColor(Color.yellow);
      g.fillRect(xCoord, yCoord + 35, 10, 30);
    } else if (direction.equals("right")) {
      g.drawLine(xCoord + 100, yCoord, xCoord + 100, yCoord + 100);
      g.setColor(Color.yellow);
      g.fillRect(xCoord + 100, yCoord + 35, 10, 30);
    }
    g.setColor(Color.black);
  }
}
