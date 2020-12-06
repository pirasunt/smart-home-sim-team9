package Views;

import Controllers.EnvironmentController;
import Models.Context;
import Models.EnvironmentModel;
import Models.Room;
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

/** The type Heating module. */
public class HeatingModule {
  /** The Rooms in house. */
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

  /** Instantiates a new Heating module. */
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
   * @param summerStart the summer start
   * @param winterStart the winter start
   * @param awaySpinnerModel the away spinner model
   * @param dangerSpinnerModel the danger spinner model
   * @param roomNameLabels the room name labels
   * @param roomTempLabels the room temp labels
   * @param editRoomTempBtn the edit room temp btn
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
   * @param zoneLabels the zone labels
   * @param tempLabels the temp labels
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
   *     UI via a DatePicker to a usable String format to be stored in the {@link EnvironmentModel}.
   *     The core logic of formatter is found in {@link EnvironmentController}
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

  /** Dispose date picker. */
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
}
