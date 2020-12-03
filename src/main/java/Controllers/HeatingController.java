package Controllers;

import Models.*;
import Views.HeatZoneCreator;
import Views.HeatingModule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HeatingController {

    private HeatingModel heatingModel;
    private HeatingModule heatingView;

    public HeatingController(HeatingModel m, HeatingModule v) {
        this.heatingModel = m;
        this.heatingView = v;

        heatingView.createHeatingZoneListener(new HeatingZoneCreationListener());
    }

    public void createHeatingZone(String zoneName, Room[] rooms) {
        heatingModel.createHeatingZone(rooms, zoneName);
    }

    public ArrayList<HeatingZone> getHeatingZones() {
        return this.heatingModel.getHeatingZones();
    }

    public Room[] getAvailableRooms() {
        ArrayList<Room> allRooms = Context.getHouse().getRooms();
        ArrayList<Room> result = new ArrayList<>();

        for (Room room : allRooms) {
            if (isRoomAvailable(room)) {
                result.add(room);
            }
        }

        Room[] resultArr = new Room[result.size()];
        return resultArr;
    }

    private boolean isRoomAvailable(Room room) {
        for (HeatingZone zone : getHeatingZones()) {
            if (zone.getRooms().contains(room)) {
                return false;
            }
        }

        return true;
    }


    private class HeatingZoneCreationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            HeatZoneCreator heatZoneDialog = new HeatZoneCreator(new HeatingController(heatingModel, heatingView));
            heatZoneDialog.setLocationRelativeTo(null);
            heatZoneDialog.setVisible(true);
        }
    }

}
