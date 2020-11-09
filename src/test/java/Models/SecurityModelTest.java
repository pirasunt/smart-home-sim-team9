package Models;

import Controllers.EnvironmentController;
import Custom.CustomXStream.CustomHouseXStream;
import Enums.ProfileType;
import Enums.WallType;
import Models.Walls.OutsideWall;
import Models.Walls.Wall;
import Models.Walls.WindowWall;
import Views.CustomConsole;
import Views.EnvironmentView;
import Views.HouseGraphic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;

public class SecurityModelTest {
  @BeforeEach
  public void initEach() {
    CustomConsole.init();
    EnvironmentModel.resetInstance();
  }

  // use case 8, "set away mode"
  @Test
  public void AwayModeTest() {
    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse;
    testHouse = (House) cxs.fromXML(new File("House.xml"));
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
    EnvironmentModel env =
        EnvironmentModel.createSimulation(testHouse, new HouseGraphic(testHouse), u);

    EnvironmentController enc = new EnvironmentController(new EnvironmentView(), env);
    EnvironmentModel.setTime(new Date());
    SecurityModel sm = new SecurityModel();

    sm.setAwayOn(true);
    assert SecurityModel.isAwayOn() == true;
  }

  // use case 8, "set away mode"
  @Test
  public void TestWindowsAndDoorsClosed() {
    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse;
    testHouse = (House) cxs.fromXML(new File("House.xml"));
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 0);
    EnvironmentModel env =
        EnvironmentModel.createSimulation(testHouse, new HouseGraphic(testHouse), u);

    EnvironmentController enc = new EnvironmentController(new EnvironmentView(), env);
    EnvironmentModel.setTime(new Date());
    SecurityModel sm = new SecurityModel();
    sm.setAwayOn(true);

    for (Room r : EnvironmentModel.getHouse().getRooms()) {
      for (Wall w : r.getAllWalls()) {
        if (w.getType() == WallType.OUTSIDE) {
          OutsideWall tmp = (OutsideWall) w;
          assert tmp.getDoorLocked() == true;
        }
        if (w.getType() == WallType.WINDOWS) {
          WindowWall tmp = (WindowWall) w;
          assert tmp.isWindowOpen() == false;
        }
      }
    }

    assert SecurityModel.isAwayOn() == true;
  }

  // use case 9
  @Test
  public void IntervalTest() {
    SecurityModel sm = new SecurityModel();
    sm.getIntervalModel().setValue(1);
    assert sm.getIntervalModel().getValue().equals(1);
  }

  @Test
  public void NotifyAuthTest() {

    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse;
    testHouse = (House) cxs.fromXML(new File("House.xml"));
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 0);
    EnvironmentModel env =
        EnvironmentModel.createSimulation(testHouse, new HouseGraphic(testHouse), u);

    EnvironmentController enc = new EnvironmentController(new EnvironmentView(), env);
    EnvironmentModel.setTime(new Date());
    SecurityModel sm = new SecurityModel();
    sm.getIntervalModel().setValue(1);
    sm.notifyAuthorities();
    assert sm.getAuthTimers().size() == 1;
    sm.getAuthTimers().get(0).cancel(); // cancel so we don't have a thread chilling
  }
}
