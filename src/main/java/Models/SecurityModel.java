package Models;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

/** The type Security model. */
public class SecurityModel {

  private final Date startDate;
  private final Date endDate;
  private final SpinnerDateModel startModel;
  private final SpinnerDateModel endModel;
  /** The Interval model. */
  SpinnerNumberModel intervalModel;

  private boolean awayOn = false;
  // TODO Lines 6&7 change data type to match correct time object
  private String startTime = null;
  private String endTime = null;
  // TODO change data type to match correct time object
  private String alertInterval = null;
  private Room[] roomsToLight = null;

  /** Instantiates a new Security model. */
  public SecurityModel() {
    startDate = new Date();
    startModel = new SpinnerDateModel(startDate, null, null, Calendar.MINUTE);
    endDate = new Date();
    endModel = new SpinnerDateModel(endDate, null, null, Calendar.MINUTE);
    intervalModel = new SpinnerNumberModel();
    intervalModel.setMinimum(0);
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
  public boolean isAwayOn() {
    return awayOn;
  }

  /**
   * Sets away on.
   *
   * @param awayOn the away on
   */
  public void setAwayOn(boolean awayOn) {
    // TODO: check if obstructed
    // check if we're turning off away mode and that there is an obstruction
    if (awayOn) {
      EnvironmentModel.getHouse().lockAndClose(true);
      EnvironmentModel.getHouseGraphic().repaint();
    }
    this.awayOn = awayOn;
  }

  /**
   * Gets start time.
   *
   * @return the start time
   */
  public String getStartTime() {
    return startTime;
  }

  /**
   * Sets start time.
   *
   * @param startTime the start time
   */
  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  /**
   * Gets end time.
   *
   * @return the end time
   */
  public String getEndTime() {
    return endTime;
  }

  /**
   * Sets end time.
   *
   * @param endTime the end time
   */
  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  /**
   * Gets alert interval.
   *
   * @return the alert interval
   */
  public String getAlertInterval() {
    return alertInterval;
  }

  /**
   * Sets alert interval.
   *
   * @param alertInterval the alert interval
   */
  public void setAlertInterval(String alertInterval) {
    this.alertInterval = alertInterval;
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
}
