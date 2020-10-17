package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Option frame.
 */
class OptionFrame extends JFrame {
    /**
     * The Panel.
     */
    OptionPanel panel;

    /**
     * Instantiates a new Option frame.
     */
    OptionFrame() {
        panel = new OptionPanel();
        this.add(panel);
        //TODO: Close window don't exit app lol
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        // add the label to the JFrame
    }
}

/**
 * The type Option panel.
 */
class OptionPanel extends JPanel {

    /**
     * Instantiates a new Option panel.
     */
    public OptionPanel() {
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
                d.tabbedPane1.addTab("Example added tab", new JLabel("foo"));
            }
        });
        setLayout(new GridLayout(1, 1));
        add(box1);
    }
}
