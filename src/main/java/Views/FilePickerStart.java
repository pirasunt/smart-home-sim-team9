package Views;

import Controllers.EnvironmentController;
import Custom.CustomXStream.CustomHouseXStream;
import Custom.CustomXStream.CustomUserXStream;
import Helpers.HouseValidationHelper;
import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.MonthDay;
import java.util.Calendar;
import java.util.Date;

/** The type File picker start. */
public class FilePickerStart extends JFrame {
  private final JTextField filename = new JTextField();
  private final JTextField dir = new JTextField();
  private final JButton choseFile = new JButton("Select");
  private final JButton goButton = new JButton("Go");
  private File pathTo;

  /** Instantiates a FilePicker window. */
  public FilePickerStart() {
    JPanel p = new JPanel();
    choseFile.addActionListener(new OpenL());

    // Is required as we need a reference to destroy the Window
    FilePickerStart o = this;
    goButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {

            CustomUserXStream uStream = new CustomUserXStream();
            UserProfileModel[] users =
                ((UserProfileModel[]) uStream.fromXML(new File("UserProfiles.xml")));

            CustomHouseXStream hStream = new CustomHouseXStream();
            House house = (House) hStream.fromXML(pathTo);
            boolean validHouse = HouseValidationHelper.ValidateHouse(house);
            HouseGraphic hg = new HouseGraphic(house);

            // Init singleton Environment object with HOUSE and USERS. Pass this instance to objects
            // that need it.
            EnvironmentModel theModel = EnvironmentModel.createSimulation(house, hg, users);

            // Initialize the house graphic with the environment
            hg.init(theModel);

            // Init console, can now call static method Console.print()
            CustomConsole.init();

            CustomConsole.print("Welcome to the simulator!");
            if (!validHouse) {
              CustomConsole.print(
                  "The XML inputted was invalid. This means the room generated in the House Layout will not make sense.");
              CustomConsole.print(
                  "Make sure each room has a unique id and that connected rooms reference each other by id.");
              CustomConsole.print(
                  "That is: if room 1 is to the right of room 2, then room 2 must be to the left of room 1.");
              CustomConsole.print("Fix the XML file and restart the program with a valid input.");
            }

            EnvironmentView theView = new EnvironmentView();
            EnvironmentController theController = new EnvironmentController(theView, theModel);

            o.dispose();

            //Set the temperature of all the rooms to the outside temperature by default
            for (Room room : house.getRooms()) {
              room.setTemperature(EnvironmentModel.getOutsideTemp());
            }

//            Room[] testRooms = {Context.getHouse().getRooms().get(1), Context.getHouse().getRooms().get(2)};
//            HeatingZone test = new HeatingZone(testRooms, MonthDay.now(), MonthDay.of(11,11), "testing");
//            test.setTemperature(3214234);

            Timer houseRefresher = new Timer(100, new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                hg.repaint();
              }
            });
            houseRefresher.start();
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
    //    House house = new House();
    //    house.getXML();
  }

  /**
   * Run.
   *
   * @param frame the frame
   * @param width the width
   * @param height the height
   */
  public static void run(JFrame frame, int width, int height) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(width, height);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
  }

  /** The open button listener. */
  class OpenL implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JFileChooser c = new JFileChooser();
      c.setCurrentDirectory(new File(System.getProperty("user.dir")));
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
