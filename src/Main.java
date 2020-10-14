import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main{
    public static void main(String[] args) {
    	java.awt.EventQueue.invokeLater(() -> {
            JScrollPane scrollPane = new JScrollPane(new HouseDrawer(new House()));
            scrollPane.getViewport().setPreferredSize(new Dimension(800, 800));
            JFrame frame = new JFrame("SOEN 343");
            frame.getContentPane().add(scrollPane);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
    	});
    }
}