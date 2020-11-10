package Models.Walls;

import Models.EnvironmentModel;
import Models.House;
import Models.UserProfileModel;
import Views.CustomConsole;
import Views.HouseGraphic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WindowWallTest {
  @BeforeAll
  static void buildContext(){
    House house = new House();
    EnvironmentModel.createSimulation(house, new HouseGraphic(house), new UserProfileModel[1]);
    CustomConsole.init();
  }

  // use case 3.11
  @Test
  void obstructWindow() {
    WindowWall ww = new WindowWall();
    ww.setWindowObstructed(true);
    assert ww.isWindowObstructed() == true;

    ww.obstructWindow();
    assert (ww.isWindowObstructed() == true);
  }

  @Test
  void unobstructWindow() {
    WindowWall ww = new WindowWall();
    ww.setWindowObstructed(false);
    assert ww.isWindowObstructed() == false;

    ww.unobstructWindow();
    assert ww.isWindowObstructed() == false;
  }

  @Test
  void openCloseObstructedWindows(){
    WindowWall ww = new WindowWall();
    ww.setWindowObstructed(true);

    ww.setWindowOpen(true);
    ww.closeWindow();
    assert ww.isWindowOpen() == false;

    ww.setWindowOpen(false);
    ww.openWindow();
    assert ww.isWindowOpen() == true;
  }
}
