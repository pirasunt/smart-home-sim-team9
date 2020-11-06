package Controllers;

import Models.EnvironmentModel;
import Models.Room;
import Models.UserProfileModel;
import Views.EditSimulationView;
import Views.EnvironmentView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditSimulationController {

    private final EditSimulationView theView;
    private final EnvironmentModel theModel;

    public EditSimulationController(EditSimulationView v, EnvironmentModel m) {
        this.theModel = m;
        this.theView = v;

        theView.addChangeDateListener(new ChangeDateListener());
        theView.addChangeTimeListener(new ChangeTimeListener());

        createEditWindow();


    }

    private void createEditWindow(){
        UserProfileModel[] allProfiles = theModel.getAllUserProfiles();
        Room[] allRooms = theModel.getRooms();
        JLabel userLabels[] = new JLabel[allProfiles.length];
        JComboBox<Room> roomDropdowns[] = new JComboBox[allProfiles.length];



        for(int i = 0; i < allProfiles.length; i++){
           userLabels[i] = new JLabel(allProfiles[i].getName());
            JComboBox<Room> comboBox = new JComboBox<>(allRooms);


            //nested for loop sets the dropdown to the currentRoom of the user
            for(int j = 0; j < allRooms.length; j++){
                if(allProfiles[i].getRoomID() == allRooms[j].getId())
                    comboBox.setSelectedItem(allRooms[j]);
            }

            int currentIndex = i;
            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox cb = (JComboBox) e.getSource(); // Newly Selected item
                    theModel.modifyProfileLocation(allProfiles[currentIndex], (Room)cb.getSelectedItem());
                }
            });

            roomDropdowns[i] = comboBox;
        }
        theView.openEditScreen(userLabels, roomDropdowns, theModel.getDateString(), theModel.getTimeString());

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
                    theModel.setDate(cal); // Update Environment date
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
            theView.changeTime(theModel.getDateObject());
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
                    theModel.setTime(date); // Update Environment time
                    theView.removeTimeComponentPicker();
                }
            }
        }
    }
}