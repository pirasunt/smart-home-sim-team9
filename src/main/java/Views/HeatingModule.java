package Views;

import Controllers.EnvironmentController;
import Models.Context;
import Models.EnvironmentModel;
import Models.Room;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * The type Heating module.
 */
public class HeatingModule {
    /**
     * The Rooms in house.
     */
    Room[] roomsInHouse = new Room[Context.getHouse().getRooms().size()];

    private JButton createNewHeatingZoneButton;
    private JPanel panel;
    private JPanel mainHeatWrap;
    private JPanel heatingZonePanel;
    private JPanel awayModeTempWrap;
    private JSpinner awayTempSpinner;
    private JSpinner dangerTempSpinner;
    private JPanel seasonPanel;
    private JPanel summerPanel;
    private JPanel winterPanel;
    private JLabel summerStartLabel;
    private JLabel winterStartLabel;
    private JButton summerChangeBtn;
    private JButton winterChangeBtn;
    private JPanel roomPanel;
    private JList<String> heatingZones;
    private JFrame datePickerWindow;

    /**
     * Instantiates a new Heating module.
     */
    public HeatingModule() {
        roomsInHouse = Context.getHouse().getRooms().toArray(roomsInHouse);

        DefaultListModel<String> lm = new DefaultListModel<>();
        heatingZones = new JList<String>(lm);
    }

    /**
     * Gets wrapper.
     *
     * @return the wrapper
     */
    public JPanel getWrapper() {
        return this.panel;
    }

    /**
     * Initialize view.
     *
     * @param summerStart        the summer start
     * @param winterStart        the winter start
     * @param awaySpinnerModel   the away spinner model
     * @param dangerSpinnerModel the danger spinner model
     * @param roomNameLabels     the room name labels
     * @param roomTempLabels     the room temp labels
     * @param editRoomTempBtn    the edit room temp btn
     */
    // Assumes that there are no heating zones when first running the program (heating zones are not
    // persistant)
    public void initializeView(
            Date summerStart,
            Date winterStart,
            SpinnerNumberModel awaySpinnerModel,
            SpinnerNumberModel dangerSpinnerModel,
            ArrayList<JLabel> roomNameLabels,
            ArrayList<JLabel> roomTempLabels,
            ArrayList<JButton> editRoomTempBtn) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd");
        this.summerStartLabel.setText(dateFormatter.format(summerStart));
        this.winterStartLabel.setText(dateFormatter.format(winterStart));
        awayTempSpinner.setModel(awaySpinnerModel);
        dangerTempSpinner.setModel(dangerSpinnerModel);

        displayRoomSection(roomNameLabels, roomTempLabels, editRoomTempBtn);
    }

    private void displayRoomSection(
            ArrayList<JLabel> roomNameLabels,
            ArrayList<JLabel> roomTempLabels,
            ArrayList<JButton> editRoomTempBtn) {

        this.roomPanel.setLayout(new GridLayout(0, 3, 5, 5));

        for (int i = 0; i < roomNameLabels.size(); i++) {
            this.roomPanel.add(roomNameLabels.get(i));
            this.roomPanel.add(roomTempLabels.get(i));
            this.roomPanel.add(editRoomTempBtn.get(i));
        }
    }

    /**
     * Refresh current heat zones.
     *
     * @param zoneLabels      the zone labels
     * @param tempLabels      the temp labels
     * @param zoneEditButtons the zone edit buttons
     */
    public void refreshCurrentHeatZones(
            ArrayList<JLabel> zoneLabels,
            ArrayList<JLabel> tempLabels,
            ArrayList<JButton> zoneEditButtons) {
        this.heatingZonePanel.removeAll(); // clears panel
        this.heatingZonePanel.setLayout(new GridLayout(0, 3, 5, 5));

        for (int i = 0; i < zoneLabels.size(); i++) {
            this.heatingZonePanel.add(zoneLabels.get(i));
            this.heatingZonePanel.add(tempLabels.get(i));
            this.heatingZonePanel.add(zoneEditButtons.get(i));
        }

        this.heatingZonePanel.revalidate();
    }

    /**
     * Create heating zone listener.
     *
     * @param createHeatingZoneListener the create heating zone listener
     */
    public void createHeatingZoneListener(ActionListener createHeatingZoneListener) {
        this.createNewHeatingZoneButton.addActionListener(createHeatingZoneListener);
    }

    /**
     * Create summer change listener.
     *
     * @param summerChangeListener the summer change listener
     */
    public void createSummerChangeListener(ActionListener summerChangeListener) {
        this.summerChangeBtn.addActionListener(summerChangeListener);
    }

    /**
     * Create winter change listener.
     *
     * @param winterChangeListener the winter change listener
     */
    public void createWinterChangeListener(ActionListener winterChangeListener) {
        this.winterChangeBtn.addActionListener(winterChangeListener);
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
        panel.setShowYearButtons(false);
        datePicker.setShowYearButtons(false);
        datePickerWindow = new JFrame("Pick Date");

        datePickerWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        datePickerWindow.add(datePicker);
        datePickerWindow.pack();
        datePickerWindow.setLocationRelativeTo(null); // Center User Selection JFrame
        datePickerWindow.setVisible(true);
    }

    /**
     * Dispose date picker.
     */
    public void disposeDatePicker() {
        this.datePickerWindow.dispose();
    }

    /**
     * Gets list.
     *
     * @return the list
     */
    public JList<String> getList() {
        return this.heatingZones;
    }

    /**
     * Update summer start label.
     *
     * @param date the date
     */
    public void updateSummerStartLabel(String date) {
        this.summerStartLabel.setText(date);
    }

    /**
     * Update winter start label.
     *
     * @param date the date
     */
    public void updateWinterStartLabel(String date) {
        this.winterStartLabel.setText(date);
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
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainHeatWrap = new JPanel();
        mainHeatWrap.setLayout(new GridLayoutManager(12, 1, new Insets(5, 10, 5, 10), -1, -1));
        panel.add(mainHeatWrap, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        heatingZonePanel = new JPanel();
        heatingZonePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 15, 0), -1, -1));
        mainHeatWrap.add(heatingZonePanel, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        awayModeTempWrap = new JPanel();
        awayModeTempWrap.setLayout(new GridLayoutManager(2, 7, new Insets(10, 0, 15, 0), -1, -1));
        mainHeatWrap.add(awayModeTempWrap, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Away Mode Temp");
        awayModeTempWrap.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        awayTempSpinner = new JSpinner();
        awayModeTempWrap.add(awayTempSpinner, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Away Mode Danger Temperature");
        awayModeTempWrap.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dangerTempSpinner = new JSpinner();
        awayModeTempWrap.add(dangerTempSpinner, new GridConstraints(1, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        awayModeTempWrap.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        awayModeTempWrap.add(spacer2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        awayModeTempWrap.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        awayModeTempWrap.add(spacer4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        awayModeTempWrap.add(spacer5, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        awayModeTempWrap.add(spacer6, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        awayModeTempWrap.add(spacer7, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        awayModeTempWrap.add(spacer8, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        awayModeTempWrap.add(spacer9, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        awayModeTempWrap.add(spacer10, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        seasonPanel = new JPanel();
        seasonPanel.setLayout(new GridLayoutManager(2, 1, new Insets(10, 0, 0, 0), -1, -1));
        mainHeatWrap.add(seasonPanel, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        summerPanel = new JPanel();
        summerPanel.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        seasonPanel.add(summerPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$(null, Font.BOLD, 12, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Summer Start");
        summerPanel.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        summerStartLabel = new JLabel();
        Font summerStartLabelFont = this.$$$getFont$$$("Fira Code", Font.BOLD, 12, summerStartLabel.getFont());
        if (summerStartLabelFont != null) summerStartLabel.setFont(summerStartLabelFont);
        summerStartLabel.setText("Label");
        summerPanel.add(summerStartLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        summerChangeBtn = new JButton();
        summerChangeBtn.setText("Change");
        summerPanel.add(summerChangeBtn, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        summerPanel.add(spacer11, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        summerPanel.add(spacer12, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        winterPanel = new JPanel();
        winterPanel.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        seasonPanel.add(winterPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, Font.BOLD, 12, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Winter Start");
        winterPanel.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        winterStartLabel = new JLabel();
        Font winterStartLabelFont = this.$$$getFont$$$("Fira Code", Font.BOLD, 12, winterStartLabel.getFont());
        if (winterStartLabelFont != null) winterStartLabel.setFont(winterStartLabelFont);
        winterStartLabel.setText("Label");
        winterPanel.add(winterStartLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        winterChangeBtn = new JButton();
        winterChangeBtn.setText("Change");
        winterPanel.add(winterChangeBtn, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        winterPanel.add(spacer13, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        winterPanel.add(spacer14, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        mainHeatWrap.add(separator1, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        mainHeatWrap.add(separator2, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$(null, Font.BOLD, 16, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Heating Zones");
        mainHeatWrap.add(label5, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$(null, Font.BOLD, 16, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Season Start Dates");
        mainHeatWrap.add(label6, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, Font.BOLD, 16, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("Away Mode Configuration");
        mainHeatWrap.add(label7, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(10, 0, 0, 0), -1, -1));
        mainHeatWrap.add(panel1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createNewHeatingZoneButton = new JButton();
        createNewHeatingZoneButton.setText("Create new heating zone");
        panel1.add(createNewHeatingZoneButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$(null, Font.BOLD, 16, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Room Temperatures");
        mainHeatWrap.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        roomPanel = new JPanel();
        roomPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainHeatWrap.add(roomPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JSeparator separator3 = new JSeparator();
        mainHeatWrap.add(separator3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        return panel;
    }
}
