package Controllers;

import Models.EnvironmentModel;
import Views.EditSimulationView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
       theView.openEditScreen(theModel.getAllUserProfiles(), theModel.getDateString(), theModel.getTimeString());
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
                    theModel.setDate(cal.getTime()); // Update Environment date
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
