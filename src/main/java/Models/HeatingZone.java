package Models;

import Custom.RoomExistsInHeatingZoneException;
import Enums.WallType;
import Models.Walls.Wall;
import Models.Walls.WindowWall;
import Views.CustomConsole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class HeatingZone {

    private int temperature;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private Date summerStart;
    private Date winterStart;
    private boolean acOn;
    private boolean heaterOn;
    private String name;

    public HeatingZone(Room[] rooms, Date summerStart, Date winterStart, String name) {
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
        this.name = name;

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
                    }
                    ((Timer)e.getSource()).stop();
                }
            }
        });

        timer.start();
    }

    /**
     * Verify if the context time is currently in the summer
     * @return true if it is summer, false if it is winter.
     */
    private boolean isSummer() {
        if (Context.getDateObject().after(summerStart) && Context.getDateObject().before(winterStart)) {
            return true;
        }
        return false;
    }

    /**
     * Verify if the temperature outside is hotter than the temperature inside
     * @return true if it is hotter outside, false if it is colder outside.
     */
    private boolean hotOutside() {
        if (EnvironmentModel.getOutsideTemp() > this.temperature) {
            return true;
        }
        return false;
    }

    /**
     * Verify if we want to increase of decrease the temperature in the house.
     * @param desiredTemp the temperature desired.
     * @return true if the current temperature is higher than desired, false if not.
     */
    private boolean hotInside(int desiredTemp) {
        if (this.temperature > desiredTemp) {
            return true;
        }
        return false;
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
                if (wall.getType() == WallType.WINDOWS) {
                    if (EnvironmentModel.getSimulationRunning())
                        ((WindowWall) wall).closeWindow();
                    else ((WindowWall) wall).setWindowOpen(false);
                }
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

    public String getName() {
        return this.name;
    }
}
