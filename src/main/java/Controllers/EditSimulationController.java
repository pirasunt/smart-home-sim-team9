package Controllers;

import Enums.ProfileType;
import Models.Context;
import Models.EnvironmentModel;
import Models.Room;
import Models.UserProfileModel;
import Views.EditSimulationView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Edit simulation controller.
 */
public class EditSimulationController {

    private final EditSimulationView theView;
    private final EnvironmentModel theModel;
    private final boolean isSimRunning;

    /**
     * Instantiates a new Edit simulation controller.
     *
     * @param v               An instance of the view associated with this controller
     * @param m               the EnvironmentModel
     * @param simulatorStatus True if the simulator is running; false otherwise
     */
    public EditSimulationController(EditSimulationView v, EnvironmentModel m, boolean simulatorStatus) {
        this.theModel = m;
        this.theView = v;
        this.isSimRunning = simulatorStatus;

        //Only add Temp, Date and Time listeners if the simulation is NOT running
        if (!isSimRunning) {
            theView.addChangeDateListener(new ChangeDateListener());
            theView.addChangeTimeListener(new ChangeTimeListener());
            theView.addTempSpinnerListener(new TempChangeListener());
            theView.addTimeSpeedRadioListener(new TimeSpeedListener());
        }
        createEditWindow();

    }

    private void createEditWindow() {
        UserProfileModel[] allProfiles = theModel.getAllUserProfiles();
        Room[] allRooms = theModel.getRooms();
        ProfileType[] allProfileTypes = Enums.ProfileType.values();
        JLabel[] userLabels = new JLabel[allProfiles.length];
        JComboBox<Room>[] roomDropdowns = new JComboBox[allProfiles.length];
        JComboBox<ProfileType>[] profileTypes = new JComboBox[allProfiles.length];

        for (int i = 0; i < allProfiles.length; i++) {
            userLabels[i] = new JLabel(allProfiles[i].getName(), SwingConstants.CENTER);
            JComboBox<Room> roomComboBox = new JComboBox<>(allRooms);
            JComboBox<ProfileType> profileComboBox = new JComboBox<>(allProfileTypes);

            // nested for loop sets the dropdown to the currentRoom of the user
            for (int j = 0; j < allRooms.length; j++) {
                if (allProfiles[i].getRoomID() == allRooms[j].getId())
                    roomComboBox.setSelectedItem(allRooms[j]);
            }

            for (int j = 0; j < allProfileTypes.length; j++) {
                if (allProfiles[i].getProfileType() == allProfileTypes[j]) {
                    profileComboBox.setSelectedItem(allProfileTypes[j]);
                }
            }

            int currentIndex = i;
            roomComboBox.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JComboBox cb = (JComboBox) e.getSource(); // Newly Selected item
                            theModel.modifyProfileLocation(
                                    allProfiles[currentIndex], (Room) cb.getSelectedItem());
                        }
                    });

            profileComboBox.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JComboBox cb = (JComboBox) e.getSource(); // Newly Selected item
                            theModel.modifyUserPrivilege(
                                    allProfiles[currentIndex], (ProfileType) cb.getSelectedItem());
                        }
                    });

            roomDropdowns[i] = roomComboBox;
            profileTypes[i] = profileComboBox;
        }
        theView.setupEditScreen(
                userLabels,
                roomDropdowns,
                profileTypes, Context.getDateString(),
                Context.getTimeString(), this.isSimRunning);

    }

    private class ChangeDateListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            theView.changeDate(new CustomDateFormatter());
        }

        private class CustomDateFormatter extends JFormattedTextField.AbstractFormatter {

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
                    Context.setDate(cal); // Update Environment date
                    theView.removeDateComponentPicker();
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
            theView.changeTime(Context.getDateObject());
            theView.addTimeConfirmListener(new TimeConfirmListener());
        }

        private class TimeConfirmListener implements ActionListener {
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
                    Context.setTime(date); // Update Environment time
                    theView.removeTimeComponentPicker();
                }
            }
        }
    }

    private class TempChangeListener implements ChangeListener {
        /**
         * Invoked when the target of the listener has changed its state.
         *
         * @param e a ChangeEvent object
         */
        @Override
        public void stateChanged(ChangeEvent e) {
            theModel.setTemperature(theView.getTempSpinnerValue());
        }
    }

    private class TimeSpeedListener implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedButton = ((JRadioButton) e.getSource()).getText();

            switch (selectedButton) {
                case "1 X":
                    Context.setDelay(1000);
                    break;
                case "10 X":
                    Context.setDelay(100);
                    break;
                case "100 X":
                    Context.setDelay(10);
                    break;
            }
        }
    }
}
