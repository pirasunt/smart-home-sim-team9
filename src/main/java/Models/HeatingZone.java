package Models;

import Custom.RoomExistsInHeatingZoneException;
import Enums.WallType;
import Models.Walls.Wall;
import Models.Walls.WindowWall;
import Views.CustomConsole;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

public class HeatingZone {

    private int temperature;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private Calendar summerStart;
    private Calendar winterStart;
    private boolean acOn;
    private boolean heaterOn;

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
        this.acOn = false;
        this.heaterOn = false;

        setTemperature(EnvironmentModel.getOutsideTemp());
    }

    public void setTemperature(int newTemp) {

        HeatingZone zone = this;

        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (zone.getTemperature() > newTemp) {
                    while (zone.getTemperature() > newTemp) {
                        zone.decrementTemperature();
                    }
                    ((Timer)e.getSource()).stop();
                }
                else if (zone.getTemperature() < newTemp) {
                    while (zone.getTemperature() < newTemp) {
                        zone.incrementTemperature();
                        CustomConsole.print(((Integer)zone.getTemperature()).toString());
                    }
                    ((Timer)e.getSource()).stop();
                }
            }
        });

        timer.start();

        //Summer + cold out -> open windows

        //Summer + warm out -> turn on ac

        //Winter + cold in -> turn on heating

        //Winter + hot in -> turn off heating

        //Temp high

        //Temp low
    }

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

    public int getTemperature() {
        return this.temperature;
    }

    public void incrementTemperature() {
        this.temperature++;
        for (Room room : rooms) {
            room.setTemperature(room.getTemperature() + 1);
        }
    }

    public void decrementTemperature() {
        this.temperature--;
        for (Room room : rooms) {
            room.setTemperature(room.getTemperature() - 1);
        }
    }
}
