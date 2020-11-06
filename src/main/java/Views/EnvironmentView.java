package Views;

import Enums.ProfileType;
import Models.Room;
import Models.UserProfileModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
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
   * @param listenForSimulationStart the listen for simulation start
   */
  public void addSimulationToggleListener(ActionListener listenForSimulationStart) {
    this.dashboard.toggleSimulationButton.addActionListener(listenForSimulationStart);
  }

  /**
   * Method used to pass on the {@link JButton} listener responsibility to its caller.
   *
   * @param listenForEditSimulation the listen for window obstruction
   */
  public void addEditSimulationListener(ActionListener listenForEditSimulation) {
    this.dashboard.editSimulationButton.addActionListener(listenForEditSimulation);
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
   * specify the new profile's name as well as the {@link 'profileType'}. The latter is displayed
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
   * UserProfileModel}* with the completed fields
   *
   * @return A {@link UserProfileModel} containing the attributes specified in {@link
   *     #userCreationWindow()}
   */
  public UserProfileModel getNewlyCreatedUser() {
    return new UserProfileModel(
        (ProfileType) this.profileType.getSelectedItem(), this.profileName.getText(), -1);
  }

  /**
   * Toggles the simulation text.
   *
   * @param text the text
   */
  public void changeSimulationToggleText(String text) {
    dashboard.toggleSimulationButton.setText(text);
  }

  /**
   * Toggles the obstruction text.
   *
   * @param text the text
   */
  public void changeWindowsObstructedToggleText(String text) {
    dashboard.editSimulationButton.setText(text);
  }


  /** Used to clean up UI after the user is done creating a {@link UserProfileModel} */
  public void disposeCreateUser() {
    this.createUser.dispose();
  }


  /** Destroys the Simulator Dashboard when called */
  public void disposeDash() {
    this.dashboard.dispose();
  }


  public void refreshDash(String dateString, String timeString) {
    this.dashboard.dateField.setText(dateString);
    this.dashboard.timeField.setText(timeString);

    //This will trigger the userProfileDropDown ActionListener and will update the room of the current user on the DASH
    this.dashboard.userProfileDropDown.setSelectedIndex(this.dashboard.userProfileDropDown.getSelectedIndex());
  }
  public void addconfirmTimeSpeedListener(ActionListener changeTimeSpeed) {
    this.dashboard.confirmTimeSpeed.addActionListener(changeTimeSpeed);
  }

  public String getTimeSpeed(){
    return dashboard.timeSpeed.getSelectedItem().toString();

  }

  public void setTimeField(String time){
    this.dashboard.timeField.setText(time);
  }
}
