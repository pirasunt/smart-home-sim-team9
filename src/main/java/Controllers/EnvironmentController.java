package Controllers;

import Custom.NonExistantUserProfileException;
import Enums.ProfileType;
import Models.EnvironmentModel;
import Models.Room;
import Models.SecurityModel;
import Models.UserProfileModel;
import Views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * The EnvironmentController handles all operations requested in {@link EnvironmentView} and makes
 * the necessary changes to the data within {@link EnvironmentModel}.
 */
public class EnvironmentController {

  private final EnvironmentView theView;
  private final EnvironmentModel theModel;

  /**
   * Initializes the Environment controller using a reference to the View and Model
   *
   * @param v A reference to an instance of an {@link EnvironmentView}
   * @param m A reference to an instance of an {@link EnvironmentModel}
   */
  public EnvironmentController(EnvironmentView v, EnvironmentModel m) {
    this.theView = v;
    this.theModel = m;
    this.theView.addUserListener(new StartListener());
    this.theView.addLocationListener(new LocationListener());
    this.theView.addSimulatorListener(new SimulatorListener());
    this.theView.addCreateUserListener(new CreateUserListener());
  }

  private class StartListener implements ActionListener {

    /**
     * Invoked when the 'Select User Profile' Button is pressed
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      CustomConsole.print("Selecting user profile...");
      UserProfileModel[] allProfiles = theModel.getAllUserProfiles();

      JLabel adultLabel = new JLabel("Adult", SwingConstants.CENTER);
      JLabel childLabel = new JLabel("Child", SwingConstants.CENTER);
      JLabel guestLabel = new JLabel("Guest", SwingConstants.CENTER);
      JLabel strangerLabel = new JLabel("Stranger", SwingConstants.CENTER);

      GridLayout userSelectionGrid = new GridLayout(0, 4, 20, 20);

      JFrame frame = new JFrame("User Selection");
      frame.setLayout(userSelectionGrid);

      // Labels
      frame.add(adultLabel);
      frame.add(childLabel);
      frame.add(guestLabel);
      frame.add(strangerLabel);

      UserProfileModel[][] organisedProfiles = {
        theModel.getProfilesByCategory(ProfileType.ADULT),
        theModel.getProfilesByCategory(ProfileType.CHILD),
        theModel.getProfilesByCategory(ProfileType.GUEST),
        theModel.getProfilesByCategory(ProfileType.STRANGER)
      };
      for (int i = 0; i < allProfiles.length * 4; i++) {
        int currentCol = i % 4;
        int currentRow = i / 4;

        switch (currentCol) {
          case 0: // adult column
            if (organisedProfiles[0].length <= currentRow) {
              frame.add(new JLabel());
            } else {
              JButton btn = new JButton(organisedProfiles[0][currentRow].getName());
              btn.addActionListener(
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      theModel.setCurrentUser(organisedProfiles[0][currentRow]);
                      frame.dispose();
                    }
                  });
              frame.add(btn);
            }
            break;
          case 1: // child column
            if (organisedProfiles[1].length <= currentRow) {
              frame.add(new JLabel());
            } else {
              JButton btn = new JButton(organisedProfiles[1][currentRow].getName());
              btn.addActionListener(
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      theModel.setCurrentUser(organisedProfiles[1][currentRow]);
                      frame.dispose();
                    }
                  });
              frame.add(btn);
            }
            break;
          case 2: // guest column
            if (organisedProfiles[2].length <= currentRow) {
              frame.add(new JLabel());
            } else {
              JButton btn = new JButton(organisedProfiles[2][currentRow].getName());
              btn.addActionListener(
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      theModel.setCurrentUser(organisedProfiles[2][currentRow]);
                      frame.dispose();
                    }
                  });
              frame.add(btn);
            }
            break;
          case 3: // stranger column
            if (organisedProfiles[3].length <= currentRow) {
              frame.add(new JLabel());
            } else {
              JButton btn = new JButton(organisedProfiles[3][currentRow].getName());
              btn.addActionListener(
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      theModel.setCurrentUser(organisedProfiles[3][currentRow]);
                      frame.dispose();
                    }
                  });
              frame.add(btn);
            }
            break;
        }
      }

      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null); // Center User Selection JFrame
      frame.setVisible(true);
    }
  }

  private class LocationListener implements ActionListener {
    /**
     * Invoked when the 'Select Location' Button is pressed
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (theModel.isCurrentUserSet()) {

        CustomConsole.print(
            "Selecting location for " + theModel.getCurrentUser().getName() + "...");
        Room[] roomList = theModel.getRooms();

        GridLayout userSelectionGrid = new GridLayout(0, 3, 20, 20);
        JFrame frame = new JFrame("Select Location for " + theModel.getCurrentUser().getName());
        frame.setLayout(userSelectionGrid);

        for (int i = 0; i < roomList.length; i++) {
          Room currentRoom = roomList[i];
          JButton btn = new JButton(currentRoom.getName());
          btn.addActionListener(
              new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  theModel.modifyProfileLocation(theModel.getCurrentUser(), currentRoom);
                  frame.dispose();
                }
              });
          frame.add(btn);
        }

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center User Selection JFrame
        frame.setVisible(true);

      } else {
        CustomConsole.print("ERROR: Please select a User first before setting a Location");
      }
    }
  }

  private class SimulatorListener implements ActionListener {

    /**
     * Invoked when the 'Enter Simulation' Button is pressed
     *
     * @param e the event to be processed
     */

    @Override
    public void actionPerformed(ActionEvent e) {
      if (theModel.isCurrentUserSet()) {


          theModel.initializeTimer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              int hour = Integer.parseInt(EnvironmentModel.getTimeString().substring(0, 2));
              int minute = Integer.parseInt(EnvironmentModel.getTimeString().substring(3, 5));
              int second = Integer.parseInt(EnvironmentModel.getTimeString().substring(6, 8));
              String amPM = EnvironmentModel.getTimeString().substring(8);
              amPM = amPM.replaceAll(" ", "");

              String hourString;
              String minuteString;
              String secondString;

              second++;

              if (second > 59) {
                minute++;
                second = 0;
              }
              if (minute > 59) {
                hour++;
                minute = 0;
              }
              if (hour > 12) {
                hour = 1;
                if (amPM.equals("AM")) amPM = "PM";
                else amPM = "AM";
              }

              if (hour < 10) hourString = "0" + hour;
              else hourString = String.valueOf(hour);

              if (minute < 10) minuteString = "0" + minute;
              else minuteString = String.valueOf(minute);

              if (second < 10) secondString = "0" + second;
              else secondString = String.valueOf(second);

              String time = hourString + ":" + minuteString + ":" + secondString + " " + amPM;
              SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
              try {
                EnvironmentModel.setTime(formatter.parse(time));
              } catch (ParseException parseException) {
                parseException.printStackTrace();
              }
            }
          });

          Dash dashView = new Dash(theModel.getOutsideTemp(),
                  EnvironmentModel.getDateString(),
                  EnvironmentModel.getTimeString(),
                  EnvironmentModel.getTimer().getDelay());
          new DashController(theModel, dashView);

          new CoreController(dashView.getSHC(), theModel);

      } else {
        CustomConsole.print("ERROR: Please Select a User Profile before Entering the Simulation");
      }
    }



  }

  private class CreateUserListener implements ActionListener {

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      theView.userCreationWindow();
      theView.addConfirmUserCreateListener(new ConfirmUserCreateListener());
    }

    private class ConfirmUserCreateListener implements ActionListener {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {

        try {
          theModel.addUserProfile(theView.getNewlyCreatedUser(), new File("UserProfiles.xml"));
        } catch (Exception exception) {
          exception.printStackTrace();
          theView.disposeCreateUser();
        }

        theView.disposeCreateUser();
      }
    }
  }

}
