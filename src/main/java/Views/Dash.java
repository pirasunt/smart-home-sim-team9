package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import Enums.ProfileType;
import Models.EnvironmentModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jdatepicker.impl.JDatePickerImpl;

/**
 * The type Dash.
 */
public class Dash extends JFrame {
  /**
   * The Stop simulation button.
   */
  JButton toggleSimulationButton;
  /**
   * The Edit action button.
   */
  JButton editSimulationButton;
  /**
   * The Tabbed pane 1.
   */
  JTabbedPane tabbedPane1;
  /**
   * The P 1.
   */
  JPanel p1;
  /**
   * The Sp 1.
   */
  JScrollPane sp1;
  /**
   * The Sp 2.
   */
  JScrollPane sp2;
  /**
   * The Tab 1.
   */
  JPanel wrapperPanel;

  /**
   * The User profile drop down.
   */
  JComboBox userProfileDropDown; // Current User Profile

  /**
   * The User room drop down.
   */
  JComboBox userRoomDropDown; // Current User Profile's Room

  /**
   * The Date field.
   */
  JLabel dateField; // Date field

  /**
   * The Time field.
   */
  JLabel timeField; // Time Field

  /**
   * The Temp spinner.
   */
  JLabel tempLabel; // temperature spinner

  /**
   * The Time speed.
   */
  JLabel timeSpeed;
  private SecurityView shp;
  private CoreView coreView;
  private JPanel shpTab;

  /**
   * The Confirm time speed.
   */
  JButton confirmTimeSpeed;

  /**
   * The Date picker.
   */
  JDatePickerImpl datePicker;

  /**
   * The Time spinner.
   */
  JSpinner time_spinner;

  /**
   * The Time confirm.
   */
  JButton timeConfirm;

  /**
   * Instantiates a new Dashboard frame.
   *
   * @param temp  the temp
   * @param date  the date
   * @param time  the time
   * @param delay the delay
   */
  public Dash(int temp, String date, String time, int delay) {
    tempLabel.setText(temp + "°C");
    dateField.setText(date);
    timeField.setText(time);
    timeSpeed.setText(1000 / delay + " X");
    timeConfirm = new JButton("OK");
    tabbedPane1.addMouseListener(
            new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
              }
            });

    setSHPVisibility();
    setSHCVisibility();
  }

  @Override
  public void repaint() {
    setSHPVisibility();
    setSHCVisibility();

    super.repaint();
  }


  private void setSHCVisibility() {
    if (EnvironmentModel.getCurrentUser().getProfileType() == ProfileType.STRANGER) {
      tabbedPane1.setEnabledAt(0, false);
      coreView.getWrapper().setVisible(false);
    } else {
      tabbedPane1.setEnabledAt(0, true);
      coreView.getWrapper().setVisible(true);
    }
  }

  private void setSHPVisibility() {
    if (EnvironmentModel.getCurrentUser().getProfileType() != ProfileType.ADULT) {
      tabbedPane1.setEnabledAt(1, false);
      shp.getWrapper().setVisible(false);
    } else
      tabbedPane1.setEnabledAt(1, true);
      shp.getWrapper().setVisible(true);
  }

  CoreView getCoreView() {
    return this.coreView;
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
  }

}
