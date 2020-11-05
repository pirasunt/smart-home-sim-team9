package Models;

import Custom.NonExistantUserProfileException;
import Enums.ProfileType;
import Views.Console;
import Views.HouseGraphic;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * EnvironmentModel represents the data structure of the system. The {@link
 * Controllers.EnvironmentController}** manipulates the data within this class.
 */
public class EnvironmentModel {
  private static EnvironmentModel instance = null;
  private final Calendar currentCalObj;
  private final ArrayList<UserProfileModel> userProfileModelList;
  private final House house;
  private final HouseGraphic houseGraphic;
  private int outsideTemperature;
  private UserProfileModel currentUser;
  private boolean simulationRunning = false;
  private boolean windowsObstructed = false;

  private EnvironmentModel(
      House h,
      HouseGraphic hg,
      int temperature,
      Calendar cal,
      ArrayList<UserProfileModel> profileList) {
    this.house = h;
    this.houseGraphic = hg;
    this.outsideTemperature = temperature;
    this.currentCalObj = cal;
    this.userProfileModelList = profileList;
    this.currentUser = null;
  }

  private EnvironmentModel(House h, HouseGraphic hg, ArrayList<UserProfileModel> profileList) {
    this(h, hg, 21, new GregorianCalendar(), profileList);
  }

  /**
   * Initializes an EnvironmentModel and ensures that only 1 instance of this class exists during
   * runtime
   *
   * @param h An instance of {@link House} that was initialized with an XML
   * @param hg An instance of {@link HouseGraphic} that is being displayed and must be refreshed
   *     when changes occur
   * @param profiles A list of initial {@link UserProfileModel} that will be initialized and
   *     available with the simulation.
   * @return An Initialized Singleton of EnvironmentModel
   */
  public static EnvironmentModel createSimulation(
      House h, HouseGraphic hg, UserProfileModel... profiles) {
    if (instance == null) {
      ArrayList<UserProfileModel> profileList = new ArrayList<UserProfileModel>();
      for (UserProfileModel profile : profiles) {
        profileList.add(profile);
      }
      instance = new EnvironmentModel(h, hg, profileList);

    } else {
      System.err.println(
          "There already exists an instance of environment. Returning that instance");
    }

    return instance;
  }

  /** Used for testing */
  public static void resetInstance() {
    instance = null;
  }

  /**
   * Used to set the outside temperature in the simulation
   *
   * @param newTemp is the new temperature
   */
  public void setTemperature(int newTemp) {
    this.outsideTemperature = newTemp;
  }

  /**
   * Returns a deep copy of all the registered user profiles
   *
   * @return UserProfile Array of all registered user profile in the environment
   */
  public UserProfileModel[] getAllUserProfiles() {
    UserProfileModel[] up = new UserProfileModel[this.userProfileModelList.size()];

    for (int i = 0; i < up.length; i++) {
      up[i] = new UserProfileModel(this.userProfileModelList.get(i));
    }
    return up;
  }

  /**
   * Modifies the room location of the specified user. NOTE: This method only modifies the location
   * on the {@link UserProfileModel} that is contained within the internal {@link ArrayList} of this
   * class and NOT the reference that is passed in.
   *
   * @param profile The {@link UserProfileModel} object that needs its location modified
   * @param room The {@link Room} object that represents the new location of the user profile
   */
  public void modifyProfileLocation(UserProfileModel profile, Room room) {

    try {
      updateProfileEntry(profile.modifyLocation(room.getId()));
      Console.print(
          "Set Room to: '"
              + room.getName()
              + "' for user "
              + profile.getName()
              + "/"
              + profile.getProfileType()
              + "\n");
    } catch (NonExistantUserProfileException e) {
      System.err.println(e.getMessage()); // TODO: Return some sort of error window in the future
    }

    houseGraphic.repaint();
  }

  /**
   * Modifies the profile name of the specified user. NOTE: This method only modifies the profile
   * name on the {@link UserProfileModel} that is contained within the internal list of this class
   * and NOT the reference that is passed in.
   *
   * @param profile The {@link UserProfileModel} object that needs its name modified
   * @param newName The new name of the user profile
   */
  public void editProfileName(UserProfileModel profile, String newName) {

    try {
      updateProfileEntry(profile.modifyName(newName));
    } catch (NonExistantUserProfileException e) {
      System.err.println(e.getMessage());
    }

    houseGraphic.repaint();
  }

  /**
   * Returns a deep copy of the currently selected user on the simulation
   *
   * @return Deep copy of currently selected user.
   */
  public UserProfileModel getCurrentUser() {
    return new UserProfileModel(this.currentUser);
  }

  /**
   * Sets current user.
   *
   * @param currentUser the current user
   */
  public void setCurrentUser(UserProfileModel currentUser) {
    this.currentUser = new UserProfileModel(currentUser);
    Console.print(
        "Current user has been set to "
            + this.currentUser.getName()
            + "/"
            + this.currentUser.getProfileType());
  }

  /**
   * Get a copy of a {@link UserProfileModel} with the specified {@link UUID}
   *
   * @param id The UUID of the {@link UserProfileModel} that one is looking for
   * @return The {@link UserProfileModel} with the specified UUID id
   * @throws NonExistantUserProfileException thrown when the specified {@link UUID} is of a user
   *     that does not exist
   */
  public UserProfileModel getUserByID(UUID id) throws NonExistantUserProfileException {
    UserProfileModel temp = null;
    for (int i = 0; i < this.userProfileModelList.size(); i++) {
      if (id == this.userProfileModelList.get(i).getProfileID()) {
        temp = new UserProfileModel(this.userProfileModelList.get(i));
        break;
      }
    }
    if (temp == null) {
      throw new NonExistantUserProfileException(
          "UserProfile " + id + "does not exist or has been deleted");
    }

    return temp;
  }

  private void updateProfileEntry(UserProfileModel updatedProfile)
      throws NonExistantUserProfileException {

    boolean existingProfile = false;
    int index = -1;
    for (int i = 0; i < this.userProfileModelList.size(); i++) {
      if (this.userProfileModelList.get(i).getProfileID() == updatedProfile.getProfileID()) {
        existingProfile = true;
        index = i;
      }
    }

    if (existingProfile && index >= 0) {
      this.userProfileModelList.set(index, updatedProfile);
    } else {
      throw new NonExistantUserProfileException(
          "UserProfile "
              + updatedProfile.getProfileID()
              + " with name "
              + updatedProfile.getName()
              + "does not exist or has been deleted");
    }

    // Update currentUser entry if needed
    if (this.currentUser.getProfileID() == updatedProfile.getProfileID()) {
      this.currentUser = updatedProfile;
    }

    houseGraphic.repaint();
  }

  /**
   * Returns an array of all the userprofiles that match the specified desiredProfileType
   *
   * @param desiredProfileType {@link ProfileType} Enum
   * @return Array of {@link UserProfileModel}
   */
  public UserProfileModel[] getProfilesByCategory(ProfileType desiredProfileType) {
    ArrayList<UserProfileModel> temp = new ArrayList<UserProfileModel>();

    for (int i = 0; i < this.userProfileModelList.size(); i++) {
      if (this.userProfileModelList.get(i).getProfileType() == desiredProfileType)
        temp.add(new UserProfileModel(this.userProfileModelList.get(i))); // Deep copy
    }
    UserProfileModel[] temp2 = new UserProfileModel[temp.size()];
    for (int i = 0; i < temp.size(); i++) {
      temp2[i] = temp.get(i);
    }

    return temp2;
  }

  /**
   * Gets a boolean indicating whether a "Current User" has been selected in the simulator
   *
   * @return true if currentUser is set and false otherwise.
   */
  public boolean isCurrentUserSet() {
    return !(this.currentUser == null);
  }

  /**
   * Gets the outside temperature that is currently set
   *
   * @return temperature value
   */
  public int getOutsideTemp() {
    return this.outsideTemperature;
  }

  /**
   * Gets the currently set date in the simulator in a pre-determined format
   *
   * @return String representation of a {@link Date} object
   */
  public String getDateString() {
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
    return dateFormatter.format(this.currentCalObj.getTime());
  }

  /**
   * Gets the currently set time in the simulator in a pre-determined format
   *
   * @return String representation of a {@link Date} object
   */
  public String getTimeString() {
    SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss a");
    return dateFormatter.format(this.currentCalObj.getTime());
  }

  /**
   * Gets the currently set date and time in the simulator in a pre-determined format
   *
   * @return Date object representing currently set date and time
   */
  public Date getDateObject() {
    return this.currentCalObj.getTime();
  }

  /**
   * Sets the Date of the Simulator
   *
   * @param newDate {@link Date} object representing the desired date
   */
  public void setDate(Date newDate) {
    this.currentCalObj.set(newDate.getYear(), newDate.getMonth(), newDate.getDate());
  }

  /**
   * Sets the Time of the Simulator
   *
   * @param newTime {@link Date} object representing the desired time
   */
  public void setTime(Date newTime) {
    this.currentCalObj.set(Calendar.HOUR_OF_DAY, newTime.getHours());
    this.currentCalObj.set(Calendar.MINUTE, newTime.getMinutes());
  }

  /**
   * Gets all the existing rooms that are in the {@link House}
   *
   * @return An array of {@link Room} objects
   */
  public Room[] getRooms() {
    ArrayList<Room> temp = this.house.getRooms();
    Room[] roomArray = new Room[temp.size() + 1];
    Room r = new Room("Outside", null, null, null, null, 0);

    for (int i = 0; i < temp.size(); i++) {
      roomArray[i] =
          temp.get(
              i); // No need to create new Room objects since the getRooms() method returns a new
      // ArrayList object.
    }

    roomArray[roomArray.length - 1] = r;
    return roomArray;
  }

  /**
   * Adds a new {@link UserProfileModel} to the internal List of this class
   *
   * @param newUser The new {@link UserProfileModel} to be added
   * @throws Exception if the specified {@link UserProfileModel} contains invalid attributes. This
   *     includes an empty profile name or non-set {@link ProfileType}
   */
  public void addUserProfile(UserProfileModel newUser) throws Exception {

    if (newUser.getName().equals("")
        || newUser.getName() == null
        || newUser.getProfileType() == null) {
      throw new Exception("Can Not Create User: Invalid User Attributes");
    } else {
      this.userProfileModelList.add(new UserProfileModel(newUser));
      Console.print(
          "New user '" + newUser.getName() + "'/" + newUser.getProfileType() + " has been created");
    }

    houseGraphic.repaint();
  }

  /**
   * Remove the inputted user profile from the list.
   *
   * @param u the user to be removed.
   */
  public void removeUserProfile(UserProfileModel u)  {
    for (int i = 0; i < this.userProfileModelList.size(); i++) {
      if(this.userProfileModelList.get(i).getProfileID() == u.getProfileID()){
        this.userProfileModelList.remove(i);
        break;
      }
    }
  }

  /**
   * Gets simulation object.
   *
   * @return the simulation running
   */
  public boolean getSimulationRunning() {
    return this.simulationRunning;
  }

  /**
   * Returns boolean if the Windows are blocked..
   *
   * @return the boolean
   */
  public boolean isWindowObstructed() {
    return this.windowsObstructed;
  }

  /** Turns on the simulation */
  public void startSimulation() {
    this.simulationRunning = true;
    Console.print("The simulation has been started.");
  }

  /** Turns off the simulation */
  public void stopSimulation() {
    this.simulationRunning = false;
    Console.print("The simulation has been stopped.");
  }

  /** Obstructs all windows */
  public void obstructWindows() {
    Console.print("Obstructing all windows!");
    this.windowsObstructed = true;
  }

  /** Removes obstruction from all windows */
  public void clearWindows() {
    Console.print("Clearing all windows!");
    this.windowsObstructed = false;
  }

  /**
   * Returns the HouseGraphic displayed to the user
   *
   * @return the house graphic
   */
  public HouseGraphic getHouseGraphic() {
    return this.houseGraphic;
  }
}
