package Models;

import java.util.ArrayList;
import java.util.Calendar;

public class HeatingModel {

    private ArrayList<HeatingZone> heatingZones;
    private Calendar summerStart;
    private Calendar winterStart;
    private boolean acOn;
    private boolean heaterOn;
    private int topThreshold;
    private int bottomThreshhold;

    public void createHeatingZone(Room[] rooms) {
        heatingZones.add(new HeatingZone(rooms, summerStart, winterStart));
    }

}
