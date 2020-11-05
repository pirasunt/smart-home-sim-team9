package Helpers;

import Models.House;
import Models.Room;
import Models.Walls.RoomWall;
import Models.Walls.Wall;
import org.junit.jupiter.api.Test;

public class HouseValidationHelperTest {

    @Test
    void duplicateIdTest(){
        House house = new House();
        house.addRoom(new Room("test1", new Wall(), new Wall(), new Wall(), new Wall(), 1));
        house.addRoom(new Room("test2", new Wall(), new Wall(), new Wall(), new Wall(), 1));

        assert HouseValidationHelper.ValidateHouse(house) == false;
    }

    @Test
    void roomReferenceTest(){
        House house = new House();
        house.addRoom(new Room("test1", new RoomWall(2), new Wall(), new Wall(), new Wall(), 1));
        house.addRoom(new Room("test2", new Wall(), new Wall(), new RoomWall(2), new Wall(), 2));

        assert HouseValidationHelper.ValidateHouse(house) == false;
    }

    @Test
    void roomReferenceWrongWallTypeTest(){
        House house = new House();
        house.addRoom(new Room("test1", new RoomWall(2), new Wall(), new Wall(), new Wall(), 1));
        house.addRoom(new Room("test2", new Wall(), new Wall(), new Wall(), new Wall(), 2));

        assert HouseValidationHelper.ValidateHouse(house) == false;
    }

}
