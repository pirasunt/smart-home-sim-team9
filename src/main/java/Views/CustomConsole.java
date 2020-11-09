package Views;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;

/** The type Console. */
public class CustomConsole {
  /** The Frame. */
  static final JFrame frame = new JFrame();
  /** The PrintStream. */
  static PrintStream ps;

  /**
   * Prints on the console.
   *
   * @param s the string to append.
   */
  public static void print(String s) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    ps.println(timestamp + " : " + s);
  }

  /** Init the console. */
  public static void init() {
    JTextArea textArea = new JTextArea(10, 80);
    textArea.setBackground(Color.BLACK);
    textArea.setForeground(Color.LIGHT_GRAY);
    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    ps =
        new PrintStream(
            new OutputStream() {
              @Override
              public void write(int b) throws IOException {
                textArea.append(String.valueOf((char) b));
              }
            });
    JScrollPane jsp =
        new JScrollPane(
            textArea,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();

    frame.setSize((int) rect.getWidth()/2, (int) rect.getHeight()/2 - 30);
    frame.setLocation((int) rect.getWidth()/2, (int) rect.getHeight()/2);
    frame.add(jsp);
    frame.setVisible(true);
  }
}
