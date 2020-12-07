package Models;

import Models.Walls.Wall;
import Views.CustomConsole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeatingModelTest {
    @Test
    void AddHeatingZoneTest() {
        HeatingModel test = new HeatingModel();
        test.createHeatingZone(new Room[] {new Room("testRoom", new Wall(), new Wall(), new Wall(), new Wall(), 0)}, "test",0,0,0);

        assert test.getHeatingZones().size() == 1;
    }
}
