package Models;

import Observers.CurrentUserObserver;
import Observers.TimeChangeObserver;
import Views.HouseGraphic;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Context.
 */
public class Context implements CurrentUserObserver {

    private static House house;
    private static HouseGraphic houseGraphic;
    private static Timer timer;
    private static Calendar calendar;
    private static ArrayList<UserProfileModel> userList;
    private static ArrayList<TimeChangeObserver> timeChangeObservers;
    private static UserProfileModel currentUser;

    /**
     * Instantiates a new Context. This is automatically instantiated in the EnvironmentModel
     *
     * @param h   Instance of House Object
     * @param hg  Instance of HouseGraphic Object
     * @param t   Instance of the Simulation Timer
     * @param c   Instance of the Calendar object that is used to provide the date/time for the simulation
     * @param upm Instance of the ArrayList that contains all of the registered user profiles in the simulation
     */
    public Context(House h, HouseGraphic hg, Timer t, Calendar c, ArrayList<UserProfileModel> upm) {
        house = h;
        houseGraphic = hg;
        timer = t;
        calendar = c;
        userList = upm;

        timeChangeObservers = new ArrayList<>();
        EnvironmentModel.subscribe(this);
    }

    /**
     * Repaint house graphic.
     */
    public static void repaintHouseGraphic(){
        houseGraphic.repaint();
    }

    /**
     * Gets house.
     *
     * @return the house
     */
    public static House getHouse() {
        return house;
    }

    /**
     * Gets the current delay of the timer
     *
     * @return the int
     */
    public static int getDelay(){
        return timer.getDelay();
    }

    /**
     * Set the delay of the timer.
     *
     * @param newDelay the new delay value. Represents the interval of time (in milliseconds) between each update of the timer
     *                 A lower number will be reflected by a faster movement of the clock
     */
    public static void setDelay(int newDelay){
        timer.setDelay(newDelay);
    }

    /**
     * Stop timer.
     */
    public static void stopTimer(){
        timer.stop();
    }

    /**
     * Restart timer.
     */
    public static void restartTimer(){
        timer.restart();
    }

    /**
     * Gets the currently set date in the simulator in a pre-determined format
     *
     * @return String representation of a {@link Date} object
     */
    public static String getDateString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM dd, yyyy");
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

    /**
     * Notify time change observers.
     *
     * @param newTime the new time in string format
     */
    public static void notifyTimeChangeObservers(String newTime) {
        for (TimeChangeObserver o : timeChangeObservers) {
            o.update(newTime);
        }
    }

    /**
     * Unsubscribe from the Subject that emits an event each time the simulation time changes
     *
     * @param ob the ob
     */
    public static void unsubscribe(TimeChangeObserver ob) {
        timeChangeObservers.remove(ob);
    }

    /**
     * Subscribe to the Subject that emits an event each time the simulation time changes
     *
     * @param ob the ob
     */
    public static void subscribe(TimeChangeObserver ob) {
        timeChangeObservers.add(ob);
    }

    /**
     *  Returns a boolean representing whether the or not the house has any occupants inside
     *
     * @return True if house is empty; false otherwise
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
