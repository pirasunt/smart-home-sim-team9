package Models;

import Enums.ProfileType;

import java.util.UUID;

/** The type User profile model. */
public class UserProfileModel {
  private final UUID profileID;
  private ProfileType type;
  private String name;
  private int roomID;

  /**
   * Creates a new UserProfileModel Object with a random UUID
   *
   * @param type One of 4 profile types and represents the privilege level of the profile
   * @param name Name of the profile.
   * @param id Represents the room ID of the user's current location
   */
  public UserProfileModel(ProfileType type, String name, int id) {
    this.profileID = UUID.randomUUID();
    this.roomID = id;
    this.type = type;
    this.name = name;
  }

  /**
   * Copy constructor used to generate deep copies
   *
   * @param original The original object that needs to be copied
   */
  public UserProfileModel(UserProfileModel original) {
    this.profileID = original.profileID; // UUID immutable obj already
    this.roomID = original.roomID;
    this.type = original.type;
    this.name = original.name;
  }

  /**
   * Returns deep copy of a UserProfile object with the location attribute modified
   *
   * @param id The new Room ID of the UserProfile
   * @return a Deep copy of the UserProfile with the roomID attribute modified
   */
  UserProfileModel modifyLocation(int id) {
    UserProfileModel temp = new UserProfileModel(this);
    temp.roomID = id;
    return temp;
  }

  UserProfileModel modifyPrivilege(ProfileType newPrivilege) {
    UserProfileModel temp = new UserProfileModel(this);
    temp.type = newPrivilege;
    return temp;
  }

  /**
   * Returns deep copy of the UserProfile object with its name modified
   *
   * @param newName The new name of the UserProfile
   * @return a Deep copy of the UserProfile with the profile Name attribute modified
   */
  UserProfileModel modifyName(String newName) {
    UserProfileModel temp = new UserProfileModel(this);
    temp.name = newName;
    return temp;
  }

  /**
   * Getter that fetches the profile ID. Used to uniquely identify an object of this class
   *
   * @return UUID of the UserProfile
   */
  public UUID getProfileID() {
    return this.profileID;
  }

  /**
   * Getter that fetches {@link ProfileType} Enum and represents the privilege level of the user
   *
   * @return UserProfile privilege level
   */
  public ProfileType getProfileType() {
    return this.type;
  }

  /**
   * Getter that fetches the Profile Name
   *
   * @return Name of the profile as a String
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter that fetches the Profile's location
   *
   * @return Integer that represents the {@link Room} ID of the UserProfile
   */
  public int getRoomID() {
    return this.roomID;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return this.name;
  }
}
