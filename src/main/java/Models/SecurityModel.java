package Models;

public class SecurityModel {

  private boolean awayOn = false;
  // TODO Lines 6&7 change data type to match correct time object
  private String startTime = null;
  private String endTime = null;
  // TODO change data type to match correct time object
  private String alertInterval = null;
  private Room[] roomsToLight = null;

  public boolean isAwayOn() {
    return awayOn;
  }

  public void setAwayOn(boolean awayOn) {
    // TODO: check if obstructed
    // check if we're turning off away mode and that there is an obstruction
    if (awayOn) {
      EnvironmentModel.getHouse().lockAndClose(true);
      EnvironmentModel.getHouseGraphic().repaint();
    }
    this.awayOn = awayOn;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getAlertInterval() {
    return alertInterval;
  }

  public void setAlertInterval(String alertInterval) {
    this.alertInterval = alertInterval;
  }

  public Room[] getRoomsToLight() {
    return roomsToLight;
  }

  public void setRoomsToLight(Room[] roomsToLight) {
    this.roomsToLight = roomsToLight;
  }
}
