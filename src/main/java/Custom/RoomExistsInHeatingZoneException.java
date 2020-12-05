package Custom;

public class RoomExistsInHeatingZoneException extends Exception{
    public RoomExistsInHeatingZoneException(String msg){
        super(msg);
    }

    public RoomExistsInHeatingZoneException() {
        super("The room you are trying to add is already part of this heating zone");
    }
}
