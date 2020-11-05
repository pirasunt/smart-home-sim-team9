package Models.Walls;

import org.junit.jupiter.api.Test;

public class WindowWallTest {
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
    assert ww.isWindowOpen() == true;

    ww.setWindowOpen(false);
    ww.openWindow();
    assert ww.isWindowOpen() == false;
  }
}
