package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.jdatepicker.impl.JDatePickerImpl;

/** The type Dash. */
public class Dash extends JFrame {
  /** The Stop simulation button. */
  JButton toggleSimulationButton;
  /** The Edit action button. */
  JButton toggleObstructionButton;
  /** The Tabbed pane 1. */
  JTabbedPane tabbedPane1;
  /** The P 1. */
  JPanel p1;
  /** The Sp 1. */
  JScrollPane sp1;
  /** The Sp 2. */
  JScrollPane sp2;
  /** The Tab 2. */
  JPanel Tab2;
  /** The Tab 1. */
  JPanel Tab1;

  /** The User profile drop down. */
  JComboBox userProfileDropDown; // Current User Profile

  /** The User room drop down. */
  JComboBox userRoomDropDown; // Current User Profile's Room

  /** The Change date. */
  JButton changeDate;

  /** The Change time. */
  JButton changeTime;

  /** The Date field. */
  JFormattedTextField dateField; // Date field

  /** The Time field. */
  JFormattedTextField timeField; // Time Field

  /** The Temp spinner. */
  JSpinner tempSpinner; // temperature spinner

  /** The Date picker. */
  JDatePickerImpl datePicker;

  /** The Time spinner. */
  JSpinner time_spinner;

  /** The Time confirm. */
  JButton timeConfirm;

  /**
   * Instantiates a new Dashboard frame.
   *
   * @param temp the temp
   * @param date the date
   * @param time the time
   */
  public Dash(int temp, String date, String time) {
    tempSpinner.setValue(temp);
    dateField.setValue(date);
    timeField.setValue(time);
    timeConfirm = new JButton("OK");
    tabbedPane1.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
          }
        });
  }
}
