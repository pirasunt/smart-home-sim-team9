package Views;

import Controllers.EditSimulationController;
import Models.UserProfileModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class EditSimulationView extends JFrame{

    private JLabel dateField;
    private JButton changeDate;
    private JLabel timeField;
    private JButton changeTime;

    private JFrame datePickerWindow;
    private JDatePickerImpl datePicker;

    private JFrame timePickerWindow;
    private JSpinner timeSpinner;
    private JButton timeConfirm;


    public EditSimulationView(){
        this.changeDate = new JButton("Change");
        this.changeTime = new JButton("Change");
        this.timeConfirm = new JButton("Confirm");
    }

    public void addChangeDateListener(ActionListener listenForDateChange) {
        this.changeDate.addActionListener(listenForDateChange);
    }

    public void addChangeTimeListener(ActionListener listenForTimeChange) {
        this.changeTime.addActionListener(listenForTimeChange);
    }

    public void addTimeConfirmListener(ActionListener listenForTimeConfirm) {
        this.timeConfirm.addActionListener(listenForTimeConfirm);
    }


    public void openEditScreen(UserProfileModel[] userList, String currentDate, String currentTime){
        GridLayout userSelectionGrid = new GridLayout(0, 2, 20, 20);
        this.setLayout(userSelectionGrid);


        JLabel changeDateLabel = new JLabel("Change Date");
        changeDateLabel.setFont(changeDateLabel.getFont().deriveFont(Font.BOLD));

        this.add(changeDateLabel);
        this.add(new JLabel()); //Blank label
        this.dateField = new JLabel(currentDate);
        this.add(this.dateField);
        this.add(this.changeDate);

        this.add(new JSeparator());
        this.add(new JSeparator());


        JLabel changeTimeLabel = new JLabel("Change Time");
        changeTimeLabel.setFont(changeTimeLabel.getFont().deriveFont(Font.BOLD));
        this.add(changeTimeLabel);
        this.add(new JLabel());
        this.timeField = new JLabel(currentTime);
        this.add(this.timeField);
        this.add(this.changeTime);


        this.add(new JSeparator());
        this.add(new JSeparator());



        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null); // Center User Selection JFrame
        this.setVisible(true);
    }

    /**
     * Provides an interface that allows the Simulator user to change the Date
     *
     * @param formatter Object that performs the conversion between the user selecting a date on the
     *     UI via a DatePicker to a usable String format to be stored in the {@link
     *     Models.EnvironmentModel}. The core logic of formatter is found in {@link
     *     Controllers.EnvironmentController}
     */

    public void changeDate(JFormattedTextField.AbstractFormatter formatter) {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        this.datePicker = new JDatePickerImpl(panel, formatter);
        datePickerWindow = new JFrame("Pick Date");


        datePickerWindow.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        datePickerWindow.add(datePicker);
        datePickerWindow.pack();
        datePickerWindow.setLocationRelativeTo(null); // Center User Selection JFrame
        datePickerWindow.setVisible(true);
    }

    public void setDateField(String date){
        this.dateField.setText(date);
    }

    public void removeDateComponentPicker() {
        this.datePickerWindow.dispose();
    }



    /**
     * Changes the time passed.
     *
     * @param currentDate the current date
     */
    public void changeTime(Date currentDate) {
        this.timePickerWindow = new JFrame("Pick Time");
        this.timePickerWindow.setLayout(new GridLayout(2, 1, 3, 3));

        SpinnerDateModel sm = new SpinnerDateModel(currentDate, null, null, Calendar.HOUR_OF_DAY);

        this.timeSpinner = new JSpinner(sm);

        JSpinner.DateEditor te = new JSpinner.DateEditor(this.timeSpinner, "hh:mm:ss a");
        this.timeSpinner.setEditor(te);

        this.timePickerWindow.add(this.timeSpinner);

        this.timePickerWindow.add(this.timeConfirm);
        this.timePickerWindow.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.timePickerWindow.pack();
        this.timePickerWindow.setLocationRelativeTo(null); // Center User Selection JFrame
        this.timePickerWindow.setVisible(true);
    }


    /**
     * Gets the value from the {@link JSpinner} module used to change the Time of the simulated
     * environment
     *
     * @return Value of the {@link JSpinner} that needs to be casted to a useable format
     */
    public Object getTimeSpinnerVal() {
        return this.timeSpinner.getValue();
    }

    /**
     * Sets the Time field of the simulator
     *
     * @param time String representation of the new Time to set
     */
    public void setTimeField(String time) {
        this.timeField.setText(time);
    }


    public void removeTimeComponentPicker() {
        this.timePickerWindow.dispose();
    }

}
