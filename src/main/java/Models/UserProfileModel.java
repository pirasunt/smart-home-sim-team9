package Models;

import Enums.ProfileType;

import java.util.UUID;

public class UserProfileModel {
    private final UUID profileID;
    private final ProfileType type;
    private String name;
    private int roomID; //Indicates UserProfile's current room location (A value of -1 indicates that a room has not been assigned to the UserProfile)

    public UserProfileModel(ProfileType type, String name, int id) {
        this.profileID = UUID.randomUUID();
        this.roomID = id;
        this.type = type;
        this.name = name;
    }

    //Copy Constructor
    public UserProfileModel(UserProfileModel original) {
        this.profileID = original.profileID; //UUID immutable obj
        this.roomID = original.roomID;
        this.type = original.type;
        this.name = original.name;
    }

    /**
     * Returns deep copy of a UserProfile object with the location attribute modified
     *
     * @param id The new Room ID to move the UserProfile to
     * @return a Deep copy of the UserProfile with the roomID attribute modified
     */
    UserProfileModel modifyLocation(int id) {
        UserProfileModel temp = new UserProfileModel(this);
        temp.roomID = id;
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

    public UUID getProfileID() {
        return this.profileID;
    }

    public ProfileType getProfileType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getRoomID() {
        return this.roomID;
    }


    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
