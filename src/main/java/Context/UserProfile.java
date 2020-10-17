package Context;

import Enums.profileType;

import java.util.UUID;

public class UserProfile{
    private UUID profileID;
    private profileType type;
    private String name;
    private int roomID; //This value is actually a roomID and is where the profile user currently is

    public UserProfile(profileType type, String name, int id){
        this.profileID = UUID.randomUUID();
        this.roomID = id;
        this.type = type;
        this.name = name;
    }

    //Copy Constructor
    public UserProfile(UserProfile original){
        this.profileID = original.profileID; //UUID immutable obj
        this.roomID = original.roomID;
        this.type = original.type;
        this.name = original.name;
    }

    /**
     * Returns deep copy of a UserProfile object with the location attribute modified
     * @param id The new Room ID to move the UserProfile to
     * @return a Deep copy of the UserProfile with the roomID attribute modified
     */
     UserProfile modifyLocation(int id){
        UserProfile temp = new UserProfile(this);
        temp.roomID = id;
        return temp;
    }

    /**
     * Returns deep copy of the UserProfile object with its name modified
     * @param newName The new name of the UserProfile
     * @return  a Deep copy of the UserProfile with the profile Name attribute modified
     */
    UserProfile modifyName(String newName) {
         UserProfile temp = new UserProfile(this);
         temp.name = newName;
         return temp;
    }

    public UUID getProfileID() {
        return this.profileID;
    }

    public profileType getProfileType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getRoomID() {
        return this.roomID;
    }


}
