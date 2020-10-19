package Models;

import Enums.profileType;
import Views.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserProfileModelTest {
    @BeforeEach
    public void initEach(){
        Console c = new Console();
        EnvironmentModel.resetInstance();
    }
    @Test
    void modifyUserProfileName() {
        UserProfileModel u = new UserProfileModel(profileType.ADULT, "Old", -1);
        EnvironmentModel env = EnvironmentModel.createSimulation(new House(), u);
        env.setCurrentUser(u);
        env.editProfileName(u, "New");
        assert env.getCurrentUser().getName().equals("New");
    }
}
