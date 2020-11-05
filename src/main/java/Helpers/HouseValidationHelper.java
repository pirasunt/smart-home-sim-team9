package Helpers;

import Enums.WallType;
import Models.House;
import Models.Room;
import Models.Walls.RoomWall;

import java.util.ArrayList;

public class HouseValidationHelper {

    public static boolean ValidateHouse(House house) {
        if (!CheckForDuplicateIds(house)) return false;
        if (!ValidateRoomReferences(house)) return false;

        return true;
    }

    private static boolean ValidateRoomReferences(House house) {
        for (Room r : house.getRooms()) {
            if (r.getLeftWall().getType() == WallType.DOOR) {
                int roomId = r.getId();
                int otherId = ((RoomWall) r.getLeftWall()).getConnectedRoom();
                for (Room r2 : house.getRooms()) {
                    if (r2.getRightWall().getClass() != RoomWall.class) return false;
                    if (r2.getId() == otherId && ((RoomWall) r2.getRightWall()).getConnectedRoom() != roomId)
                        return false;
                }
            } else if (r.getBottomWall().getType() == WallType.DOOR) {
                int roomId = r.getId();
                int otherId = ((RoomWall) r.getBottomWall()).getConnectedRoom();
                for (Room r2 : house.getRooms()) {
                    if (r2.getTopWall().getClass() != RoomWall.class) return false;
                    if (r2.getId() == otherId && ((RoomWall) r2.getTopWall()).getConnectedRoom() != roomId)
                        return false;
                }
            } else if (r.getRightWall().getType() == WallType.DOOR) {
                int roomId = r.getId();
                int otherId = ((RoomWall) r.getRightWall()).getConnectedRoom();
                for (Room r2 : house.getRooms()) {
                    if (r2.getLeftWall().getClass() != RoomWall.class) return false;
                    if (r2.getId() == otherId && ((RoomWall) r2.getLeftWall()).getConnectedRoom() != roomId)
                        return false;
                }
            } else if (r.getTopWall().getType() == WallType.DOOR) {
                int roomId = r.getId();
                int otherId = ((RoomWall) r.getTopWall()).getConnectedRoom();
                for (Room r2 : house.getRooms()) {
                    if (r2.getBottomWall().getClass() != RoomWall.class) return false;
                    if (r2.getId() == otherId && ((RoomWall) r2.getBottomWall()).getConnectedRoom() != roomId)
                        return false;
                }
            }
        }

        return true;
    }

    private static boolean CheckForDuplicateIds(House house) {
        ArrayList<Integer> existingIds = new ArrayList<Integer>();
        for (Room r : house.getRooms()) {
            if (existingIds.contains(r.getId()))
                return false;
            existingIds.add(r.getId());
        }
        return true;
    }
}
