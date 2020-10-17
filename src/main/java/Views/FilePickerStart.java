package Views;

import Context.Environment;
import Context.UserProfile;
import Custom.CustomXStream;
import Models.House;
import Enums.profileType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.UUID;

import javax.swing.*;

/**
 * The type File picker start.
 */
public class FilePickerStart extends JFrame {
    private final JTextField filename = new JTextField();
    private final JTextField dir = new JTextField();
    private final JButton choseFile = new JButton("Select");
    private final JButton goButton = new JButton("Go");
    private File pathTo;


    /**
     * Instantiates a FilePicker window.
     */
    public FilePickerStart() {
        JPanel p = new JPanel();
        choseFile.addActionListener(new OpenL());

        // Is required as we need a reference to destroy the Window
        FilePickerStart o = this;
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //Sample users. The 3rd parameter is the UUID of a Room
                UserProfile p1 = new UserProfile(profileType.ADULT, "James",1);
                UserProfile p2 = new UserProfile(profileType.STRANGER, "Janice", 1);
                UserProfile p3 = new UserProfile(profileType.CHILD, "Morty", 3);
                UserProfile p4 = new UserProfile(profileType.GUEST, "Astley", 3);
                UserProfile p5 = new UserProfile(profileType.GUEST, "Penny", 4);
                UserProfile p6 = new UserProfile(profileType.STRANGER, "Cool Guy", 5);
                UserProfile p7 = new UserProfile(profileType.CHILD, "Rick", 6);

                //Init singleton Environment object. Pass this instance to objects that need it.
                Environment env = Environment.createSimulation(p1, p2, p3, p4, p5, p6,p7);

                CustomXStream stream = new CustomXStream();
                House house = (House) stream.fromXML(pathTo);
                HouseGraphic hg = new HouseGraphic(house);
                hg.init();

                //Init console, can now call static method Console.print()
                Console c = new Console();
                Console.init();

                Console.print("Welcome to the simulator!");

                OptionFrame pp2 = new OptionFrame(env);
                pp2.setSize(250, 250);
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

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        run(new FilePickerStart(), 250, 110);
    }

    /**
     * Run.
     *
     * @param frame  the frame
     * @param width  the width
     * @param height the height
     */
    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    /**
     * The open button listener.
     */
    class OpenL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser();
            // Demonstrate "Open" dialog:
            int rVal = c.showOpenDialog(FilePickerStart.this);
            if (rVal == JFileChooser.APPROVE_OPTION) {
                pathTo = c.getSelectedFile();
                filename.setText(c.getSelectedFile().getName());
                dir.setText(c.getCurrentDirectory().toString());
            }
        }
    }


}
