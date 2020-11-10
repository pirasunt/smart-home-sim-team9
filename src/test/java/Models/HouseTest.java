package Models;

import Controllers.CoreController;
import Custom.CustomXStream.CustomHouseXStream;
import Enums.ProfileType;
import Models.Walls.Wall;
import Views.CoreView;
import Views.CustomConsole;
import Views.HouseGraphic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class HouseTest {
  @BeforeEach
  public void initEach() {
    CustomConsole.init();
    EnvironmentModel.resetInstance();
  }

  @Test
  void shouldAddRoomsCorrectly() {
    House h = new House();
    Room r = new Room("Test Room", new Wall(), new Wall(), new Wall(), new Wall(), 0);
    h.addRoom(r);
    assert h.getRooms().size() == 1;
  }

  @Test
  void autoLightsTests(){
    House testHouse = new House();
    testHouse.addRoom(new Room("test", new Wall(), new Wall(), new Wall(), new Wall(), 1));
    HouseGraphic hg = new HouseGraphic(testHouse);
    UserProfileModel testUser = new UserProfileModel(ProfileType.ADULT, "test", 1);
    CoreView cv = new CoreView();
    CoreController SHC = new CoreController(cv, EnvironmentModel.createSimulation(testHouse,hg, testUser));

    Room testRoom = testHouse.getRooms().get(0);

    //No one is in the room
    assert !testRoom.getLightsOn();

    //call to update method of Observer that is usually automatically done by the EnvironmentModel.
    SHC.update(testUser.getRoomID(), testRoom.getId());

    //Room Light should be on now
    assert testRoom.getLightsOn();
  }

}
