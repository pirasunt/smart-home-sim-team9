package Models;

import Observers.CurrentUserObserver;
import Observers.TimeChangeObserver;
import Views.HouseGraphic;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Context implements CurrentUserObserver {

    private static House house;
    private static HouseGraphic houseGraphic;
    private static Timer timer;
    private static Calendar calendar;
    private static ArrayList<UserProfileModel> userList;
    private static ArrayList<TimeChangeObserver> timeChangeObservers;
    private static UserProfileModel currentUser;

    public Context(House h, HouseGraphic hg, Timer t, Calendar c, ArrayList<UserProfileModel> upm) {
        house = h;
        houseGraphic = hg;
        timer = t;
        calendar = c;
        userList = upm;

        timeChangeObservers = new ArrayList<>();
        EnvironmentModel.subscribe(this);
    }

    public static void repaintHouseGraphic(){
        houseGraphic.repaint();
    }

    public static House getHouse() {
        return house;
    }

    public static int getDelay(){
        return timer.getDelay();
    }

    public static void setDelay(int newDelay){
        timer.setDelay(newDelay);
    }

    public static void stopTimer(){
        timer.stop();
    }

    public static void restartTimer(){
        timer.restart();
    }

    /**
     * Gets the currently set date in the simulator in a pre-determined format
     *
     * @return String representation of a {@link Date} object
     */
    public static String getDateString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormatter.format(calendar.getTime());
    }

    /**
     * Gets the currently set time in the simulator in a pre-determined format
     *
     * @return String representation of a {@link Date} object
     */
    public static String getTimeString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss a");
        return dateFormatter.format(calendar.getTime());
    }

    /**
     * Gets the currently set date and time in the simulator in a pre-determined format
     *
     * @return Date object representing currently set date and time
     */
    public static Date getDateObject() {
        return calendar.getTime();
    }


    /**
     * Sets the Date of the Simulator
     *
     * @param newDate {@link Date} object representing the desired date
     */
    public static void setDate(Calendar newDate) {
        calendar.set(
                newDate.get(Calendar.YEAR),
                newDate.get(Calendar.MONTH),
                newDate.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Sets the Time of the Simulator
     *
     * @param newTime {@link Date} object representing the desired time
     */
    public static void setTime(Date newTime) {
        calendar.set(Calendar.HOUR_OF_DAY, newTime.getHours());
        calendar.set(Calendar.MINUTE, newTime.getMinutes());
        calendar.set(Calendar.SECOND, newTime.getSeconds());
        notifyTimeChangeObservers(Context.getTimeString());
    }

    public static void notifyTimeChangeObservers(String newTime) {
        for (TimeChangeObserver o : timeChangeObservers) {
            o.update(newTime);
        }
    }
    public static void unsubscribe(TimeChangeObserver ob) {
        timeChangeObservers.remove(ob);
    }
    public static void subscribe(TimeChangeObserver ob) {
        timeChangeObservers.add(ob);
    }

    /**
     * House is empty boolean.
     *
     * @return the boolean
     */
    public static boolean houseIsEmpty() {
        for (UserProfileModel u : userList) {
            if (u.getRoomID() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the observer with the new currentUser of the Simulation
     *
     * @param newCurrentUser {@link UserProfileModel} Object representing the new user
     */
    @Override
    public void update(UserProfileModel newCurrentUser) {
        currentUser = new UserProfileModel(newCurrentUser);
    }

    /**
     * Returns a deep copy of the currently selected user on the simulation
     *
     * @return Deep copy of currently selected user.
     */
    public static UserProfileModel getCurrentUser() {
        return new UserProfileModel(currentUser);
    }
}
