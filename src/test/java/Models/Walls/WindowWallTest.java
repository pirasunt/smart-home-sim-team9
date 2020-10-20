package Models.Walls;

import org.junit.jupiter.api.Test;

public class WindowWallTest {
    @Test
    void obstructWindow(){
        WindowWall ww = new WindowWall();
        ww.setWindowObstructed(true);
        assert ww.isWindowObstructed() == true;
    }
}
