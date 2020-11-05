package Models;

import Custom.CustomXStream.CustomHouseXStream;
import Enums.ProfileType;
import Models.Walls.Wall;
import Views.HouseGraphic;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomTest {

  @Test
  void changeRoomTemp() {
    Room r = new Room("Test Room", new Wall(), new Wall(), new Wall(), new Wall(), 0);
    r.setTemperature(25);
    assert r.getTemperature() == 25;
  }

  @Test
  void turnLightsOn(){
    Room room = new Room("Test Room", new Wall(), new Wall(), new Wall(), new Wall(), 0);
    room.setLightsOn(false);

    room.turnOnLights();
    assert room.getLightsOn() == true;
  }

  @Test
  void turnLightOff(){
    Room room = new Room("Test Room", new Wall(), new Wall(), new Wall(), new Wall(), 0);
    room.setLightsOn(true);

    room.turnOffLights();
    assert room.getLightsOn() == false;
  }

  @Test
  void getAllUsersInRoom(){
    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse;
    testHouse = (House) cxs.fromXML(new File("House.xml"));
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
    EnvironmentModel env =
            EnvironmentModel.createSimulation(testHouse, new HouseGraphic(testHouse), u);
    Room testRoom = testHouse.getRoomById(2);
    ArrayList<UserProfileModel> expected = new ArrayList<UserProfileModel>();
    expected.add(u);

    assertEquals(testRoom.getAllUsersInRoom(env).size(), expected.size());
    assertEquals(testRoom.getAllUsersInRoom(env).get(0).getName(), expected.get(0).getName());
    assertEquals(testRoom.getAllUsersInRoom(env).get(0).getRoomID(), expected.get(0).getRoomID());
    assertEquals(testRoom.getAllUsersInRoom(env).get(0).getProfileType(), expected.get(0).getProfileType());
  }
}
