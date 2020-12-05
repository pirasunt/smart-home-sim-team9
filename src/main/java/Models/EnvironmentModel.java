package Models;

import Custom.CustomXStream.CustomUserXStream;
import Custom.NonExistantUserProfileException;
import Enums.ProfileType;
import Observers.CurrentUserObserver;
import Observers.RoomChangeObserver;
import Views.CustomConsole;
import Views.HouseGraphic;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * EnvironmentModel represents the data structure of the system. The {@link
 * Controllers.EnvironmentController}****** manipulates the data within this class.
 */
public class EnvironmentModel {
  private static EnvironmentModel instance = null;
  private final House house;
  private final HouseGraphic houseGraphic;
  private final ArrayList<UserProfileModel> userProfileModelList;
  private UserProfileModel currentUser;
  private static boolean simulationRunning = false;
  private static Timer timer;
  private static ArrayList<RoomChangeObserver> roomChangeObservers;
  private static ArrayList<CurrentUserObserver> currentUserObservers;
  private boolean windowsObstructed = false;
  private static int outsideTemperature;
  private boolean automaticLights;
  private final Context c;

  private EnvironmentModel(
      House h,
      HouseGraphic hg,
      int temperature,
      Calendar cal,
      ArrayList<UserProfileModel> profileList) {
    house = h;
    houseGraphic = hg;
    outsideTemperature = temperature;
    userProfileModelList = profileList;
    currentUser = null;
    automaticLights = false;
    roomChangeObservers = new ArrayList<>();
    currentUserObservers = new ArrayList<>();
    timer = new Timer(1000, null);

    c = new Context(h, hg, timer, cal, profileList);
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
   * Sets current user.
   *
   * @param currentUser the current user
   */
  public void setCurrentUser(UserProfileModel currentUser) {
    this.currentUser = new UserProfileModel(currentUser);
    notifyCurrentUserObservers(currentUser);
    CustomConsole.print(
        "Current user has been set to "
            + this.currentUser.getName()
            + "/"
            + this.currentUser.getProfileType());
  }
  /**
   * Gets simulation object.
   *
   * @return the simulation running
   */
  public static boolean getSimulationRunning() {
    return simulationRunning;
  }


  /**
   * Method that subscribes a RoomChangeObserver. Fires anytime a user is successfully moved to a new room.
   *
   * @param ob the observer
   */
  public static void subscribe(RoomChangeObserver ob) {
    roomChangeObservers.add(ob);
  }

  public static void unsubscribe(RoomChangeObserver ob) {
    roomChangeObservers.remove(ob);
  }

  public static void subscribe(CurrentUserObserver ob) {
    currentUserObservers.add(ob);
  }

  public static void unsubscribe(CurrentUserObserver ob) {
    currentUserObservers.remove(ob);
  }


  /**
   * Notifies all Observers whenever a user changes rooms in the simulation
   * @param oldRoomID ID of the old room
   * @param newRoomID ID of the new room
   */
  public static void notifyRoomChangeObservers(int oldRoomID, int newRoomID) {
    for (RoomChangeObserver o : roomChangeObservers) {
      o.update(oldRoomID, newRoomID);
    }
  }

  /**
   * Notifies all Observers whenever the current user is changed
   * @param newCurrentUser the "New" current user
   */
  public static void notifyCurrentUserObservers(UserProfileModel newCurrentUser) {
    for (CurrentUserObserver o : currentUserObservers) {
      o.update(new UserProfileModel(newCurrentUser));
    }
  }

  /**
   * Initialize timer.
   *
   * @param listenForTimer the listen for timer
   */
  public void initializeTimer(ActionListener listenForTimer) {
    timer.addActionListener(listenForTimer);
  }

  /**
   * Used to set the outside temperature in the simulation
   *
   * @param newTemp is the new temperature
   */
  public void setTemperature(int newTemp) {
    outsideTemperature = newTemp;
  }

  /**
   * Returns a deep copy of all the registered user profiles
   *
   * @return UserProfile Array of all registered user profile in the environment
   */
  public UserProfileModel[] getAllUserProfiles() {
    UserProfileModel[] up = new UserProfileModel[userProfileModelList.size()];

    for (int i = 0; i < up.length; i++) {
      up[i] = new UserProfileModel(userProfileModelList.get(i));
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

    int oldRoomID = profile.getRoomID();
    int newRoomID = room.getId();
    UserProfileModel updatedProfile = profile.modifyLocation(room.getId());

    try {
      updateProfileEntry(updatedProfile, new File("UserProfiles.xml"));
      notifyRoomChangeObservers(oldRoomID, newRoomID);
      if(currentUser.getProfileID() == updatedProfile.getProfileID())
        notifyCurrentUserObservers(updatedProfile);
      CustomConsole.print(
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
   * Modify user privilege.
   *
   * @param profile the profile
   * @param newPrivilegeLevel the new privilege level
   */
  public void modifyUserPrivilege(UserProfileModel profile, ProfileType newPrivilegeLevel) {

    UserProfileModel updatedProfile = profile.modifyPrivilege(newPrivilegeLevel);
    try {
      updateProfileEntry(updatedProfile, new File("UserProfiles.xml"));
      if(currentUser.getProfileID() == updatedProfile.getProfileID())
        notifyCurrentUserObservers(updatedProfile);
      CustomConsole.print(
          "Updated privilege of user '"
              + profile.getName()
              + "' to '"
              + newPrivilegeLevel.toString()
              + "'.");
    } catch (NonExistantUserProfileException e) {
      System.err.println(e.getMessage()); // TODO: Return some sort of error window in the future
    }
  }

  /**
   * Modifies the profile name of the specified user. NOTE: This method only modifies the profile
   * name on the {@link UserProfileModel} that is contained within the internal list of this class
   * and NOT the reference that is passed in.
   *
   * @param profile The {@link UserProfileModel} object that needs its name modified
   * @param newName The new name of the user profile
   * @param file the file
   */
  public void editProfileName(UserProfileModel profile, String newName, File file) {

    UserProfileModel updatedProfile = profile.modifyName(newName);
    try {
      updateProfileEntry(updatedProfile, file);
      if(currentUser.getProfileID() == updatedProfile.getProfileID())
        notifyCurrentUserObservers(updatedProfile);
    } catch (NonExistantUserProfileException e) {
      System.err.println(e.getMessage());
    }

    houseGraphic.repaint();
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
    for (int i = 0; i < userProfileModelList.size(); i++) {
      if (id == userProfileModelList.get(i).getProfileID()) {
        temp = new UserProfileModel(userProfileModelList.get(i));
        break;
      }
    }
    if (temp == null) {
      throw new NonExistantUserProfileException(
          "UserProfile " + id + "does not exist or has been deleted");
    }

    return temp;
  }

  private void updateProfileEntry(UserProfileModel updatedProfile, File userProfilesFile)
      throws NonExistantUserProfileException {

    boolean existingProfile = false;
    int index = -1;
    for (int i = 0; i < userProfileModelList.size(); i++) {
      if (userProfileModelList.get(i).getProfileID() == updatedProfile.getProfileID()) {
        existingProfile = true;
        index = i;
      }
    }

    if (existingProfile && index >= 0) {

      userProfileModelList.set(index, updatedProfile);

      try {
        UserProfileModel[] profileListAsArray = new UserProfileModel[userProfileModelList.size()];

        CustomUserXStream uStream = new CustomUserXStream();
        uStream.toXML(
            userProfileModelList.toArray(profileListAsArray),
            new FileOutputStream(userProfilesFile));
      } catch (Exception e) {
      }

    } else {
      throw new NonExistantUserProfileException(
          "UserProfile "
              + updatedProfile.getProfileID()
              + " with name "
              + updatedProfile.getName()
              + "does not exist or has been deleted");
    }

    // Update currentUser entry if needed
    if (currentUser.getProfileID() == updatedProfile.getProfileID()) {
      currentUser = updatedProfile;
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

    for (int i = 0; i < userProfileModelList.size(); i++) {
      if (userProfileModelList.get(i).getProfileType() == desiredProfileType)
        temp.add(new UserProfileModel(userProfileModelList.get(i))); // Deep copy
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
    return !(currentUser == null);
  }

  /**
   * Gets the outside temperature that is currently set
   *
   * @return temperature value
   */
  public static int getOutsideTemp() {
    return outsideTemperature;
  }

  /**
   * Gets all the existing rooms that are in the {@link House}
   *
   * @return An array of {@link Room} objects
   */
  public Room[] getRooms() {
    ArrayList<Room> temp = house.getRooms();
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
   * Returns a Room with the matching roomID
   * @param roomID Integer that uniquely represents a room
   * @return Room
   */
  public Room getRoomByID(int roomID){
    return house.getRoomById(roomID);
  }

  /**
   * Adds a new {@link UserProfileModel} to the internal List of this class
   *
   * @param newUser The new {@link UserProfileModel} to be added
   * @param userProfilesFile the user profiles file
   * @throws Exception if the specified {@link UserProfileModel} contains invalid attributes. This
   *     includes an empty profile name or non-set {@link ProfileType}
   */
  public void addUserProfile(UserProfileModel newUser, File userProfilesFile) throws Exception {

    if (newUser.getName().equals("")
        || newUser.getName() == null
        || newUser.getProfileType() == null) {
      throw new Exception("Can Not Create User: Invalid User Attributes");
    } else {

      UserProfileModel userToAdd = new UserProfileModel(newUser);

      try {
        userProfileModelList.add(userToAdd);
        UserProfileModel[] profileListAsArray = new UserProfileModel[userProfileModelList.size()];
        CustomConsole.print(
            "New user '"
                + newUser.getName()
                + "'/"
                + newUser.getProfileType()
                + " has been created");

        CustomUserXStream uStream = new CustomUserXStream();
        uStream.toXML(
            userProfileModelList.toArray(profileListAsArray),
            new FileOutputStream(userProfilesFile));
      } catch (FileNotFoundException e) {
        userProfileModelList.remove(userToAdd);
        CustomConsole.print("Error writing to UserProfiles.xml; user was not created.");
      }
    }

    houseGraphic.repaint();
  }

  /**
   * Remove the inputted user profile from the list.
   *
   * @param u the user to be removed.
   * @param userProfilesFile the user profiles file
   */
  public void removeUserProfile(UserProfileModel u, File userProfilesFile) {
    for (int i = 0; i < userProfileModelList.size(); i++) {
      if (userProfileModelList.get(i).getProfileID() == u.getProfileID()) {
        userProfileModelList.remove(i);
        try {
          UserProfileModel[] profileListAsArray = new UserProfileModel[userProfileModelList.size()];

          CustomUserXStream uStream = new CustomUserXStream();
          uStream.toXML(
              userProfileModelList.toArray(profileListAsArray),
              new FileOutputStream(userProfilesFile));
        } catch (FileNotFoundException e) {
          userProfileModelList.add(u);
          CustomConsole.print("Error removing user from UserProfiles.xml; user was not deleted.");
        }
        break;
      }
    }
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
    simulationRunning = true;
    CustomConsole.print("The simulation has been started.");
  }

  /**
   * Sets the status of the automatic lighting system. True turns on the auto-lighting system whereas false turns it off
   * @param newStatus
   */
  public void setAutomaticLights(boolean newStatus){
    this.automaticLights = newStatus;
  }

  public boolean getAutomaticLights(){
    return this.automaticLights;
  }

  /** Turns off the simulation */
  public void stopSimulation() {
    simulationRunning = false;
    CustomConsole.print("The simulation has been stopped.");
  }
}
