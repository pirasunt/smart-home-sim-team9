package Views;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import javax.swing.JFrame;
import javax.swing.JTextArea;


/**
 * The type Console.
 */
class Console {
    /**
     * The Frame.
     */
    static final JFrame frame = new JFrame();
    /**
     * The PrintStream.
     */
    static PrintStream ps;

    /**
     * Instantiates a new Console.
     */
    public Console() {
        JTextArea textArea = new JTextArea(10, 80);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        ps = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                textArea.append(String.valueOf((char) b));
            }
        });
        frame.add(textArea);
    }

    /**
     * Prints on the console.
     *
     * @param s the string to append.
     */
    static public void print(String s) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ps.println(timestamp + " : " + s);
    }

    /**
     * Init the console.
     */
    static public void init() {
        frame.pack();
        frame.setVisible(true);
    }
}