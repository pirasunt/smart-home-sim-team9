package Controllers;

import Custom.NonExistantUserProfileException;
import Enums.ProfileType;
import Enums.WallType;
import Models.EnvironmentModel;
import Models.Room;
import Models.UserProfileModel;
import Models.Walls.WindowWall;
import Views.Console;
import Views.EditSimulationView;
import Views.EnvironmentView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
      Console.print("Selecting user profile...");
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

        Console.print("Selecting location for " + theModel.getCurrentUser().getName() + "...");
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
        Console.print("ERROR: Please select a User first before setting a Location");
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
          theView.addSimulationToggleListener(new SimulationToggleListener());
          theView.addEditSimulationListener(new EditSimulationListener());

        } else {
          Console.print(
              "ERROR: Please set location for selected user: '"
                  + theModel.getCurrentUser().getName()
                  + "'");
        }
      } else {
        Console.print("ERROR: Please Select a User Profile before Entering the Simulation");
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
        } else if (theModel.getSimulationRunning() == false) {
          theModel.startSimulation();
          theView.changeSimulationToggleText("Stop Simulation");
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
        Console.print("NO LOCATION HAS BEEN SET FOR: " + theModel.getCurrentUser().getName());
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

  private class EditSimulationListener implements ActionListener {

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

      //Pass on responsibility of editing the simulation to its own controller and view.
      //The Environment Model contains all the environment data that will be needed.
      EditSimulationView editSimView = new EditSimulationView();
      new EditSimulationController(editSimView, theModel);

      editSimView.addWindowListener(new EditSimulationWindowListener());

/*
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
      */
    }

    private class EditSimulationWindowListener implements WindowListener {
      /**
       * Invoked the first time a window is made visible.
       *
       * @param e the event to be processed
       */
      @Override
      public void windowOpened(WindowEvent e) {

      }

      /**
       * Invoked when the user attempts to close the window
       * from the window's system menu.
       *
       * @param e the event to be processed
       */
      @Override
      public void windowClosing(WindowEvent e) {

      }

      /**
       * Invoked when a window has been closed as the result
       * of calling dispose on the window.
       *
       * @param e the event to be processed
       */
      @Override
      public void windowClosed(WindowEvent e) {
        theView.refreshDash(theModel.getDateString(), theModel.getTimeString());
      }

      /**
       * Invoked when a window is changed from a normal to a
       * minimized state. For many platforms, a minimized window
       * is displayed as the icon specified in the window's
       * iconImage property.
       *
       * @param e the event to be processed
       * @see Frame#setIconImage
       */
      @Override
      public void windowIconified(WindowEvent e) {

      }

      /**
       * Invoked when a window is changed from a minimized
       * to a normal state.
       *
       * @param e the event to be processed
       */
      @Override
      public void windowDeiconified(WindowEvent e) {

      }

      /**
       * Invoked when the Window is set to be the active Window. Only a Frame or
       * a Dialog can be the active Window. The native windowing system may
       * denote the active Window or its children with special decorations, such
       * as a highlighted title bar. The active Window is always either the
       * focused Window, or the first Frame or Dialog that is an owner of the
       * focused Window.
       *
       * @param e the event to be processed
       */
      @Override
      public void windowActivated(WindowEvent e) {

      }

      /**
       * Invoked when a Window is no longer the active Window. Only a Frame or a
       * Dialog can be the active Window. The native windowing system may denote
       * the active Window or its children with special decorations, such as a
       * highlighted title bar. The active Window is always either the focused
       * Window, or the first Frame or Dialog that is an owner of the focused
       * Window.
       *
       * @param e the event to be processed
       */
      @Override
      public void windowDeactivated(WindowEvent e) {

      }
    }
  }
}
