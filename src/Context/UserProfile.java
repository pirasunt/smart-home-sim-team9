package Context;

import common.profileType;

import java.util.UUID;

public class UserProfile {

    private profileType type;
    private String name;
    private UUID roomID; //This value is actually a roomID and is where the profile user currently is

    public UserProfile(profileType type, String name, UUID id){
        this.roomID = id;
        this.type = type;
        this.name = name;
    }

    //TODO: HAVE THESE SETTERS RETURN EXCEPTIONS
    public void modifyLocation(UUID id){
        this.roomID = id;
    }

    public void editProfileName(String name) {
        this.name = name;
    }
}
