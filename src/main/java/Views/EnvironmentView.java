package Views;

import Enums.ProfileType;
import Models.Room;
import Models.UserProfileModel;
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

/**
 * EnvironmentView is the visual representation of the entire system and is the module that
 * interacts directly with the user. Any operation requested by the user is then passed on to the
 * {@link Controllers.EnvironmentController}
 */
public class EnvironmentView extends JFrame {

  private final JButton userButton;
  private final JButton locationButton;
  private final JButton enterSimButton;
  private final JButton createUserButton;
  private JButton confirmUserCreateBtn;

  private JFrame createUser;
  private JTextField profileName;
  private JComboBox<ProfileType> profileType;

  private Dash dashboard;

  /** Initializes the Environment View */
  public EnvironmentView() {
    userButton = new JButton("1. Select User Profile");
    locationButton = new JButton("2. Select Location");
    enterSimButton = new JButton("3. Enter Simulation");
    Box box1 = Box.createVerticalBox();
    box1.add(userButton);
    box1.add(locationButton);
    box1.add(enterSimButton);
    box1.add(new JSeparator());
    box1.add(new JLabel("Configuration"));

    this.createUserButton = new JButton("Create User");
    box1.add(this.createUserButton);

    this.add(box1);
    setLayout(new GridLayout(1, 1));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(250, 250);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForConfirmUserCreate Listener for the button that confirms user creation
   */
  public void addConfirmUserCreateListener(ActionListener listenForConfirmUserCreate) {
    this.confirmUserCreateBtn.addActionListener(listenForConfirmUserCreate);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForCreateUser Listener for the button that starts the user creation process
   */
  public void addCreateUserListener(ActionListener listenForCreateUser) {
    this.createUserButton.addActionListener(listenForCreateUser);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForUser Listener for the button that selects the {@link UserProfileModel} before
   *     starting the simulation
   */
  public void addUserListener(ActionListener listenForUser) {
    this.userButton.addActionListener(listenForUser);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForLocation Listener for the button that selects the selected {@link
   *     UserProfileModel} location before starting the simulation
   */
  public void addLocationListener(ActionListener listenForLocation) {
    this.locationButton.addActionListener(listenForLocation);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForSim Listener for the button that starts the simulation
   */
  public void addSimulatorListener(ActionListener listenForSim) {
    this.enterSimButton.addActionListener(listenForSim);
  }

  /**
   * Method used to pass on the {@link JComboBox} listener responsibility to its caller.
   *
   * @param listenForUserDropDown Listener for the dropdown button that selects between different
   *     {@link UserProfileModel}
   */
  public void addUserDropDownListener(ActionListener listenForUserDropDown) {
    this.dashboard.userProfileDropDown.addActionListener(listenForUserDropDown);
  }

  /**
   * Method used to pass on the {@link JComboBox} listener responsibility to its caller.
   *
   * @param listenForUserRoomDropDown Listener for the dropdown button that between different {@link
   *     Room} for the currently selected {@link UserProfileModel}
   */
  public void addUserRoomDropDownListener(ActionListener listenForUserRoomDropDown) {
    this.dashboard.userRoomDropDown.addActionListener(listenForUserRoomDropDown);
  }

  /**
   * Method used to pass on the {@link JSpinner} listener responsibility to its caller.
   *
   * @param listenForTempSpinner Listener for the button that starts the user creation process
   */
  public void addTempSpinnerListener(ChangeListener listenForTempSpinner) {
    this.dashboard.tempSpinner.addChangeListener(listenForTempSpinner);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForDateChange Listener for the button that starts a Date Change
   */
  public void addChangeDateListener(ActionListener listenForDateChange) {
    this.dashboard.changeDate.addActionListener(listenForDateChange);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForTimeChange Listener for the button that starts a Time change
   */
  public void addChangeTimeListener(ActionListener listenForTimeChange) {
    this.dashboard.changeTime.addActionListener(listenForTimeChange);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForTimeConfirm Listener for the button that applies the selected time
   */
  public void addConfirmTimeListener(ActionListener listenForTimeConfirm) {
    this.dashboard.timeConfirm.addActionListener(listenForTimeConfirm);
  }

  public void addSimulationToggleListener(ActionListener listenForSimulationStart) {
    this.dashboard.toggleSimulationButton.addActionListener(listenForSimulationStart);
  }

  public void addObstructionToggleListener(ActionListener listenForWindowObstruction) {
    this.dashboard.toggleObstructionButton.addActionListener(listenForWindowObstruction);
  }

  /**
   * Creates Simulation Dashboard and uses the passed in values to set initial values
   *
   * @param temp The outside temperature of the simulated environment
   * @param date The date of the simulated environment
   * @param time The time of the simulated environment
   */
  public void createDash(int temp, String date, String time) {
    JFrame frame = new JFrame("Dashboard");
    this.dashboard = new Dash(temp, date, time);
    JPanel jp = this.dashboard.p1;
    frame.setContentPane(jp);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * Adds the specified {@link UserProfileModel} to the Dashboard User Dropdown Menu
   *
   * @param profile The {@link UserProfileModel} to add to the User Dropdown
   * @param isCurrentUser boolean indicating whether a current user is set in the {@link
   *     Models.EnvironmentModel}
   */
  public void addProfileToDropDown(UserProfileModel profile, boolean isCurrentUser) {
    dashboard.userProfileDropDown.addItem(profile);
    if (isCurrentUser) dashboard.userProfileDropDown.setSelectedItem(profile);
  }

  /**
   * Adds the specified {@link Room} to the Room Selection Dropdown
   *
   * @param room The {@link Room} to add to the Room Dropdown
   * @param isCurrentRoom boolean indicating whether the currently selected {@link UserProfileModel}
   *     is in the passed in {@link Room}
   */
  public void addRoomToDropDown(Room room, boolean isCurrentRoom) {
    dashboard.userRoomDropDown.addItem(room);
    if (isCurrentRoom) dashboard.userRoomDropDown.setSelectedItem(room);
  }

  /**
   * Sets Room Dropdown menu to the {@link Room} specified by the passed in index value
   *
   * @param index integer value representing the index position of the {@link Room} within the
   *     Dropdown
   */
  public void setRoomDropDownIndex(int index) {
    this.dashboard.userRoomDropDown.setSelectedIndex(index);
  }

  /**
   * Sets Room Dropdown menu to the specified {@link Room} NOTE: This only works if the passed in
   * {@link Room} object has the same reference as the {@link Room} object that is contained within
   * the Dropdown menu
   *
   * @param room {@link Room} object to set the Dropdown menu
   */
  public void setRoomDropDownItem(Room room) {
    this.dashboard.userRoomDropDown.setSelectedItem(room);
  }

  public void ChangeTime(Date currentDate) {
    this.dashboard.setLayout(new GridLayout(2, 1, 3, 3));

    SpinnerDateModel sm = new SpinnerDateModel(currentDate, null, null, Calendar.HOUR_OF_DAY);

    dashboard.time_spinner = new JSpinner(sm);

    JSpinner.DateEditor te = new JSpinner.DateEditor(dashboard.time_spinner, "hh:mm:ss a");
    dashboard.time_spinner.setEditor(te);

    this.dashboard.add(dashboard.time_spinner);

    this.dashboard.add(dashboard.timeConfirm);
    this.dashboard.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
    this.dashboard.pack();
    this.dashboard.setVisible(true);
  }

  /**
   * Provides an interface that allows the Simulator user to change the Date
   *
   * @param formatter Object that performs the conversion between the user selecting a date on the
   *     UI via a DatePicker to a useable String format to be stored in the {@link
   *     Models.EnvironmentModel}. The core logic of {@param formatter} is found in {@link
   *     Controllers.EnvironmentController}
   */
  public void ChangeDate(JFormattedTextField.AbstractFormatter formatter) {
    SqlDateModel model = new SqlDateModel();
    Properties p = new Properties();
    p.put("text.today", "Today");
    p.put("text.month", "Month");
    p.put("text.year", "Year");
    JDatePanelImpl panel = new JDatePanelImpl(model, p);
    dashboard.datePicker = new JDatePickerImpl(panel, formatter);
    this.dashboard.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
    this.dashboard.add(dashboard.datePicker);
    this.dashboard.pack();
    this.dashboard.setVisible(true);
  }

  /**
   * Gets the value from the {@link JSpinner} module used to change the Time of the simulated
   * environment
   *
   * @return Value of the {@link JSpinner} that needs to be casted to a useable format
   */
  public Object getTimeSpinnerVal() {
    return dashboard.time_spinner.getValue();
  }

  /**
   * Sets the Time field of the simulator
   *
   * @param time String representation of the new Time to set
   */
  public void setTimeField(String time) {
    this.dashboard.timeField.setValue(time);
  }

  /**
   * Sets Date field of the simulator
   *
   * @param date String representation of the new Date to set
   */
  public void setDateField(String date) {
    this.dashboard.dateField.setValue(date);
  }

  /**
   * Gets the outside temperature of the simulated environment
   *
   * @return The currently set temperature as an integer
   */
  public int getTemperatureFromSpinner() {
    return (int) dashboard.tempSpinner.getValue();
  }

  /**
   * Creates an interface that allows the Simulator user to create new profiles The user needs to
   * specify the new profile's name as well as the {@link profileType}. The latter is displayed
   */
  public void userCreationWindow() {
    this.createUser = new JFrame("Create a new User");
    GridLayout grid = new GridLayout(4, 2, 2, 2);
    createUser.setLayout(grid);

    this.profileName = new JTextField();
    this.profileType = new JComboBox<ProfileType>(ProfileType.values());
    this.confirmUserCreateBtn = new JButton("Create");

    createUser.add(new JLabel("Profile Name: "));
    createUser.add(profileName);
    createUser.add(new JLabel("Profile Type"));
    createUser.add(profileType);
    createUser.add(new JLabel()); // Empty cell
    createUser.add(new JLabel()); // Empty cell
    createUser.add(new JLabel()); // Empty cell
    createUser.add(this.confirmUserCreateBtn);

    createUser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    createUser.pack();
    createUser.setLocationRelativeTo(null); // Center User Selection JFrame
    createUser.setVisible(true);
  }

  /**
   * Called after a user confirms the create of a user. This method creates a new {@link
   * UserProfileModel} with the completed fields
   *
   * @return A {@link UserProfileModel} containing the attributes specified in {@link
   *     #userCreationWindow()}
   */
  public UserProfileModel getNewlyCreatedUser() {
    return new UserProfileModel(
        (ProfileType) this.profileType.getSelectedItem(), this.profileName.getText(), -1);
  }

  public void changeSimulationToggleText(String test) {
    dashboard.toggleSimulationButton.setText(test);
  }

  public void changeWindowsObstructedToggleText(String text) {
    dashboard.toggleObstructionButton.setText(text);
  }
  /** Used to clean up UI after the user is done creating a {@link UserProfileModel} */
  public void disposeCreateUser() {
    this.createUser.dispose();
  }

  /** Used to clean up UI after the user is done using the Calendar Date Picker */
  public void removeDateComponentPicker() {
    this.dashboard.remove(dashboard.datePicker);
  }

  /** Used to clean up UI after the user is done using the Time Picker */
  public void removeTimeComponentPicker() {
    this.dashboard.remove(dashboard.time_spinner);
    this.dashboard.remove(dashboard.timeConfirm);
  }

  /** Destroys the Simulator Dashboard when called */
  public void disposeDash() {
    this.dashboard.dispose();
  }
}
