package Models;

import Views.CustomConsole;

import javax.swing.*;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HeatingModel {

  private final ArrayList<HeatingZone> heatingZones = new ArrayList<HeatingZone>();
  private MonthDay summerStart;
  private MonthDay winterStart;
  private int topThreshold;
  private int bottomThreshhold;
  private final SpinnerNumberModel awayTempSpinner;
  private final SpinnerNumberModel dangerTempSpinner;

  public HeatingModel() {
    awayTempSpinner = new SpinnerNumberModel();
    dangerTempSpinner = new SpinnerNumberModel();
    this.winterStart = MonthDay.of(1,1); //Default Winter Starts Jan 1
    this.summerStart = MonthDay.of(7,1); //Default Summer Starts July 2nd
  }

  public SpinnerNumberModel getDangerTempSpinner() {
    return dangerTempSpinner;
  }

  public void createHeatingZone(Room[] rooms, String name) {
    heatingZones.add(new HeatingZone(rooms, summerStart, winterStart, name, dangerTempSpinner));
  }

  public SpinnerNumberModel getAwayTempSpinner() {
    return this.awayTempSpinner;
  }

  public ArrayList<HeatingZone> getHeatingZones() {
    return this.heatingZones;
  }

  public Date getSummerStart() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, this.summerStart.getMonthValue()-1);
    cal.set(Calendar.DAY_OF_MONTH, this.summerStart.getDayOfMonth());

    return cal.getTime();
  }
  public void updateSummerStart(Date date){
    this.summerStart = MonthDay.of(date.getMonth()+1, date.getDate());
    CustomConsole.print("Summer start date has been set to: " + this.summerStart.toString());
  }
  public void updateWinterStart(Date date){
    this.winterStart = MonthDay.of(date.getMonth()+1, date.getDate());
    CustomConsole.print("Winter start date has been set to: " + this.winterStart.toString());
  }

  public Date getWinterStart() {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.MONTH, this.winterStart.getMonthValue()-1);
    cal.set(Calendar.DAY_OF_MONTH, this.winterStart.getDayOfMonth());

    return cal.getTime();
  }

}

