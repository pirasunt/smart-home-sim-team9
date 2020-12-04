package Models;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HeatingModel {

  private final ArrayList<HeatingZone> heatingZones = new ArrayList<HeatingZone>();
  private Date summerStart;
  private Date winterStart;
  private int topThreshold;
  private int bottomThreshhold;
  private final SpinnerNumberModel awayTempSpinner;
  private final SpinnerNumberModel dangerTempSpinner;

  public HeatingModel() {
    awayTempSpinner = new SpinnerNumberModel();
    dangerTempSpinner = new SpinnerNumberModel();
    this.winterStart = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
    this.summerStart = new GregorianCalendar(2020, Calendar.JULY, 1).getTime();
  }

  public SpinnerNumberModel getDangerTempSpinner() {
    return dangerTempSpinner;
  }

  public void createHeatingZone(Room[] rooms, String name) {
    heatingZones.add(new HeatingZone(rooms, summerStart, winterStart, name));
  }

  public SpinnerNumberModel getAwayTempSpinner() {
    return this.awayTempSpinner;
  }

  public ArrayList<HeatingZone> getHeatingZones() {
    return this.heatingZones;
  }

  public Date getSummerStart() {
    return summerStart;
  }
  public void updateSummerStart(Date date){
    this.summerStart = date;
  }
  public void updateWinterStart(Date date){
    this.winterStart = date;
  }

  public Date getWinterStart() {
    return winterStart;
  }
}

