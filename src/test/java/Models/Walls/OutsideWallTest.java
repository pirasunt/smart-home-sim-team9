package Models.Walls;

import org.junit.jupiter.api.Test;

public class OutsideWallTest {
    @Test
    void doorLock(){
        OutsideWall ow = new OutsideWall();
        ow.setDoorLocked(false);

        ow.lockDoor();
        assert ow.getDoorLocked() == true;
    }

    @Test
    void doorUnlock(){
        OutsideWall ow = new OutsideWall();
        ow.setDoorLocked(true);

        ow.unlockDoor();
        assert ow.getDoorLocked() == false;
    }

    @Test
    void turnLightsOn(){
        OutsideWall ow = new OutsideWall();
        ow.setLightsOn(false);

        ow.turnLightsOn();
        assert ow.getLightsOn() == true;
    }

    @Test
    void turnLightOff(){
        OutsideWall ow = new OutsideWall();
        ow.setLightsOn(true);

        ow.turnLightsOff();
        assert ow.getLightsOn() == false;
    }
}
