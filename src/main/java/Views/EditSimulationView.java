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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(13, 1, new Insets(0, 0, 0, 0), -1, -1));
        changeTempPanel = new JPanel();
        changeTempPanel.setLayout(new GridLayoutManager(1, 4, new Insets(10, 5, 10, 5), -1, -1));
        mainPanel.add(changeTempPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Change Temp");
        changeTempPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tempSpinner = new JSpinner();
        changeTempPanel.add(tempSpinner, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        changeTempPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        changeTempPanel.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        mainPanel.add(separator1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        changeTempSeparator = new JSeparator();
        mainPanel.add(changeTempSeparator, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        changeDatePanel = new JPanel();
        changeDatePanel.setLayout(new GridLayoutManager(1, 5, new Insets(10, 5, 10, 5), -1, -1));
        mainPanel.add(changeDatePanel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Change Date");
        changeDatePanel.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dateField = new JLabel();
        Font dateFieldFont = this.$$$getFont$$$("Fira Code", -1, 16, dateField.getFont());
        if (dateFieldFont != null) dateField.setFont(dateFieldFont);
        dateField.setText("[DATE STRING]");
        changeDatePanel.add(dateField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changeDate = new JButton();
        changeDate.setText("Change");
        changeDatePanel.add(changeDate, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        changeDatePanel.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        changeDatePanel.add(spacer4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        changeDateSeparator = new JSeparator();
        mainPanel.add(changeDateSeparator, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        changeTimePanel = new JPanel();
        changeTimePanel.setLayout(new GridLayoutManager(1, 5, new Insets(10, 5, 10, 5), -1, -1));
        mainPanel.add(changeTimePanel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 18, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Change Time");
        changeTimePanel.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeField = new JLabel();
        Font timeFieldFont = this.$$$getFont$$$("Fira Code", -1, 16, timeField.getFont());
        if (timeFieldFont != null) timeField.setFont(timeFieldFont);
        timeField.setText("[TIME STRING]");
        changeTimePanel.add(timeField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changeTime = new JButton();
        changeTime.setText("Change");
        changeTimePanel.add(changeTime, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        changeTimePanel.add(spacer5, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        changeTimePanel.add(spacer6, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        changeTimeSeparator = new JSeparator();
        mainPanel.add(changeTimeSeparator, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        userRoomPrivPanel = new JPanel();
        userRoomPrivPanel.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 0, 5), -1, -1));
        mainPanel.add(userRoomPrivPanel, new GridConstraints(12, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        jp3 = new JPanel();
        jp3.setLayout(new GridLayoutManager(1, 1, new Insets(10, 5, 0, 0), -1, -1));
        mainPanel.add(jp3, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Fira Code", Font.BOLD, 18, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Edit Users");
        jp3.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jp4 = new JPanel();
        jp4.setLayout(new GridLayoutManager(1, 2, new Insets(10, 5, 10, 50), -1, -1));
        mainPanel.add(jp4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 1, false));
        final JLabel label5 = new JLabel();
        label5.setEnabled(true);
        Font label5Font = this.$$$getFont$$$("Cambria", Font.ITALIC, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-6750193));
        label5.setText("\nNOTE: Changes will be reflected on the dashboard once this window is closed\n");
        jp4.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        jp4.add(spacer7, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        timeSpeedPanel = new JPanel();
        timeSpeedPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(timeSpeedPanel, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        oneTimeSpeed = new JRadioButton();
        oneTimeSpeed.setText("1 X");
        timeSpeedPanel.add(oneTimeSpeed, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tenTimeSpeed = new JRadioButton();
        tenTimeSpeed.setText("10 X");
        timeSpeedPanel.add(tenTimeSpeed, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        hundredTimeSpeed = new JRadioButton();
        hundredTimeSpeed.setText("100 X");
        timeSpeedPanel.add(hundredTimeSpeed, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        mainPanel.add(separator2, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        privilegeDef = new JLabel();
        privilegeDef.setHorizontalAlignment(0);
        privilegeDef.setText("<html>ADULT: Access to ALL modules<br/>CHILD: Access to SHC module<br/>GUEST: Access to SHC module<br/>STRANGER: Access to no modules</html>");
        mainPanel.add(privilegeDef, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(tenTimeSpeed);
        buttonGroup.add(hundredTimeSpeed);
        buttonGroup.add(oneTimeSpeed);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
