package Views;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import javax.swing.JFrame;
import javax.swing.JTextArea;


class Console {
    final JFrame frame = new JFrame();
    PrintStream ps;

    public Console() {
        JTextArea textArea = new JTextArea(24, 80);
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

    public void print(String s) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ps.println(timestamp + " : " + s);
    }

    public void init() {
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}