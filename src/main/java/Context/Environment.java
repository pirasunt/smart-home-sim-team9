package Context;

import Enums.profileType;
import Models.House;
import Models.Room;

import java.text.SimpleDateFormat;
import java.util.*;

public class Environment {
    private static Environment instance = null;

    private int outsideTemperature;
    private Calendar currentCalObj;
    private UserProfile currentUser;
    private ArrayList<UserProfile> userProfileList;
    private House house;


    public static Environment createSimulation(House h, UserProfile... profiles){
        if(instance == null) {
            ArrayList<UserProfile> profileList = new ArrayList<UserProfile>();
            for(UserProfile profile:profiles) {
                profileList.add(profile);
            }
            instance = new Environment(h, profileList);


        } else {
            System.err.println("There already exists an instance of environment. Returning that instance");
        }

        return instance;
    }

    private Environment(House h, int temperature, Calendar cal, ArrayList<UserProfile> profileList) {
        this.house = h;
        this.outsideTemperature = temperature;
        this.currentCalObj = cal;
        this.userProfileList = profileList;
        this.currentUser = null;
    }

    private Environment(House h, ArrayList<UserProfile> profileList) {
       this(h, 21, new GregorianCalendar(), profileList);
    }

    public void setTemperature(int newTemp){
        this.outsideTemperature = newTemp;
    }

    /**
     * Returns a deep copy of all the registered user profiles
     * @return UserProfile Array of all registered user profile in the environment
     */
    public UserProfile[] getAllUserProfiles(){
        UserProfile[] up = new UserProfile[this.userProfileList.size()];

        for(int i =0; i< up.length ; i++){
            up[i] = new UserProfile(this.userProfileList.get(i));
        }
        return up;
    }


    public void modifyProfileLocation(UserProfile profile, int roomID) {

        try {
            updateProfileEntry(profile.modifyLocation(roomID));
        }
        catch(NonExistantUserProfileException e) {
            System.err.println(e.getMessage()); //TODO: Return some sort of error window
        }
    }

    public void editProfileName(UserProfile profile, String newName) {

        try {
            updateProfileEntry(profile.modifyName(newName));
        }
        catch(NonExistantUserProfileException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Returns a deep copy of the currently selected user on the simulation
     * @return Deep copy of currently selected user.
     */
    public UserProfile getCurrentUser() {
        return new UserProfile(this.currentUser);
    }


    /**
     * Internal helper method that finds and updates a current UserProfile object in the ArrayList attribute
     * Any updated UserProfile objects in the ArrayList must pass by this method
     * @param updatedProfile UserProfile object with updated attributes
     */
    private void updateProfileEntry(UserProfile updatedProfile) throws NonExistantUserProfileException {

        boolean existingProfile = false;
        int index = -1;
        for(int i = 0; i < this.userProfileList.size(); i++) {
            if(this.userProfileList.get(i).getProfileID() == updatedProfile.getProfileID()) {
                existingProfile = true;
                index = i;
            }
        }

        if(existingProfile && index >= 0) {
            this.userProfileList.set(index, updatedProfile);
        } else {
            throw new NonExistantUserProfileException("UserProfile " + updatedProfile.getProfileID() + " with name " + updatedProfile.getName() + "does not exist or has been deleted");
        }

        //Update currentUser entry if needed
        if(this.currentUser.getProfileID() == updatedProfile.getProfileID()) {
            this.currentUser = updatedProfile;
        }

    }


    public UserProfile[] getProfilesByCategory(profileType desiredProfileType) {
        ArrayList<UserProfile> temp = new ArrayList<UserProfile>();

        for(int i =0; i < this.userProfileList.size(); i++) {
            if(this.userProfileList.get(i).getProfileType() == desiredProfileType)
                temp.add(new UserProfile(this.userProfileList.get(i))); //Deep copy
        }
        UserProfile[] temp2 = new UserProfile[temp.size()];
        for(int i=0; i < temp.size(); i++) {
            temp2[i] = temp.get(i);
        }

    return temp2;
    }

    public void setCurrentUser(UserProfile currentUser) {
        this.currentUser = new UserProfile(currentUser);
    }

    public boolean isCurrentUserSet() {
        return !(this.currentUser == null);
    }

    public int getOutsideTemp() {
        return this.outsideTemperature;
    }

    public String getDateString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormatter.format(this.currentCalObj.getTime());
    }

    public String getTimeString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm a");
        return dateFormatter.format(this.currentCalObj.getTime());
    }

    public Date getDateObject() {
        return this.currentCalObj.getTime();
    }

    public void setDate(Date newDate) {
        this.currentCalObj.set(newDate.getYear(), newDate.getMonth(), newDate.getDate());
    }

    public void setTime(Date newDate) {
        this.currentCalObj.set(Calendar.HOUR_OF_DAY, newDate.getHours());
        this.currentCalObj.set(Calendar.MINUTE, newDate.getMinutes());
    }

    public Room[] getRooms() {
        ArrayList<Room> temp = this.house.getRooms();
        Room[] roomArray = new Room[temp.size()];

        for(int i =0; i< temp.size(); i++) {
            roomArray[i] = temp.get(i); //No need to create new Room objects since the getRooms() method returns a new ArrayList object.
        }

        return roomArray;
    }
}
