package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Pain2 extends JFrame {
    MyPanel panel;

    // constructor
    Pain2() {

        panel = new MyPanel();
        this.add(panel);
        //TODO: Close window don't exit app lol
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        // add the label to the JFrame
    }
}

class MyPanel extends JPanel {

    public MyPanel() {
        JButton jrb1 = new JButton("Select User Profile");
        JButton jrb2 = new JButton("Select Location");
        JButton jrb3 = new JButton("Enter Simulation");
        Box box1 = Box.createVerticalBox();
        box1.add(jrb1);
        box1.add(jrb2);
        box1.add(jrb3);
        jrb3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

        JFrame frame = new JFrame("Dash");
        Dash d = new Dash();
        JPanel jp = d.p1;
        frame.setContentPane(jp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // Little example on how to programmatically add tabs.
        d.tabbedPane1.addTab("Example added tab",new JLabel("foo"));
            }
        });


        setLayout(new GridLayout(1, 1));
        add(box1);


    }

}
//public class TestFrame2 {
//    public static void main(String[] args) {
//        Pain1 frame = new Pain1("Hello"); // construct a MyFrame object
//        frame.setVisible(true);             // ask it to become visible
//    }
//}