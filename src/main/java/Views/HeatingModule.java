package Views;


import Controllers.EnvironmentController;
import Controllers.HeatingController;
import Models.Context;
import Models.EnvironmentModel;
import Models.HeatingModel;
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
import java.util.Date;
import java.util.Properties;

public class HeatingModule extends JPanel {
    HeatingController hc;
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
    private JList<String> heatingZones;
    private JFrame datePickerWindow;

    public HeatingModule() {
        HeatingModel model = new HeatingModel();
        hc = new HeatingController(model, this);
        roomsInHouse = Context.getHouse().getRooms().toArray(roomsInHouse);

        DefaultListModel<String> lm = new DefaultListModel<>();
        heatingZones = new JList<String>(lm);
        awayTempSpinner.setModel(model.getAwayTempSpinner());
        dangerTempSpinner.setModel(model.getDangerTempSpinner());

    }

    public void createHeatingZoneListener(ActionListener createHeatingZoneListener) {
        this.createNewHeatingZoneButton.addActionListener(createHeatingZoneListener);
    }

    public void createSummerChangeListener(ActionListener summerChangeListener) {
        this.summerChangeBtn.addActionListener(summerChangeListener);
    }

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

    public void disposeDatePicker() {
        this.datePickerWindow.dispose();
    }

    public JList<String> getList() {
        return this.heatingZones;
    }


    public void initializeView(Date summerStart, Date winterStart) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd");
        this.summerStartLabel.setText(dateFormatter.format(summerStart));
        this.winterStartLabel.setText(dateFormatter.format(winterStart));
    }

    public void updateSummerStartLabel(String date) {
        this.summerStartLabel.setText(date);
    }

    public void updateWinterStartLabel(String date) {
        this.winterStartLabel.setText(date);
    }

}
