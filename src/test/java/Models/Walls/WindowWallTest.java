package Models.Walls;

import org.junit.jupiter.api.Test;

public class WindowWallTest {

    @Test
    void obstructWindow(){
        WindowWall w = new WindowWall();
        w.setWindowObstructed(true);
        assert w.windowObstructed == true;
    }
}
