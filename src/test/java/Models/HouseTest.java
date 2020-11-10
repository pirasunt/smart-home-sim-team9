package Models;

import Controllers.CoreController;
import Custom.CustomXStream.CustomHouseXStream;
import Enums.ProfileType;
import Models.Walls.Wall;
import Views.CoreView;
import Views.HouseGraphic;
import org.junit.jupiter.api.Test;

import java.io.File;

public class HouseTest {
  @Test
  void shouldAddRoomsCorrectly() {
    House h = new House();
    Room r = new Room("Test Room", new Wall(), new Wall(), new Wall(), new Wall(), 0);
    h.addRoom(r);
    assert h.getRooms().size() == 1;
  }

  @Test
  void autoLightsTests(){
    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse = (House) cxs.fromXML(new File("House.xml"));
    HouseGraphic hg = new HouseGraphic(testHouse);
    UserProfileModel testUser = new UserProfileModel(ProfileType.ADULT, "test", 1);
    EnvironmentModel.resetInstance();
    CoreController SHC = new CoreController(new CoreView(), EnvironmentModel.createSimulation(testHouse,hg, testUser));

    Room testRoom = testHouse.getRooms().get(0);

    //No one is in the room
    assert !testRoom.getLightsOn();

    //call to update method of Observer that is usually automatically done by the EnvironmentModel.
    SHC.update(testUser.getRoomID(), testRoom.getId());

    //Room Light should be on now
    assert testRoom.getLightsOn();
  }

}
