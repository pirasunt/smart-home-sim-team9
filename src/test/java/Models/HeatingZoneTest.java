package Models;

import Custom.CustomXStream.CustomHouseXStream;
import Enums.ProfileType;
import Models.Walls.Wall;
import Models.Walls.WindowWall;
import Views.CustomConsole;
import Views.HouseGraphic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class HeatingZoneTest {
    @BeforeEach
    void init(){
        CustomHouseXStream cxs = new CustomHouseXStream();
        House testHouse;
        testHouse = (House) cxs.fromXML(new File("House.xml"));
        UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
        EnvironmentModel env =
                EnvironmentModel.createSimulation(testHouse, new HouseGraphic(testHouse), u);
    }

    @Test
    void AddRoomTest(){
        HeatingModel test = new HeatingModel();
        test.createHeatingZone(new Room[] {new Room("testRoom", new Wall(), new Wall(), new Wall(), new Wall(), 0)}, "test",0,0,0);
        HeatingZone testZone = test.getHeatingZones().get(0);

        assert testZone.getRooms().size() == 1;

        testZone.addRoom(new Room("testRoom", new Wall(), new Wall(), new Wall(), new Wall(), 1));

        assert testZone.getRooms().size() == 2;
    }

    @Test
    void RemoveRoomTest(){
        HeatingModel test = new HeatingModel();
        Room testRoom = new Room("testRoom", new Wall(), new Wall(), new Wall(), new Wall(), 0);
        test.createHeatingZone(new Room[] {testRoom}, "test",0,0,0);
        HeatingZone testZone = test.getHeatingZones().get(0);

        assert testZone.getRooms().size() == 1;

        testZone.removeRoom(testRoom);

        assert testZone.getRooms().size() == 0;
    }

    @Test
    void SetTemperatureTest(){
        HeatingModel test = new HeatingModel();
        Room testRoom = new Room("testRoom", new Wall(), new Wall(), new Wall(), new Wall(), 0);
        test.createHeatingZone(new Room[] {testRoom}, "test",0,0,0);
        HeatingZone testZone = test.getHeatingZones().get(0);
        testZone.setTemperature(25);

        assert testZone.getTemperature() == 25;
    }

    @Test
    void IncrementTemperatureTest(){
        HeatingModel test = new HeatingModel();
        Room testRoom = new Room("testRoom", new Wall(), new Wall(), new Wall(), new Wall(), 0);
        test.createHeatingZone(new Room[] {testRoom}, "test",0,0,0);
        HeatingZone testZone = test.getHeatingZones().get(0);

        int prevTemp = testZone.getTemperature();

        testZone.incrementTemperature();

        assert testZone.getTemperature() == prevTemp + 1;

        assert testRoom.getTemperature() == prevTemp + 1;
    }

    @Test
    void DecrementTemperatureTest(){
        HeatingModel test = new HeatingModel();
        Room testRoom = new Room("testRoom", new Wall(), new Wall(), new Wall(), new Wall(), 0);
        test.createHeatingZone(new Room[] {testRoom}, "test",0,0,0);
        HeatingZone testZone = test.getHeatingZones().get(0);

        int prevTemp = testZone.getTemperature();

        testZone.decrementTemperature();

        assert testZone.getTemperature() == prevTemp - 1;

        assert testRoom.getTemperature() == prevTemp - 1;
    }
}
