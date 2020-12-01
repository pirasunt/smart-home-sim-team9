package Models;

import Custom.RoomExistsInHeatingZoneException;
import Enums.WallType;
import Models.Walls.Wall;
import Models.Walls.WindowWall;

import java.util.ArrayList;
import java.util.Calendar;

public class HeatingZone {

    private int temperature;
    private ArrayList<Room> rooms;
    private Calendar summerStart;
    private Calendar winterStart;

    public HeatingZone(Room[] rooms, Calendar summerStart, Calendar winterStart) {
        int total = 0;
        int count = 0;
        for (Room room : rooms) {
            this.rooms.add(room);
            room.setIsInHeatingZone(true);
            total += room.getTemperature();
            total++;
        }
        this.summerStart = summerStart;
        this.winterStart = winterStart;
    }

//    public void setTemperature(int newTemp) {
//        //Summer + cold out -> open windows
//
//        //Summer + warm out -> turn on ac
//
//        //Winter + cold in -> turn on heating
//
//        //Winter + hot in -> turn off heating
//
//        //Temp high
//
//        //Temp low
//    }

    private void openAllWindowsInZone() {
        for (Room room : rooms) {
            for (Wall wall : room.getAllWalls()) {
                if (wall.getType() == WallType.WINDOWS) {
                    if (EnvironmentModel.getSimulationRunning())
                        ((WindowWall)wall).openWindow();
                    else
                        ((WindowWall)wall).setWindowOpen(true);
                }
            }
        }
    }

    private void closeAllWindowsInZone() {
        for (Room room : rooms) {
            for (Wall wall : room.getAllWalls()) {
                if (EnvironmentModel.getSimulationRunning())
                    ((WindowWall)wall).closeWindow();
                else
                    ((WindowWall)wall).setWindowOpen(false);
            }
        }
    }

    public void addRoom(Room room) {
        try {
            if (rooms.contains(room)) {
                throw new RoomExistsInHeatingZoneException();
            }
            rooms.add(room);
            room.setIsInHeatingZone(true);
        }
        catch (RoomExistsInHeatingZoneException e) {}
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setIsInHeatingZone(false);
    }
}
