package Views;

import Models.Context;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/** The type Console. */
public class CustomConsole {
  /** The Frame. */
  static final JFrame frame = new JFrame();
  /** The PrintStream. */
  static PrintStream ps;
  /** The FileWriter */
  static BufferedWriter log;

  /**
   * Prints on the console.
   *
   * @param s the string to append.
   */
  public static void print(String s) {
    ps.println(Context.getDateObject() + " : " + s);
    try {
      log.write(Context.getDateObject() + " : " + s);
      log.newLine();
      log.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
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

    try {
      log = new BufferedWriter(new FileWriter(new File("log.txt"), false));
    } catch (IOException e) { }

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();

    frame.setSize((int) rect.getWidth()/2, (int) rect.getHeight()/2 - 30);
    frame.setLocation((int) rect.getWidth()/2, (int) rect.getHeight()/2);
    frame.add(jsp);
    frame.setVisible(true);
  }
}
