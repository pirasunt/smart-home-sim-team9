package Models;

import Models.Walls.Wall;
import org.junit.jupiter.api.Test;

public class HouseTest {
    @Test
    void shouldAddRoomsCorrectly() {
        House h = new House();
        Room r = new Room("Test Room", new Wall(), new Wall(), new Wall(), new Wall(), 0);
        h.addRoom(r);
        assert h.getRooms().size() == 1;
    }
}