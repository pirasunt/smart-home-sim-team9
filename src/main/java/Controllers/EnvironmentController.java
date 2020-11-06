package Controllers;

import Custom.NonExistantUserProfileException;
import Enums.ProfileType;
import Enums.WallType;
import Models.EnvironmentModel;
import Models.Room;
import Models.UserProfileModel;
import Models.Walls.WindowWall;
import Views.CustomConsole;
import Views.EnvironmentView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * The EnvironmentController handles all operations requested in {@link EnvironmentView} and makes
 * the necessary changes to the data within {@link EnvironmentModel}.
 */
public class EnvironmentController {

  private final EnvironmentView theView;
  private final EnvironmentModel theModel;
  private int hour;
  private int minute;
  private int second;
  private String amPM;

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
  /**
   * Initializes the timer
   */
  Timer timer = new Timer(1000, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      String hourString;
      String minuteString;
      String secondString;

      second++;
      if(second > 59) {
        minute++;
        second = 0;
      }
      if(minute > 59) {
        hour++;
        minute = 0;
      }
      if(hour > 12){
        hour = 1;
        if(amPM == "AM")
          amPM = "PM";
        else
          amPM = "AM";
      }

      if(hour < 10)
        hourString = "0" + hour;
      else
        hourString = String.valueOf(hour);

      if(minute < 10)
        minuteString = "0" + minute;
      else
        minuteString = String.valueOf(minute);

      if(second < 10)
        secondString = "0" + second;
      else
        secondString = String.valueOf(second);

      String time = hourString + ":" + minuteString + ":" + secondString + " " + amPM;
      theView.setTimeField(time);
    }
  });

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

        CustomConsole.print("Selecting location for " + theModel.getCurrentUser().getName() + "...");
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
    private Room[] allRooms;

    @Override
    public void actionPerformed(ActionEvent e) {
      if (theModel.isCurrentUserSet()) {
        if (theModel.getCurrentUser().getRoomID() != -1) {

          theView.createDash(
              theModel.getOutsideTemp(), theModel.getDateString(), theModel.getTimeString());
          UserProfileModel[] allProfiles = theModel.getAllUserProfiles();

          for (int i = 0; i < allProfiles.length; i++) {
            boolean isCurrentUser = false;
            if (theModel.getCurrentUser().getProfileID() == allProfiles[i].getProfileID())
              isCurrentUser = true;

            theView.addProfileToDropDown(allProfiles[i], isCurrentUser);
          }

          allRooms = theModel.getRooms();
          for (int i = 0; i < allRooms.length; i++) {
            boolean isCurrentRoom = false;
            if (theModel.getCurrentUser().getRoomID() == allRooms[i].getId()) isCurrentRoom = true;

            theView.addRoomToDropDown(allRooms[i], isCurrentRoom);
          }

          theView.addUserDropDownListener(new UserDropDownListener());
          theView.addUserRoomDropDownListener(new UserRoomDropDownListener());
          theView.addTempSpinnerListener(new TempSpinnerListener());
          theView.addChangeDateListener(new ChangeDateListener());
          theView.addChangeTimeListener(new ChangeTimeListener());
          theView.addSimulationToggleListener(new SimulationToggleListener());
          theView.addObstructionToggleListener(new ObstructWindowsToggleListener());
          theView.addconfirmTimeSpeedListener(new confirmTimeSpeedListener());

        } else {
          CustomConsole.print(
              "ERROR: Please set location for selected user: '"
                  + theModel.getCurrentUser().getName()
                  + "'");
        }
      } else {
        CustomConsole.print("ERROR: Please Select a User Profile before Entering the Simulation");
      }
    }

    private class UserDropDownListener implements ActionListener {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource(); // Newly Selected item
        UUID newCurrentUserID = ((UserProfileModel) cb.getSelectedItem()).getProfileID();
        try {
          theModel.setCurrentUser(theModel.getUserByID(newCurrentUserID));
        } catch (NonExistantUserProfileException nonExistantUserProfileException) {
          nonExistantUserProfileException.printStackTrace();
        }

        if (theModel.getCurrentUser().getRoomID() == -1) {
          theView.setRoomDropDownIndex(-1);
        } else {
          for (int i = 0; i < allRooms.length; i++) {
            if (allRooms[i].getId() == theModel.getCurrentUser().getRoomID()) {
              theView.setRoomDropDownItem(allRooms[i]);
              break;
            }
          }
        }
      }
    }

    private class SimulationToggleListener implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
        if (theModel.getSimulationRunning() == true) {
          theModel.stopSimulation();
          theView.changeSimulationToggleText("Start Simulation");
          timer.stop();
        } else if (theModel.getSimulationRunning() == false) {
          theModel.startSimulation();
          theView.changeSimulationToggleText("Stop Simulation");
          hour = Integer.parseInt(theModel.getTimeString().substring(0,2));
          minute = Integer.parseInt(theModel.getTimeString().substring(3,5));
          second = Integer.parseInt(theModel.getTimeString().substring(6,8));
          amPM = theModel.getTimeString().substring(8);
          timer.restart();
        }
      }
    }
  }

  private class UserRoomDropDownListener implements ActionListener {
    /**
     * Invoked when the Room dropdown is selected and modified.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      JComboBox cb = (JComboBox) e.getSource(); // Newly Selected item
      if (cb.getSelectedIndex() == -1) {
        CustomConsole.print("NO LOCATION HAS BEEN SET FOR: " + theModel.getCurrentUser().getName());
      } else {
        Room newRoom = (Room) cb.getSelectedItem();
        if (newRoom.getId() != theModel.getCurrentUser().getRoomID()) {
          theModel.modifyProfileLocation(theModel.getCurrentUser(), newRoom);
        }
      }
    }
  }

  private class TempSpinnerListener implements ChangeListener {
    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
      theModel.setTemperature(
          theView
              .getTemperatureFromSpinner()); // Any change on Temp Spinner will update Environment
      // attribute
    }
  }

  private class ChangeDateListener implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        theView.ChangeDate(new CustomDateFormatter());
    }

    private class CustomDateFormatter extends JFormattedTextField.AbstractFormatter {
      /**
       * Parses <code>text</code> returning an arbitrary Object. Some formatters may return null.
       *
       * @param text String to convert
       * @return Object representation of text
       * @throws ParseException if there is an error in the conversion
       */
      private final String datePattern = "MMM dd, yyyy";

      private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

      @Override
      public Object stringToValue(String text) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) dateFormatter.parseObject(text));
        return cal;
      }

      /**
       * Returns the string value to display for <code>value</code>.
       *
       * @param value Value to convert
       * @return String representation of value
       * @throws ParseException if there is an error in the conversion
       */
      @Override
      public String valueToString(Object value) throws ParseException {
        if (value != null) {
          Calendar cal = (Calendar) value;
          theView.setDateField(dateFormatter.format(cal.getTime()));
          theModel.setDate(cal.getTime()); // Update Environment date
          theView.removeDateComponentPicker();
          theView.disposeDash();
          return dateFormatter.format(cal.getTime());
        }
        return "";
      }
    }
  }

  private class ChangeTimeListener implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        theView.addConfirmTimeListener(new ConfirmTimeListener());
        theView.ChangeTime(theModel.getDateObject());
    }

    private class ConfirmTimeListener implements ActionListener {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        Object value = theView.getTimeSpinnerVal();
        if (value instanceof Date) {
          Date date = (Date) value;
          SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
          String time = formatter.format(date);
          theView.setTimeField(time);
          theModel.setTime(date); // Update Environment time
          theView.removeTimeComponentPicker();
          theView.disposeDash();
        }
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
          theModel.addUserProfile(theView.getNewlyCreatedUser());
        } catch (Exception exception) {
          exception.printStackTrace();
          theView.disposeCreateUser();
        }

        theView.disposeCreateUser();
      }
    }
  }

  private class ObstructWindowsToggleListener implements ActionListener {

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (theModel.isWindowObstructed()) {
        Room[] rooms = theModel.getRooms();

        for (Room r : rooms) {
          if (r.getId() == 0) continue;

          if (r.getLeftWall().getType() == WallType.WINDOWS) {
            ((WindowWall) r.getLeftWall()).setWindowObstructed(false);
          }
          if (r.getRightWall().getType() == WallType.WINDOWS) {
            ((WindowWall) r.getRightWall()).setWindowObstructed(false);
          }
          if (r.getTopWall().getType() == WallType.WINDOWS) {
            ((WindowWall) r.getTopWall()).setWindowObstructed(false);
          }
          if (r.getBottomWall().getType() == WallType.WINDOWS) {
            ((WindowWall) r.getBottomWall()).setWindowObstructed(false);
          }
        }

        theModel.getHouseGraphic().repaint();
        theModel.clearWindows();
        theView.changeWindowsObstructedToggleText("Obstruct Windows");
      } else if (!theModel.isWindowObstructed()) {
        Room[] rooms = theModel.getRooms();

        for (Room r : rooms) {
          if (r.getId() == 0) continue;

          if (r.getLeftWall().getType() == WallType.WINDOWS) {
            ((WindowWall) r.getLeftWall()).setWindowObstructed(true);
          }
          if (r.getRightWall().getType() == WallType.WINDOWS) {
            ((WindowWall) r.getRightWall()).setWindowObstructed(true);
          }
          if (r.getTopWall().getType() == WallType.WINDOWS) {
            ((WindowWall) r.getTopWall()).setWindowObstructed(true);
          }
          if (r.getBottomWall().getType() == WallType.WINDOWS) {
            ((WindowWall) r.getBottomWall()).setWindowObstructed(true);
          }
        }

        theModel.getHouseGraphic().repaint();
        theModel.obstructWindows();
        theView.changeWindowsObstructedToggleText("Clear Windows");
      }
    }
  }

  private class confirmTimeSpeedListener implements ActionListener {
    /**
     * Invoked when the 'OK' Button is pressed for the time speed
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      if (theView.getTimeSpeed() == "10x")
        timer.setDelay(100);
      else if (theView.getTimeSpeed() == "100x")
        timer.setDelay(10);
      else
        timer.setDelay(1000);
    }
  }
}
