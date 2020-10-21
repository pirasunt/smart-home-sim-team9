package Models.Walls;

import org.junit.jupiter.api.Test;

public class WindowWallTest {
  // use case 3.11
  @Test
  void obstructWindow() {
    WindowWall ww = new WindowWall();
    ww.setWindowObstructed(true);
    assert ww.isWindowObstructed() == true;
  }
}
