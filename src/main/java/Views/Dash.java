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

  JComboBox userProfileDropDown; // Current User Profile
  JComboBox userRoomDropDown; // Current User Profile's Room
  JButton changeDate;
  JButton changeTime;
  JFormattedTextField dateField; // Date field
  JFormattedTextField timeField; // Time Field
  JSpinner tempSpinner; // temperature spinner
  JDatePickerImpl datePicker;
  JSpinner time_spinner;
  JButton timeConfirm;

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
