package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import Enums.ProfileType;
import Models.EnvironmentModel;
import Models.Room;
import Models.UserProfileModel;
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
  private JButton toggleSimulationButton;
  /**
   * The Edit action button.
   */
  private JButton editSimulationButton;
  /**
   * The Tabbed pane 1.
   */
  private JTabbedPane tabbedPane1;
  /**
   * The P 1.
   */
  private JPanel smartModulesPanel;
  /**
   * The Sp 1.
   */
  private JScrollPane sp1;
  /**
   * The Sp 2.
   */
  private JScrollPane sp2;
  /**
   * The Tab 1.
   */
  private JPanel wrapperPanel;

  /**
   * The User profile drop down.
   */
  private JComboBox userProfileDropDown; // Current User Profile

  /**
   * The User room drop down.
   */
  private JComboBox userRoomDropDown; // Current User Profile's Room

  /**
   * The Date field.
   */
  private JLabel dateField; // Date field

  /**
   * The Time field.
   */
  private JLabel timeField; // Time Field

  /**
   * The Temp spinner.
   */
  private JLabel tempLabel; // temperature spinner

  /**
   * The Time speed.
   */
  private JLabel timeSpeed;
  private SecurityView shp;
  private CoreView coreView;
  private JPanel shpTab;

  /**
   * The Confirm time speed.
   */
  private JButton confirmTimeSpeed;

  /**
   * The Date picker.
   */
  private JDatePickerImpl datePicker;

  /**
   * The Time spinner.
   */
  private JSpinner time_spinner;

  /**
   * The Time confirm.
   */
  private JButton timeConfirm;

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


    JPanel jp = this.smartModulesPanel;
    this.setContentPane(jp);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
    Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();

    this.setSize((int) rect.getWidth()/2, (int) rect.getHeight());
    this.setLocation(0,0);
    this.setVisible(true);

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


  /**
   * Since CoreView is initialized by The IntelliJ Swing Builder, we need to use that instance.
   * @return returns an instance of CoreView (SHC)
   */
  public CoreView getSHC(){
    return this.coreView;
  }

  @Override
  public void repaint() {
    setSHPVisibility();
    setSHCVisibility();

    super.repaint();
  }

  /**
   * Adds the specified {@link UserProfileModel} to the Dashboard User Dropdown Menu
   *
   * @param profile The {@link UserProfileModel} to add to the User Dropdown
   * @param isCurrentUser boolean indicating whether a current user is set in the {@link
   *     Models.EnvironmentModel}
   */
  public void addProfileToDropDown(UserProfileModel profile, boolean isCurrentUser) {
    this.userProfileDropDown.addItem(profile);
    if (isCurrentUser) this.userProfileDropDown.setSelectedItem(profile);
  }

  /**
   * Adds the specified {@link Room} to the Room Selection Dropdown
   *
   * @param room The {@link Room} to add to the Room Dropdown
   * @param isCurrentRoom boolean indicating whether the currently selected {@link UserProfileModel}
   *     is in the passed in {@link Room}
   */
  public void addRoomToDropDown(Room room, boolean isCurrentRoom) {
    this.userRoomDropDown.addItem(room);
    if (isCurrentRoom) this.userRoomDropDown.setSelectedItem(room);
  }

  /**
   * Method used to pass on the {@link JComboBox} listener responsibility to its caller.
   *
   * @param listenForUserDropDown Listener for the dropdown button that selects between different
   *     {@link UserProfileModel}
   */
  public void addUserDropDownListener(ActionListener listenForUserDropDown) {
    this.userProfileDropDown.addActionListener(listenForUserDropDown);
  }

  /**
   * Method used to pass on the {@link JComboBox} listener responsibility to its caller.
   *
   * @param listenForUserRoomDropDown Listener for the dropdown button that between different {@link
   *     Room} for the currently selected {@link UserProfileModel}
   */
  public void addUserRoomDropDownListener(ActionListener listenForUserRoomDropDown) {
    this.userRoomDropDown.addActionListener(listenForUserRoomDropDown);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForSimulationStart the listen for simulation start
   */
  public void addSimulationToggleListener(ActionListener listenForSimulationStart) {
    this.toggleSimulationButton.addActionListener(listenForSimulationStart);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForEditSimulation the listen for window obstruction
   */
  public void addEditSimulationListener(ActionListener listenForEditSimulation) {
    this.editSimulationButton.addActionListener(listenForEditSimulation);
  }

  /**
   * Sets Room Dropdown menu to the {@link Room} specified by the passed in index value
   *
   * @param index integer value representing the index position of the {@link Room} within the
   *     Dropdown
   */
  public void setRoomDropDownIndex(int index) {
    this.userRoomDropDown.setSelectedIndex(index);
  }

  /**
   * Sets Room Dropdown menu to the specified {@link Room} NOTE: This only works if the passed in
   * {@link Room} object has the same reference as the {@link Room} object that is contained within
   * the Dropdown menu
   *
   * @param room {@link Room} object to set the Dropdown menu
   */
  public void setRoomDropDownItem(Room room) {
    this.userRoomDropDown.setSelectedItem(room);
  }

  public void refreshDash() {
    this.repaint();
  }

  public void refreshDash(String dateString, String timeString, int temp, int timerDelay) {
    this.dateField.setText(dateString);
    this.timeField.setText(timeString);
    this.tempLabel.setText(temp + "°C");
    this.timeSpeed.setText(1000 / timerDelay + " X");

    // This will trigger the userProfileDropDown ActionListener and will update the room of the
    // current user on the DASH
    this.userProfileDropDown.setSelectedIndex(
            this.userProfileDropDown.getSelectedIndex());
  }

  /**
   * Toggles the simulation text.
   *
   * @param text the text
   */
  public void changeSimulationToggleText(String text) {
    this.toggleSimulationButton.setText(text);
  }

  public void setTimeField(String time) {
    this.timeField.setText(time);
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


}
