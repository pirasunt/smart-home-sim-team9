package Context;

import java.util.ArrayList;
import java.util.Date;

public class Environment {

    private double currentTemperature;
    private Date currentTime;
    private ArrayList<UserProfile> userProfileList;


    public Environment(double temperature, Date time, ArrayList<UserProfile> profileList) {
        this.currentTemperature = temperature;
        this.currentTime = time;
        this.userProfileList = profileList;

    }

    public Environment(ArrayList<UserProfile> profileList) {
        this.currentTemperature = 21;
        this.currentTime = new Date();
    }

    public void setTemperature(int newTemp){
        this.currentTemperature = newTemp;
    }

    public void setTime(Date newTime) {
        this.currentTime = newTime;

    }

}
