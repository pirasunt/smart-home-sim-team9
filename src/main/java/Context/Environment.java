package Context;

import Enums.profileType;

import java.text.SimpleDateFormat;
import java.util.*;

public class Environment {
    private static Environment instance = null;

    private int outsideTemperature;
    private Calendar currentCalObj;
    private UserProfile currentUser;
    private ArrayList<UserProfile> userProfileList;


    public static Environment createSimulation(UserProfile... profiles){
        if(instance == null) {
            ArrayList<UserProfile> profileList = new ArrayList<UserProfile>();
            for(UserProfile profile:profiles) {
                profileList.add(profile);
            }
            instance = new Environment(profileList);


        } else {
            System.err.println("There already exists an instance of environment. Returning that instance");
        }

        return instance;
    }

    private Environment(int temperature, Calendar cal, ArrayList<UserProfile> profileList) {
        this.outsideTemperature = temperature;
        this.currentCalObj = cal;
        this.userProfileList = profileList;
        this.currentUser = null;

    }

    private Environment(ArrayList<UserProfile> profileList) {
       this(21, new GregorianCalendar(), profileList);
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
            System.err.println(e.getMessage()); //TODO: Return some sort of error window?
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
        this.currentUser = currentUser;
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
}
