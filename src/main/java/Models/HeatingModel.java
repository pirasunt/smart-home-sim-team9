package Models;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

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
}
