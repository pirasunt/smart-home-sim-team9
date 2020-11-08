package Models;

import Tools.CustomTimer;
import Views.CustomConsole;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimerTask;

/** The type Security model. */
public class SecurityModel {

  /** The Custom start. */
  static StartAwayLights customStart;
  /** The Custom end. */
  static EndAwayLights customEnd;
  /** The Start t. */
  static CustomTimer startT;
  /** The End t. */
  static CustomTimer endT;

  private static SpinnerDateModel startModel;
  private static SpinnerDateModel endModel;
  private final Date startDate;
  private final Date endDate;
  /** The Interval model. */
  SpinnerNumberModel intervalModel;

  private static boolean awayOn = false;
  private Room[] roomsToLight = null;

  /** Instantiates a new Security model. */
  public SecurityModel() {
    startDate = new Date();
    startModel = new SpinnerDateModel(startDate, null, null, Calendar.MINUTE);
    endDate = new Date();
    endModel = new SpinnerDateModel(endDate, null, null, Calendar.MINUTE);
    intervalModel = new SpinnerNumberModel();
    intervalModel.setMinimum(0);
    startT = new CustomTimer();
    endT = new CustomTimer();
  }

  /** Start away timer. */
  public static void startAwayTimer() {

    int timerDel = EnvironmentModel.getTimer().getDelay();
    int multiplier = 1;
    if (timerDel == 1000) {
      multiplier = 1;
    } else if (timerDel == 100) {
      multiplier = 10;
    } else if (timerDel == 10) {
      multiplier = 100;
    }
    customStart = new StartAwayLights();
    customEnd = new EndAwayLights();

    startT = new CustomTimer();
    endT = new CustomTimer();

    Date envTime = EnvironmentModel.getDateObject();
    Date start = startModel.getDate();
    Date end = endModel.getDate();

    Calendar sCal = new GregorianCalendar();
    Calendar endCal = new GregorianCalendar();

    sCal.setTime(start);
    sCal.set(Calendar.YEAR, 1900 + envTime.getYear());
    sCal.set(Calendar.DAY_OF_MONTH, envTime.getDate());
    sCal.set(Calendar.MONTH, envTime.getMonth());

    endCal.setTime(end);
    endCal.set(Calendar.YEAR, 1900 + envTime.getYear());
    endCal.set(Calendar.DAY_OF_MONTH, envTime.getDate());
    endCal.set(Calendar.MONTH, envTime.getMonth());
    boolean endIsBefore = end.before(start);

    if (endIsBefore) {
      endCal.add(Calendar.DATE, 1);
    }
    long deltaStart =
        sCal.getTimeInMillis() - EnvironmentModel.getDateObject().getTime() / multiplier;
    long deltaEnd =
        endCal.getTimeInMillis() - EnvironmentModel.getDateObject().getTime() / multiplier;
    long dayLen = 1000 * 60 * 60 * 24 / multiplier;

    if (deltaStart < 0) {
      // start the lights right away and set the end timer because we're in the zone
      customStart.turnOnSelectedLights();
    } else {
      startT.schedule(customStart, deltaStart, dayLen);
    }
    endT.schedule(customEnd, deltaEnd, dayLen);
    CustomConsole.print("Away Mode lighting has been set!");
  }

  /**
   * Gets interval model.
   *
   * @return the interval model
   */
  public SpinnerNumberModel getIntervalModel() {
    return intervalModel;
  }

  /**
   * Gets start model.
   *
   * @return the start model
   */
  public SpinnerDateModel getStartModel() {
    return startModel;
  }

  /**
   * Gets end model.
   *
   * @return the end model
   */
  public SpinnerDateModel getEndModel() {
    return endModel;
  }

  /**
   * Is away on boolean.
   *
   * @return the boolean
   */
  public static boolean isAwayOn() {
    return awayOn;
  }

  /**
   * Sets away on.
   *
   * @param awayOn the away on
   */
  public void setAwayOn(boolean awayOn) {
    // TODO: Verify if obstruction should be checked here or in Controller (rn its controller)
    if (awayOn) {
      EnvironmentModel.getHouse().lockAndClose(true);
      EnvironmentModel.getHouseGraphic().repaint();
    }
    else{
      startAwayTimer();
    }

    this.awayOn = awayOn;
  }
  /**
   * Get rooms to light room [ ].
   *
   * @return the room [ ]
   */
  public Room[] getRoomsToLight() {
    return roomsToLight;
  }

  /**
   * Sets rooms to light.
   *
   * @param roomsToLight the rooms to light
   */
  public void setRoomsToLight(Room[] roomsToLight) {
    this.roomsToLight = roomsToLight;
  }

  /** Cancel all timers. */
  public static void cancelAllTimers() {
    startT.cancel();
    endT.cancel();
  }


  /**
   * Timer stuff.
   *
   * @param start the start
   * @param end the end
   */
  public void timerStuff(String start, String end) {
    /**
     * get current time calculate how many seconds (or ms) until the start make a thread with that
     * delay? execute once done repeat for end
     *
     * <p>--
     *
     * <p>if paused, cancel thread, when resume, recalc time -- if time changed, cancel thread check
     * if action should have happened and should be happening if so, do them else make a new thread
     * with tht delay
     *
     * <p>--
     *
     * <p>if away mode off, cancel all threads
     */
  }

  private static class StartAwayLights extends TimerTask {

    @Override
    public void run() {
      // start the lights
      turnOnSelectedLights();
    }

    /** Turn on selected lights. */
    public void turnOnSelectedLights() {
      System.out.println("lights on bitches");
      CustomConsole.print("Light have been turned on via a timer!");
    }
  }

  private static class EndAwayLights extends TimerTask {

    @Override
    public void run() {
      // end the lights
      turnOffSelectedLights();
    }

    /** Turn off selected lights. */
    public void turnOffSelectedLights() {
      System.out.println("lights off bitches");
      CustomConsole.print("Light have been turned off via a timer!");
    }
  }
}
