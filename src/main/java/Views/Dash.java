package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.Security;

import Controllers.SecurityController;
import Models.SecurityModel;
import org.jdatepicker.impl.JDatePickerImpl;
import Tools.CustomTimer;


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

  /** The User profile drop down. */
  JComboBox userProfileDropDown; // Current User Profile

  /** The User room drop down. */
  JComboBox userRoomDropDown; // Current User Profile's Room

  /** The Change date. */
  JButton changeDate;

  /** The Change time. */
  JButton changeTime;

  /** The Date field. */
  JLabel dateField; // Date field

  /** The Time field. */
  JLabel timeField; // Time Field

  /** The Temp spinner. */
  JSpinner tempSpinner; // temperature spinner
  JButton confirmTemp;
  private JComboBox timeSpeed;
  private JButton confirmTimeSpeed;

  /** The Date picker. */
  JDatePickerImpl datePicker;

  /** The Time spinner. */
  JSpinner time_spinner;

  /** The Time confirm. */
  JButton timeConfirm;

  private int hour;
  private int minute;
  private int second;
  private String amPM;
  private boolean simRunning = false;

  /**
   * Instantiates a new Dashboard frame.
   *
   * @param temp the temp
   * @param date the date
   * @param time the time
   */
  public Dash(int temp, String date, String time) {
    tempSpinner.setValue(temp);
    dateField.setText(date);
    timeField.setText(time);
    timeConfirm = new JButton("OK");
    tabbedPane1.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
          }
        });


    Timer timer = new Timer(1000, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String hourString;
        String minuteString;
        String secondString;

        second++;
        if(second > 59) {
          minute++;
          second = 0;
        }
        if(minute > 59) {
          hour++;
          minute = 0;
        }
        if(hour > 12){
          hour = 1;
          if(amPM == "AM")
            amPM = "PM";
          else
            amPM = "AM";
        }

        if(hour < 10)
          hourString = "0" + hour;
        else
          hourString = String.valueOf(hour);

        if(minute < 10)
          minuteString = "0" + minute;
        else
          minuteString = String.valueOf(minute);

        if(second < 10)
          secondString = "0" + second;
        else
          secondString = String.valueOf(second);

        String time = hourString + ":" + minuteString + ":" + secondString + " " + amPM;
        timeField.setText(time);
      }
    });


    toggleSimulationButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(simRunning == true) {
          timer.stop();
          simRunning = false;
        }
        else {
          hour = Integer.parseInt(timeField.getText().substring(0,2));
          minute = Integer.parseInt(timeField.getText().substring(3,5));
          second = Integer.parseInt(timeField.getText().substring(6,8));
          amPM = timeField.getText().substring(8);
          timer.restart();
          simRunning = true;
        }
      }
    });
    confirmTimeSpeed.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (timeSpeed.getSelectedItem().toString() == "10x")
          timer.setDelay(100);
        else if (timeSpeed.getSelectedItem().toString() == "100x")
          timer.setDelay(10);
        else
          timer.setDelay(1000);
      }
    });
  }
}
