package Models;

import Custom.RoomExistsInHeatingZoneException;
import Enums.WallType;
import Models.Walls.Wall;
import Models.Walls.WindowWall;
import Views.CustomConsole;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HeatingZone {

  private int temperature;
  private final ArrayList<Room> rooms = new ArrayList<Room>();
  private final MonthDay summerStart;
  private final MonthDay winterStart;
  private boolean acOn;
  private boolean heaterOn;
  private final String name;

  public HeatingZone(Room[] rooms, MonthDay summerStart, MonthDay winterStart, String name) {
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

    this.temperature = EnvironmentModel.getOutsideTemp();
  }

  /**
   * Verify if the context time is currently in the summer
   *
   * @return true if it is summer, false if it is winter.
   */
  private boolean isSummer() {
    MonthDay currentMonthDay = MonthDay.of(Context.getDateObject().getMonth()+1, Context.getDateObject().getDate());

    //If summer start date is before winter date
    if(summerStart.isBefore(winterStart)){
      return currentMonthDay.isAfter(summerStart) && currentMonthDay.isBefore(winterStart);
    } else {
      //If summer start date is after winter date
      return currentMonthDay.isAfter(summerStart);
    }

  }

  /**
   * Verify if the temperature outside is hotter than the temperature inside
   *
   * @return true if it is hotter outside, false if it is colder outside.
   */
  private boolean hotOutside() {
      return EnvironmentModel.getOutsideTemp() > this.temperature;
  }

  /**
   * Verify if we want to increase of decrease the temperature in the house.
   *
   * @param desiredTemp the temperature desired.
   * @return true if the current temperature is higher than desired, false if not.
   */
  private boolean hotInside(int desiredTemp) {
      return this.temperature > desiredTemp;
  }

  private void openAllWindowsInZone() {
    for (Room room : rooms) {
      for (Wall wall : room.getAllWalls()) {
        if (wall.getType() == WallType.WINDOWS) {
          if (EnvironmentModel.getSimulationRunning()) ((WindowWall) wall).openWindow();
          else ((WindowWall) wall).setWindowOpen(true);
        }
      }
    }
  }

  private void closeAllWindowsInZone() {
    for (Room room : rooms) {
      for (Wall wall : room.getAllWalls()) {
        if (wall.getType() == WallType.WINDOWS) {
          if (EnvironmentModel.getSimulationRunning()) ((WindowWall) wall).closeWindow();
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
    } catch (RoomExistsInHeatingZoneException e) {
    }
  }

  public void removeRoom(Room room) {
    rooms.remove(room);
    room.setIsInHeatingZone(false);
  }

  public int getTemperature() {
    return this.temperature;
  }

  public void setTemperature(int newTemp) {

    HeatingZone zone = this;

    Timer timer =
        new Timer(
            1000,
            new ActionListener() {

              @Override
              public void actionPerformed(ActionEvent e) {
                if (zone.getTemperature() > newTemp && EnvironmentModel.getSimulationRunning()) {
                    zone.decrementTemperature();
                } else if (zone.getTemperature() < newTemp && EnvironmentModel.getSimulationRunning()) {
                    zone.incrementTemperature();
                }

                // This handles opening and closing of windows/ac in summer
                if (isSummer() && hotOutside()) {
                  if (!acOn) {
                    acOn = true;
                    heaterOn = false;
                    closeAllWindowsInZone();
                    CustomConsole.print(
                        "The current temperature in zone :"
                            + zone.getName()
                            + " is "
                            + zone.getTemperature());
                    CustomConsole.print(
                        "The outside temperature is hotter and so the AC has been turned on, and windows closed.");
                  }
                  switch (Context.getDelay()) {
                    case 1000:
                      ((Timer) e.getSource()).setDelay(10000);
                      break;
                    case 100:
                      ((Timer) e.getSource()).setDelay(1000);
                      break;
                    case 10:
                      ((Timer) e.getSource()).setDelay(100);
                      break;
                  }
                }
                else if (isSummer() && !hotOutside()) {
                  if (acOn) {
                    acOn = false;
                    heaterOn = false;
                    openAllWindowsInZone();
                    CustomConsole.print(
                        "The current temperature in zone :"
                            + zone.getName()
                            + " is "
                            + zone.getTemperature());
                    CustomConsole.print(
                        "The outside temperature is colder and so the AC has been turned off, and windows opened.");
                  }
                  switch (Context.getDelay()) {
                    case 1000:
                      ((Timer) e.getSource()).setDelay(20000);
                      break;
                    case 100:
                      ((Timer) e.getSource()).setDelay(2000);
                      break;
                    case 10:
                      ((Timer) e.getSource()).setDelay(200);
                      break;
                  }
                }
                // This handles heater in winter
                if (!isSummer() && hotInside(newTemp)) {
                    if (heaterOn) {
                        acOn = false;
                        heaterOn = false;
                        closeAllWindowsInZone();
                        CustomConsole.print(
                                "The current temperature in zone :"
                                        + zone.getName()
                                        + " is "
                                        + zone.getTemperature());
                        CustomConsole.print(
                                "It is winter but it is already hotter than desired inside, nothing will change and the temperature will drop naturally.");
                    }
                  switch (Context.getDelay()) {
                    case 1000:
                      ((Timer) e.getSource()).setDelay(20000);
                      break;
                    case 100:
                      ((Timer) e.getSource()).setDelay(2000);
                      break;
                    case 10:
                      ((Timer) e.getSource()).setDelay(200);
                      break;
                    }
                }
                else if (!isSummer() && !hotInside(newTemp)) {
                    if (!heaterOn) {
                        acOn = false;
                        heaterOn = true;
                        closeAllWindowsInZone();
                        CustomConsole.print(
                                "The current temperature in zone :"
                                        + zone.getName()
                                        + " is "
                                        + zone.getTemperature());
                        CustomConsole.print(
                                "It is winter and the temperature in the zone is too cold, the heater has been turned on.");
                    }
                  switch (Context.getDelay()) {
                    case 1000:
                      ((Timer) e.getSource()).setDelay(10000);
                      break;
                    case 100:
                      ((Timer) e.getSource()).setDelay(1000);
                      break;
                    case 10:
                      ((Timer) e.getSource()).setDelay(100);
                      break;
                  }
                }
              }
            });

    timer.start();
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

  public ArrayList<Room> getRooms() {
    return this.rooms;
  }
}
