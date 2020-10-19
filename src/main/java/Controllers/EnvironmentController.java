package Controllers;

import Custom.NonExistantUserProfileException;
import Enums.profileType;
import Models.EnvironmentModel;
import Models.Room;
import Models.UserProfileModel;
import Views.Console;
import Views.Dash;
import Views.EnvironmentView;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

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
import java.util.Properties;
import java.util.UUID;

public class EnvironmentController {

    private EnvironmentView theView;
    private EnvironmentModel theModel;

    private Boolean action1 = false;
    private Boolean action2= false;

    public EnvironmentController(EnvironmentView v, EnvironmentModel m){
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
            UserProfileModel[] allProfiles =  theModel.getAllUserProfiles();

            JLabel adultLabel = new JLabel("Adult", SwingConstants.CENTER);
            JLabel childLabel = new JLabel("Child", SwingConstants.CENTER);
            JLabel guestLabel = new JLabel("Guest", SwingConstants.CENTER);
            JLabel strangerLabel = new JLabel("Stranger", SwingConstants.CENTER);

            GridLayout userSelectionGrid = new GridLayout(0, 4, 20,20);

            JFrame frame = new JFrame("User Selection");
            frame.setLayout(userSelectionGrid);

            //Labels
            frame.add(adultLabel);
            frame.add(childLabel);
            frame.add(guestLabel);
            frame.add(strangerLabel);

            UserProfileModel[][] organisedProfiles = {theModel.getProfilesByCategory(profileType.ADULT), theModel.getProfilesByCategory(profileType.CHILD), theModel.getProfilesByCategory(profileType.GUEST), theModel.getProfilesByCategory(profileType.STRANGER)};
            for(int i = 0; i< allProfiles.length*4; i++) {
                int currentCol = i%4;
                int currentRow = i/4;

                switch(currentCol){
                    case 0: //adult column
                        if(organisedProfiles[0].length <= currentRow) {
                            frame.add(new JLabel());
                        } else {
                            JButton btn = new JButton(organisedProfiles[0][currentRow].getName());
                            btn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    theModel.setCurrentUser(organisedProfiles[0][currentRow]);
                                    frame.dispose();
                                }
                            });
                            frame.add(btn);
                        }
                        break;
                    case 1: //child column
                        if(organisedProfiles[1].length <=  currentRow) {
                            frame.add(new JLabel());
                        } else {
                            JButton btn = new JButton(organisedProfiles[1][currentRow].getName());
                            btn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    theModel.setCurrentUser(organisedProfiles[1][currentRow]);
                                    frame.dispose();
                                }
                            });
                            frame.add(btn);
                        }
                        break;
                    case 2: //guest column
                        if(organisedProfiles[2].length <= currentRow) {
                            frame.add(new JLabel());
                        } else {
                            JButton btn = new JButton(organisedProfiles[2][currentRow].getName());
                            btn.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    theModel.setCurrentUser(organisedProfiles[2][currentRow]);
                                    frame.dispose();
                                }
                            });
                            frame.add(btn);
                        }
                        break;
                    case 3://stranger column
                        if(organisedProfiles[3].length <= currentRow) {
                            frame.add(new JLabel());
                        } else {
                            JButton btn = new JButton(organisedProfiles[3][currentRow].getName());
                            btn.addActionListener(new ActionListener() {
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
            frame.setLocationRelativeTo(null); //Center User Selection JFrame
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
            if(theModel.isCurrentUserSet()) {

                Console.print("Selecting location for "  + theModel.getCurrentUser().getName() + "...");
                Room[] roomList = theModel.getRooms();


                GridLayout userSelectionGrid = new GridLayout(0, 3, 20,20);
                JFrame frame = new JFrame("Select Location for " + theModel.getCurrentUser().getName());
                frame.setLayout(userSelectionGrid);

                for(int i = 0; i< roomList.length; i++) {
                    Room currentRoom = roomList[i];
                    JButton btn = new JButton(currentRoom.getName());
                    btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            theModel.modifyProfileLocation(theModel.getCurrentUser(),currentRoom);
                            frame.dispose();
                        }
                    });
                    frame.add(btn);
                }


                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); //Center User Selection JFrame
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
             if(theModel.isCurrentUserSet()) {
                 if(theModel.getCurrentUser().getRoomID() != -1) {

                    theView.createDash(theModel.getOutsideTemp(), theModel.getDateString(), theModel.getTimeString());
                    UserProfileModel[] allProfiles = theModel.getAllUserProfiles();

                    for(int i =0; i < allProfiles.length; i++){
                        boolean isCurrentUser = false;
                        if(theModel.getCurrentUser().getProfileID() == allProfiles[i].getProfileID())
                            isCurrentUser = true;

                        theView.addProfileToDropDown(allProfiles[i], isCurrentUser);

                    }

                    allRooms = theModel.getRooms();
                     for(int i =0; i < allRooms.length; i++){
                         boolean isCurrentRoom = false;
                         if(theModel.getCurrentUser().getRoomID() == allRooms[i].getId())
                             isCurrentRoom = true;

                         theView.addRoomToDropDown(allRooms[i], isCurrentRoom);
                     }

                     theView.addUserDropDownListener(new UserDropDownListener());
                     theView.addUserRoomDropDownListener(new UserRoomDropDownListener());
                     theView.addTempSpinnerListener(new TempSpinnerListener());
                     theView.addChangeDateListener(new ChangeDateListener());
                     theView.addChangeTimeListener(new ChangeTimeListener());



                 } else {
                     Console.print("ERROR: Please set location for selected user: '" + theModel.getCurrentUser().getName() + "'");
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
                 JComboBox cb = (JComboBox) e.getSource(); //Newly Selected item
                 UUID newCurrentUserID = ((UserProfileModel)cb.getSelectedItem()).getProfileID();
                 try {
                     theModel.setCurrentUser(theModel.getUserByID(newCurrentUserID));
                 } catch (NonExistantUserProfileException nonExistantUserProfileException) {
                     nonExistantUserProfileException.printStackTrace();
                 }

                 if(theModel.getCurrentUser().getRoomID() == -1) {
                     theView.setRoomDropDownIndex(-1);
                 } else {
                     for(int i=0; i < allRooms.length; i++) {
                         if(allRooms[i].getId() == theModel.getCurrentUser().getRoomID()) {
                             theView.setRoomDropDownItem(allRooms[i]);
                             break;
                         }
                     }
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
             JComboBox cb = (JComboBox) e.getSource(); //Newly Selected item
             if(cb.getSelectedIndex() == -1) {
                 Console.print("NO LOCATION HAS BEEN SET FOR: " + theModel.getCurrentUser().getName());
             } else {
                 Room newRoom = (Room) cb.getSelectedItem();
                 if(newRoom.getId() != theModel.getCurrentUser().getRoomID()) {
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
            theModel.setTemperature(theView.getTemperatureFromSpinner()); //Any change on Temp Spinner will update Environment attribute
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
            if (action2 == false) {
                action1 = true;
                theView.ChangeDate(new CustomDateFormatter());
            }

        }

        private class CustomDateFormatter extends JFormattedTextField.AbstractFormatter {
            /**
             * Parses <code>text</code> returning an arbitrary Object. Some
             * formatters may return null.
             *
             * @param text String to convert
             * @return Object representation of text
             * @throws ParseException if there is an error in the conversion
             */
            private String datePattern = "MMM dd, yyyy";
            private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
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
                    theModel.setDate(cal.getTime());//Update Environment date
                    theView.removeDateComponentPicker();
                    action1 = false;
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
            if (action1 == false) {
                action2 = true;
                theView.addConfirmTimeListener(new ConfirmTimeListener());
                theView.ChangeTime(theModel.getDateObject());
            }
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
                    Date date = (Date)value;
                    SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
                    String time = formatter.format(date);
                    theView.getTimeField().setValue(time);
                    theModel.setTime(date); //Update Environment time
                    theView.removeTimeComponentPicker();
                    action2 = false;
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
}









