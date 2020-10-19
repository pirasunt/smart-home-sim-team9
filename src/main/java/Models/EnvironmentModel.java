package Models;

import Custom.NonExistantUserProfileException;
import Enums.profileType;
import Views.Console;

import java.text.SimpleDateFormat;
import java.util.*;

public class EnvironmentModel {
    private static EnvironmentModel instance = null;

    private int outsideTemperature;
    private Calendar currentCalObj;
    private UserProfileModel currentUser;
    private ArrayList<UserProfileModel> userProfileModelList;
    private House house;


    public static EnvironmentModel createSimulation(House h, UserProfileModel... profiles){
        if(instance == null) {
            ArrayList<UserProfileModel> profileList = new ArrayList<UserProfileModel>();
            for(UserProfileModel profile:profiles) {
                profileList.add(profile);
            }
            instance = new EnvironmentModel(h, profileList);


        } else {
            System.err.println("There already exists an instance of environment. Returning that instance");
        }

        return instance;
    }

    private EnvironmentModel(House h, int temperature, Calendar cal, ArrayList<UserProfileModel> profileList) {
        this.house = h;
        this.outsideTemperature = temperature;
        this.currentCalObj = cal;
        this.userProfileModelList = profileList;
        this.currentUser = null;
    }

    private EnvironmentModel(House h, ArrayList<UserProfileModel> profileList) {
       this(h, 21, new GregorianCalendar(), profileList);
    }

    // used in testing to reset instance
    public static void resetInstance(){
        instance = null;
    }

    public void setTemperature(int newTemp){
        this.outsideTemperature = newTemp;
    }

    /**
     * Returns a deep copy of all the registered user profiles
     * @return UserProfile Array of all registered user profile in the environment
     */
    public UserProfileModel[] getAllUserProfiles(){
        UserProfileModel[] up = new UserProfileModel[this.userProfileModelList.size()];

        for(int i =0; i< up.length ; i++){
            up[i] = new UserProfileModel(this.userProfileModelList.get(i));
        }
        return up;
    }


    public void modifyProfileLocation(UserProfileModel profile, Room room) {

        try {
            updateProfileEntry(profile.modifyLocation(room.getId()));
            Console.print("Set Room to: '" + room.getName() + "' for user " + profile.getName() +"/" +profile.getProfileType() + "\n");
        }
        catch(NonExistantUserProfileException e) {
            System.err.println(e.getMessage()); //TODO: Return some sort of error window
        }
    }

    public void editProfileName(UserProfileModel profile, String newName) {

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
    public UserProfileModel getCurrentUser() {
        return new UserProfileModel(this.currentUser);
    }

    public UserProfileModel getUserByID(UUID id) throws NonExistantUserProfileException {
        UserProfileModel temp = null;
        for(int i = 0; i< this.userProfileModelList.size(); i++) {
            if(id == this.userProfileModelList.get(i).getProfileID()) {
                temp = new UserProfileModel(this.userProfileModelList.get(i));
                break;
            }
        }
        if(temp == null) {
            throw new NonExistantUserProfileException("UserProfile " + id + "does not exist or has been deleted");
        }

        return temp;
    }


    /**
     * Internal helper method that finds and updates a current UserProfile object in the ArrayList attribute
     * Any updated UserProfile objects in the ArrayList must pass by this method
     * @param updatedProfile UserProfile object with updated attributes
     */
    private void updateProfileEntry(UserProfileModel updatedProfile) throws NonExistantUserProfileException {

        boolean existingProfile = false;
        int index = -1;
        for(int i = 0; i < this.userProfileModelList.size(); i++) {
            if(this.userProfileModelList.get(i).getProfileID() == updatedProfile.getProfileID()) {
                existingProfile = true;
                index = i;
            }
        }

        if(existingProfile && index >= 0) {
            this.userProfileModelList.set(index, updatedProfile);
        } else {
            throw new NonExistantUserProfileException("UserProfile " + updatedProfile.getProfileID() + " with name " + updatedProfile.getName() + "does not exist or has been deleted");
        }

        //Update currentUser entry if needed
        if(this.currentUser.getProfileID() == updatedProfile.getProfileID()) {
            this.currentUser = updatedProfile;
        }

    }


    public UserProfileModel[] getProfilesByCategory(profileType desiredProfileType) {
        ArrayList<UserProfileModel> temp = new ArrayList<UserProfileModel>();

        for(int i = 0; i < this.userProfileModelList.size(); i++) {
            if(this.userProfileModelList.get(i).getProfileType() == desiredProfileType)
                temp.add(new UserProfileModel(this.userProfileModelList.get(i))); //Deep copy
        }
        UserProfileModel[] temp2 = new UserProfileModel[temp.size()];
        for(int i=0; i < temp.size(); i++) {
            temp2[i] = temp.get(i);
        }

    return temp2;
    }

    public void setCurrentUser(UserProfileModel currentUser) {
        this.currentUser = new UserProfileModel(currentUser);
        Console.print("Current user has been set to " + this.currentUser.getName()+"/" + this.currentUser.getProfileType());
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
