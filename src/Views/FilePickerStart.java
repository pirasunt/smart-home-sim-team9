package Views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilePickerStart extends JFrame {
    private final JTextField filename = new JTextField();
    private final JTextField dir = new JTextField();

    private final JButton choseFile = new JButton("Select");
    private final JButton goButton = new JButton("Go");


    public FilePickerStart() {
        JPanel p = new JPanel();
        choseFile.addActionListener(new OpenL());

        FilePickerStart o = this;
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HouseLayoutView pp = new HouseLayoutView();
                Console c = new Console();
                c.init();
                c.print("Welcome to the sim!");
                Pain2 pp2 = new Pain2();
                pp2.setSize(250,250);
                pp.setVisible(true);
                pp2.setVisible(true);
                o.dispose();
            }
        });
        p.add(choseFile);
        p.add(goButton);
        Container cp = getContentPane();
        cp.add(p, BorderLayout.SOUTH);
        dir.setEditable(false);
        filename.setEditable(false);
        p = new JPanel();
        p.setLayout(new GridLayout(2, 1));
        p.add(filename);
        p.add(dir);
        cp.add(p, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        run(new FilePickerStart(), 250, 110);
    }

    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            // Demonstrate "Open" dialog:
            int rVal = c.showOpenDialog(FilePickerStart.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getCurrentDirectory().toString());
            }
        }
    }


}
