package Models;

import Enums.WallType;
import Models.Walls.OutsideWall;
import Models.Walls.Wall;
import Observers.AwayChangeObserver;
import Observers.RoomChangeObserver;
import Tools.CustomTimer;
import Views.CustomConsole;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/** The type Security model. */
public class SecurityModel {

  private static ArrayList<Room> roomsToLight = null;
  private static ScheduledFuture<?> startExec;
  private static ScheduledFuture<?> endExec;
  private static SpinnerDateModel startModel;
  private static SpinnerDateModel endModel;
  private static boolean awayOn = false;
  private final Date startDate;
  private final Date endDate;
  private final ArrayList<CustomTimer> authTimers;
  private final ArrayList<NotifyAuthTask> authTasks;
  private static ArrayList<AwayChangeObserver> awayChangeObservers;

  /** The Interval model. */
  SpinnerNumberModel intervalModel;
  /** Instantiates a new Security model. */
  public SecurityModel() {
    startDate = new Date();
    startModel = new SpinnerDateModel(startDate, null, null, Calendar.MINUTE);
    endDate = new Date();
    endModel = new SpinnerDateModel(endDate, null, null, Calendar.MINUTE);
    intervalModel = new SpinnerNumberModel();
    intervalModel.setMinimum(0);
    authTimers = new ArrayList<>();
    authTasks = new ArrayList<>();
    roomsToLight = new ArrayList<>();
    awayChangeObservers = new ArrayList<>();
  }

  /** Start away timer. */
  public static void startAwayTimer() {
    cancelAllTimers();

    int timerDel = Context.getDelay();
    int multiplier = 1;
    if (timerDel == 1000) {
      multiplier = 1;
    } else if (timerDel == 100) {
      multiplier = 10;
    } else if (timerDel == 10) {
      multiplier = 100;
    }

    Date _envTime = Context.getDateObject();
    Calendar envTime = new GregorianCalendar();
    envTime.setTime(_envTime);
    Date start = startModel.getDate();
    Date end = endModel.getDate();

    Calendar sCal = new GregorianCalendar();
    Calendar endCal = new GregorianCalendar();

    sCal.setTime(start);
    sCal.set(Calendar.YEAR, envTime.get(Calendar.YEAR));
    sCal.set(Calendar.DAY_OF_MONTH, envTime.get(Calendar.DAY_OF_MONTH));
    sCal.set(Calendar.MONTH, envTime.get(Calendar.MONTH));

    endCal.setTime(end);
    endCal.set(Calendar.YEAR, envTime.get(Calendar.YEAR));
    endCal.set(Calendar.DAY_OF_MONTH, envTime.get(Calendar.DAY_OF_MONTH));
    endCal.set(Calendar.MONTH, envTime.get(Calendar.MONTH));
    boolean endIsBefore = endCal.before(sCal);

    if (endIsBefore) {
      endCal.add(Calendar.DATE, 1);
    }
    long deltaStart = (sCal.getTimeInMillis() - (envTime.getTimeInMillis())) / multiplier;
    long deltaEnd = (endCal.getTimeInMillis() - (envTime.getTimeInMillis())) / multiplier;
    long dayLen = 1000 * 60 * 60 * 24 / multiplier;

    handleStartAwayTimerLogic(deltaStart, deltaEnd, dayLen);

    CustomConsole.print("Away Mode lighting has been set!");
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
   * @param shouldTurnAwayOn the away on
   */
  public void setAwayOn(boolean shouldTurnAwayOn) {
    if (shouldTurnAwayOn) {
      Context.getHouse().lockAndClose(true);
      Context.repaintHouseGraphic();
      startAwayTimer();
    } else {
      cancelAllTimers();
    }
    awayOn = shouldTurnAwayOn;
    notifyObservers();
  }

  private static void handleStartAwayTimerLogic(long deltaStart, long deltaEnd, long dayLen) {
    if (deltaStart < 0 && deltaEnd < 0) {
      // verify if both events are in the past, then we schedule them "tommorow"
      startExec =
              Executors.newSingleThreadScheduledExecutor()
                      .scheduleAtFixedRate(
                              new StartAwayLights(), deltaStart + dayLen, dayLen, TimeUnit.MILLISECONDS);
      endExec =
              Executors.newSingleThreadScheduledExecutor()
                      .scheduleAtFixedRate(
                              new EndAwayLights(), deltaEnd + dayLen, dayLen, TimeUnit.MILLISECONDS);

    } else if (deltaStart < 0) {
      // if the end is in the future but lights should be on
      new StartAwayLights().turnOnSelectedLights();
      endExec =
              Executors.newSingleThreadScheduledExecutor()
                      .scheduleAtFixedRate(new EndAwayLights(), deltaEnd, dayLen, TimeUnit.MILLISECONDS);
      //      endExec.scheduleAtFixedRate(new EndAwayLights(), deltaEnd, dayLen,
      // TimeUnit.MILLISECONDS);
    } else {
      startExec =
              Executors.newSingleThreadScheduledExecutor()
                      .scheduleAtFixedRate(
                              new StartAwayLights(), deltaStart, dayLen, TimeUnit.MILLISECONDS);
      endExec =
              Executors.newSingleThreadScheduledExecutor()
                      .scheduleAtFixedRate(new EndAwayLights(), deltaEnd, dayLen, TimeUnit.MILLISECONDS);
    }
  }

  public static void subscribe(AwayChangeObserver aw){
    awayChangeObservers.add(aw);
  }


  /** Cancel all timers. */
  public static void cancelAllTimers() {
    if (startExec != null) {
      startExec.cancel(true);
    }
    if (endExec != null) {
      endExec.cancel(true);
    }
  }

  /**
   * Gets auth timers.
   *
   * @return the auth timers
   */
  public ArrayList<CustomTimer> getAuthTimers() {
    return authTimers;
  }

  /**
   * Add to light list.
   *
   * @param roomToAdd the room to add
   */
  public void addToLightList(Room roomToAdd) {
    roomsToLight.add(roomToAdd);
  }

  /**
   * Remove from light list boolean.
   *
   * @param toRemove the to remove
   * @return the boolean
   */
  public boolean removeFromLightList(Room toRemove) {
    for (int i = 0; i < roomsToLight.size(); i++) {
      if (roomsToLight.get(i).getId() == toRemove.getId()) {
        roomsToLight.remove(i);
        return true;
      }
    }
    return false;
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

  /** Notify authorities. */
  public void notifyAuthorities() {
    CustomTimer temp = new CustomTimer();
    NotifyAuthTask tempTask = new NotifyAuthTask();

    int waitFor = intervalModel.getNumber().intValue();
    if (waitFor == 0) {
      tempTask.doNotify();
    } else {

      CustomConsole.print("Authorities will be notified in " + waitFor + " minutes.");
      authTimers.add(temp);
      authTasks.add(tempTask);

      int multiplier = getMultiplier();

      Date envTime = Context.getDateObject();
      Calendar sCal = new GregorianCalendar();
      sCal.setTime(envTime);
      sCal.set(Calendar.YEAR, 1900 + envTime.getYear());
      sCal.set(Calendar.DAY_OF_MONTH, envTime.getDate());
      sCal.set(Calendar.MONTH, envTime.getMonth());
      sCal.add(Calendar.MINUTE, waitFor);
      long deltaStart =
          (sCal.getTimeInMillis() - (Context.getDateObject().getTime())) / multiplier;
      temp.schedule(tempTask, deltaStart);
    }
  }

  private static int getMultiplier(){
    int timerDel = Context.getDelay();
    if (timerDel == 1000) {
      return 1;
    } else if (timerDel == 100) {
      return 10;
    } else if (timerDel == 10) {
      return 100;
    }
    return 1;
  }

  /** The type Start away lights. */
  public static class StartAwayLights implements Runnable {

    @Override
    public void run() {
      // start the lights
      turnOnSelectedLights();
    }

    /** Turn on selected lights. */
    public void turnOnSelectedLights() {
      CustomConsole.print("Lights have been turned on via a timer!");
      for (Room room : roomsToLight) {
        room.turnOnLights();
      }
      for (Room r : Context.getHouse().getRooms()) {
        for (Wall w : r.getAllWalls()) {
          if (w.getType() == WallType.OUTSIDE) {
            OutsideWall tmp = (OutsideWall) w;
            tmp.turnLightsOn();
          }
        }
      }
      Context.repaintHouseGraphic();
    }
  }

  /** The type End away lights. */
  public static class EndAwayLights implements Runnable {

    @Override
    public void run() {
      // end the lights
      turnOffSelectedLights();
    }

    /** Turn off selected lights. */
    public void turnOffSelectedLights() {
      CustomConsole.print("Light have been turned off via a timer!");
      for (Room room : roomsToLight) {
        room.turnOffLights();
      }
      for (Room r : Context.getHouse().getRooms()) {
        for (Wall w : r.getAllWalls()) {
          if (w.getType() == WallType.OUTSIDE) {
            OutsideWall tmp = (OutsideWall) w;
            tmp.turnLightsOff();
          }
        }
      }
      Context.repaintHouseGraphic();
    }
  }

  private void notifyObservers(){
    for (AwayChangeObserver a : awayChangeObservers ) {
        a.update(this.isAwayOn());
    }
  }

  private class NotifyAuthTask extends TimerTask {

    @Override
    public void run() {
      doNotify();
    }

    /** Turn off selected lights. */
    public void doNotify() {
      CustomConsole.print("The authorities have been notified!");
    }
  }
}
