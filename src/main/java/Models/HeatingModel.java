package Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HeatingModel {

    private ArrayList<HeatingZone> heatingZones;
    private Date summerStart;
    private Date winterStart;
    private int topThreshold;
    private int bottomThreshhold;

    public void createHeatingZone(Room[] rooms) {
        heatingZones.add(new HeatingZone(rooms, summerStart, winterStart));
    }

}
