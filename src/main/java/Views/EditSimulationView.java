package Views;

import Controllers.EnvironmentController;
import Enums.ProfileType;
import Models.EnvironmentModel;
import Models.Room;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class EditSimulationView extends JFrame {

    private final JButton timeConfirm;
    private JLabel dateField;
    private JButton changeDate;
    private JLabel timeField;
    private JButton changeTime;
    private JSpinner tempSpinner;
    private JFrame datePickerWindow;
    private JFrame timePickerWindow;
    private JSpinner timeSpinner;
    private JPanel userRoomPrivPanel; // This is dynamically generated, so can not use GUI Designer
    private JPanel mainPanel;
    private JRadioButton oneTimeSpeed;
    private JRadioButton tenTimeSpeed;
    private JRadioButton hundredTimeSpeed;
    private JPanel changeTempPanel;
    private JPanel changeDatePanel;
    private JPanel changeTimePanel;
    private JPanel jp3;
    private JPanel jp4;
    private JPanel timeSpeedPanel;
    private JLabel privilegeDef;
    private JSeparator changeTempSeparator;
    private JSeparator changeDateSeparator;
    private JSeparator changeTimeSeparator;

    public EditSimulationView(int temp, int delay) {
        this.timeConfirm = new JButton("Confirm");
        this.tempSpinner.setValue(temp);

        int speedFactor = 1000 / delay;
        switch (speedFactor) {
            case 1:
                this.oneTimeSpeed.setSelected(true);
                break;
            case 10:
                this.tenTimeSpeed.setSelected(true);
                break;
            case 100:
                this.hundredTimeSpeed.setSelected(true);
                break;
        }
    }

    public void addTimeSpeedRadioListener(ActionListener listenForSpeedChange) {
        this.oneTimeSpeed.addActionListener(listenForSpeedChange);
        this.tenTimeSpeed.addActionListener(listenForSpeedChange);
        this.hundredTimeSpeed.addActionListener(listenForSpeedChange);
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

    public void addTempSpinnerListener(ChangeListener listenForTempChange) {
        this.tempSpinner.addChangeListener(listenForTempChange);
    }

    public Integer getTempSpinnerValue() {
        return (Integer) this.tempSpinner.getValue();
    }

    public void setupEditScreen(
            JLabel[] userLabels,
            JComboBox<Room>[] userDropdowns,
            JComboBox<ProfileType>[] profileTypes,
            String currentDate,
            String currentTime,
            boolean isSimRunning) {

        //If simulator is running, remove panels that changes temperature, date and time
        if (isSimRunning) {
            this.changeTempPanel.setVisible(false);
            this.changeDatePanel.setVisible(false);
            this.changeTimePanel.setVisible(false);
            this.timeSpeedPanel.setVisible(false);
            this.changeTempSeparator.setVisible(false);
            this.changeDateSeparator.setVisible(false);
            this.changeTimeSeparator.setVisible(false);
            CustomConsole.print("WARNING: The Simulation is turned on. Unable to Change Temperature, Date, Time and Time Speed");
        }

        GridLayout userSelectionGrid = new GridLayout(0, 3, 20, 20);

        this.userRoomPrivPanel.setLayout(userSelectionGrid);
        this.userRoomPrivPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 5));
        this.dateField.setText(currentDate);
        this.timeField.setText(currentTime);

        this.userRoomPrivPanel.add(new JSeparator());
        this.userRoomPrivPanel.add(new JLabel());
        this.userRoomPrivPanel.add(new JLabel());

        this.privilegeDef.setText(
                "ADULT: Access to ALL modules \nCHILD: Access to SHC module \nGUEST: Access to SHC module \nSTRANGER: Access to no modules");

        JLabel userLabel = new JLabel("Users", SwingConstants.CENTER);
        JLabel roomLabel = new JLabel("Current Room");
        JLabel privilegeLabel = new JLabel("Privilege");

        JLabel[] tempArray = {userLabel, roomLabel, privilegeLabel};
        for (JLabel label : tempArray) {
            label.setFont(new Font(null, Font.BOLD, 14));
            this.userRoomPrivPanel.add(label);
        }

        // userLabels, userDropdowns & profileTypes arrays are the same length
        for (int i = 0; i < userLabels.length; i++) {
            this.userRoomPrivPanel.add(userLabels[i]);
            this.userRoomPrivPanel.add(userDropdowns[i]);
            this.userRoomPrivPanel.add(profileTypes[i]);
        }

        this.add(this.mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null); // Center User Selection JFrame
        this.setVisible(true);
    }

    /**
     * Provides an interface that allows the Simulator user to change the Date
     *
     * @param formatter Object that performs the conversion between the user selecting a date on the
     *                  UI via a DatePicker to a usable String format to be stored in the {@link EnvironmentModel}.
     *                  The core logic of formatter is found in {@link EnvironmentController}
     */
    public void changeDate(JFormattedTextField.AbstractFormatter formatter) {
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(panel, formatter);
        datePickerWindow = new JFrame("Pick Date");

        datePickerWindow.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        datePickerWindow.add(datePicker);
        datePickerWindow.pack();
        datePickerWindow.setLocationRelativeTo(null); // Center User Selection JFrame
        datePickerWindow.setVisible(true);
    }

    public void setDateField(String date) {
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
