package Models;

import Views.CustomConsole;

import javax.swing.*;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/** The type Heating model. */
public class HeatingModel {

  private final ArrayList<HeatingZone> heatingZones = new ArrayList<HeatingZone>();
  private MonthDay summerStart;
  private MonthDay winterStart;
  private final SpinnerNumberModel awayTempSpinner;
  private final SpinnerNumberModel dangerTempSpinner;

  /** Instantiates a new Heating model. */
  public HeatingModel() {
    awayTempSpinner = new SpinnerNumberModel();
    dangerTempSpinner = new SpinnerNumberModel();
    this.winterStart = MonthDay.of(1, 1); // Default Winter Starts Jan 1
    this.summerStart = MonthDay.of(7, 1); // Default Summer Starts July 2nd
  }

  /**
   * Gets danger temp spinner.
   *
   * @return the danger temp spinner
   */
  public SpinnerNumberModel getDangerTempSpinner() {
    return dangerTempSpinner;
  }

  /**
   * Create heating zone.
   *
   * @param rooms the rooms
   * @param name the name
   */
  public void createHeatingZone(Room[] rooms, String name) {
    heatingZones.add(new HeatingZone(rooms, this, name, dangerTempSpinner, false));
  }

  public MonthDay getSummerStartAsMD() {
    return this.summerStart;
  }

  public MonthDay getWinterStartAsMD() {
    return this.winterStart;
  }

  public ArrayList<HeatingZone> getAllHeatingZonesIncludingRooms() {
    ArrayList<HeatingZone> temp = this.heatingZones;
    for (Room r: Context.getHouse().getRooms()) {
      if (!r.getIsInHeatingZone()) {
        temp.add(new HeatingZone(new Room[] {r}, this, r.getName(), this.dangerTempSpinner, true));
        r.setIsInHeatingZone(false);
      }
    }
    return temp;
  }

  public HeatingZone getAllRoomsNotInZonesAsHeatingZones() {
    ArrayList<Room> temp = new ArrayList<Room>();
    for (Room r: Context.getHouse().getRooms()) {
      if (!r.getIsInHeatingZone()) {
        temp.add(r);
      }
    }
    Room[] tempAsArray = new Room[temp.size()];
    HeatingZone result = new HeatingZone(temp.toArray(tempAsArray), this, "All Rooms", this.dangerTempSpinner, true);

    for (Room r: result.getRooms()) {
      r.setIsInHeatingZone(false);
    }
    return result;
  }

  public HeatingZone getRoomAsHeatingZone(int roomdID) {
    for (Room r: Context.getHouse().getRooms()) {
      if (roomdID == r.getId()) {
        HeatingZone result =  new HeatingZone(new Room[] {r}, this, r.getName(), this.dangerTempSpinner, true);
        result.getRooms().get(0).setIsInHeatingZone(false);
        return result;
      }
    }
    return null;
  }

  /**
   * Gets away temp spinner.
   *
   * @return the away temp spinner
   */
  public SpinnerNumberModel getAwayTempSpinner() {
    return this.awayTempSpinner;
  }

  /**
   * Gets heating zones.
   *
   * @return the heating zones
   */
  public ArrayList<HeatingZone> getHeatingZones() {
    return this.heatingZones;
  }

  /**
   * Gets summer start.
   *
   * @return the summer start
   */
  public Date getSummerStart() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, this.summerStart.getMonthValue() - 1);
    cal.set(Calendar.DAY_OF_MONTH, this.summerStart.getDayOfMonth());

    return cal.getTime();
  }
  /**
   * Update summer start.
   *
   * @param date the date
   */
  public void updateSummerStart(Date date) {
    this.summerStart = MonthDay.of(date.getMonth() + 1, date.getDate());
    CustomConsole.print("Summer start date has been set to: " + this.summerStart.toString());
  }
  /**
   * Update winter start.
   *
   * @param date the date
   */
  public void updateWinterStart(Date date) {
    this.winterStart = MonthDay.of(date.getMonth() + 1, date.getDate());
    CustomConsole.print("Winter start date has been set to: " + this.winterStart.toString());
  }

  /**
   * Gets winter start.
   *
   * @return the winter start
   */
  public Date getWinterStart() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, this.winterStart.getMonthValue() - 1);
    cal.set(Calendar.DAY_OF_MONTH, this.winterStart.getDayOfMonth());

    return cal.getTime();
  }

  /**
   * Set away mode temp. First we get all rooms. Then we remove all the rooms that are contained
   * within a zone. We set the temp of the zones. We then set the temps of the loner rooms.
   */
  public void setAwayModeTemp(boolean awayIsOn) {

    CustomConsole.print("DEBUG! -> SET AWAY MODE TEMP");
//    int tempToSet;
//
//
//    ArrayList<Room> allRooms = Context.getHouse().getRooms();
//
//    ArrayList<Room> copyRooms = new ArrayList<>();
//    for (Room rm : allRooms ) {
//        copyRooms.add(rm);
//    }
//
//
//    for (int i = 0; i < heatingZones.size(); i++) {
//      for (int j = 0; j < copyRooms.size(); j++) {
//
//        if (heatingZones.get(i).containsRoom(copyRooms.get(j))) {
//          copyRooms.remove(j);
//        }
//      }
//      if (awayIsOn) {
//        tempToSet = (int) awayTempSpinner.getValue();
//      } else {
//        tempToSet = heatingZones.get(i).getLastTemp();
//      }
//      heatingZones.get(i).setTemperature(tempToSet);
//    }

    for (HeatingZone h : heatingZones ) {
      System.out.println("iter");
      if(awayIsOn){
        h.setTemperature(66);
      }
      else{
        h.setTemperature(-66);
      }
    }
    // TODO: Change room temps here

  }
}
