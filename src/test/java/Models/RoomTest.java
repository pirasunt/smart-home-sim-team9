package Models;

import Models.Walls.Wall;
import org.junit.jupiter.api.Test;

public class RoomTest {

  @Test
  void changeRoomTemp() {
    Room r = new Room("Test Room", new Wall(), new Wall(), new Wall(), new Wall(), 0);
    r.setTemperature(25);
    assert r.getTemperature() == 25;
  }
}
