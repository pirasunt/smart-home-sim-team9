package Models;

import Models.Walls.Wall;
import Views.CustomConsole;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** The type Room. */
public class Room {
  private final Wall leftWall, rightWall, topWall, bottomWall;
  private final String name;
  private final int id;
  private int temperature = 0;
  private JLabel roomTempLabel;
  private boolean lightsOn = false;
  private boolean isInHeatingZone = false;
  private boolean isTempOverriden = false;

  /**
   * Instantiates a new Room.
   *
   * @param name the name of the room
   * @param left the left wall
   * @param bottom the bottom wall
   * @param right the right wall
   * @param top the top wall
   * @param id the id of the room
   */
  public Room(String name, Wall left, Wall bottom, Wall right, Wall top, int id) {
    this.name = name;
    this.leftWall = left;
    this.rightWall = right;
    this.topWall = top;
    this.bottomWall = bottom;
    this.id = id;
  }

  /**
   * Gets left wall.
   *
   * @return the left wall
   */
  public Wall getLeftWall() {
    return leftWall;
  }

  /**
   * Is temp overriden boolean.
   *
   * @return the boolean
   */
  public boolean isTempOverriden() {
    return isTempOverriden;
  }

  /**
   * Set room temp setting.
   *
   * @param value the value
   */
  public void setRoomTempSetting(boolean value) {
    this.isTempOverriden = value;
  }

  /**
   * Gets right wall.
   *
   * @return the right wall
   */
  public Wall getRightWall() {
    return rightWall;
  }

  /**
   * Gets top wall.
   *
   * @return the top wall
   */
  public Wall getTopWall() {
    return topWall;
  }

  /**
   * Gets bottom wall.
   *
   * @return the bottom wall
   */
  public Wall getBottomWall() {
    return bottomWall;
  }

  /**
   * Gets name wall.
   *
   * @return the name wall
   */
  public String getName() {
    return name;
  }

  /**
   * Gets id of the room.
   *
   * @return the id of the room
   */
  public int getId() {
    return id;
  }

  /**
   * Gets temperature in the room.
   *
   * @return the temperature of the room
   */
  public int getTemperature() {
    return temperature;
  }

  /**
   * Sets temperature of the room.
   *
   * @param temperature the temperature of the room
   */
  public void setTemperature(int temperature) {
    if (!isTempOverriden) { // Can't auto-set room temp if temp setting is overridden
      this.temperature = temperature;
      if (roomTempLabel == null) this.roomTempLabel = new JLabel();
      this.roomTempLabel.setText(this.temperature + " °C");
    }
  }

  /**
   * Get room temp label j label.
   *
   * @return the j label
   */
  public JLabel getRoomTempLabel() {
    return this.roomTempLabel;
  }

  /**
   * Manual set temperature.
   *
   * @param newTemp the new temp
   */
  public void manualSetTemperature(int newTemp) {

    if (!EnvironmentModel.getSimulationRunning()) {
      this.temperature = newTemp;
      if (isTempOverriden) this.roomTempLabel.setText(temperature + " °C [OVERRIDDEN]");
      else this.roomTempLabel.setText(temperature + " °C");

    } else {
      new Timer(
              Context.getDelay(),
              new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                  if (temperature > newTemp) {
                    temperature--;

                    if (isTempOverriden) roomTempLabel.setText(temperature + " °C [OVERRIDDEN]");
                    else roomTempLabel.setText(temperature + " °C");
                  } else if (temperature < newTemp) {
                    temperature++;

                    if (isTempOverriden) roomTempLabel.setText(temperature + " °C [OVERRIDDEN]");
                    else roomTempLabel.setText(temperature + " °C");
                  } else if (temperature == newTemp) {
                    ((Timer) e.getSource()).stop();
                  }
                }
              })
          .start();
    }
  }

  /**
   * Get's a boolean value representing if the lights are on or not
   *
   * @return lightsOn the value of the lights being on or off
   */
  public boolean getLightsOn() {
    return lightsOn;
  }

  /**
   * Sets a new value for if the lights are on or off
   *
   * @param value true if trying to turn lights on, false if off
   */
  public void setLightsOn(boolean value) {
    this.lightsOn = value;
    Context.repaintHouseGraphic();
  }

  /** Method to be used when lights are turned off while the simulator is running */
  public void turnOffLights() {
    if (lightsOn) {
      lightsOn = false;
      CustomConsole.print("Lights in room: " + name + " have been turned off.");
    }
    Context.repaintHouseGraphic();
  }

  /** Method to be used when lights are turned on while the simulator is running */
  public void turnOnLights() {
    if (!lightsOn) {
      lightsOn = true;
      CustomConsole.print("Lights in room: " + name + " have been turned on.");
    }
    Context.repaintHouseGraphic();
  }

  /**
   * Returns which users are located in the room represented by this instance of Room.
   *
   * @param environment The EnvironmentModel singleton which stores all user profiles that exist in
   *     the simulation.
   * @return an array containing all the UserProfileModel objects with a room id equal to the one of
   *     this Room instance.
   */
  public ArrayList<UserProfileModel> getAllUsersInRoom(EnvironmentModel environment) {
    ArrayList<UserProfileModel> users = new ArrayList<UserProfileModel>();

    for (UserProfileModel user : environment.getAllUserProfiles()) {
      if (user.getRoomID() == this.id) {
        users.add(user);
      }
    }

    return users;
  }

  /**
   * Get all walls wall [ ].
   *
   * @return the wall [ ]
   */
  public Wall[] getAllWalls() {
    return new Wall[] {leftWall, rightWall, bottomWall, topWall};
  }

  /**
   * Is room in heating zone boolean.
   *
   * @return the boolean
   */
  public boolean isRoomInHeatingZone() {
    return this.isInHeatingZone;
  }

  /**
   * Gets is in heating zone.
   *
   * @return the is in heating zone
   */
  public boolean getIsInHeatingZone() {
    return this.isInHeatingZone;
  }

  /**
   * Sets is in heating zone.
   *
   * @param value the value
   */
  public void setIsInHeatingZone(boolean value) {
    this.isInHeatingZone = value;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
