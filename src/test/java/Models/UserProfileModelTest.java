package Models;

import Enums.ProfileType;
import Views.Console;
import Views.HouseGraphic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserProfileModelTest {
  @BeforeEach
  public void initEach() {
    Console c = new Console();
    EnvironmentModel.resetInstance();
  }

  @Test
  void modifyUserProfileName() {
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "Old", -1);
    House house = new House();
    HouseGraphic hg = new HouseGraphic(house);
    EnvironmentModel env = EnvironmentModel.createSimulation(house, hg, u);
    env.setCurrentUser(u);
    env.editProfileName(u, "New");
    assert env.getCurrentUser().getName().equals("New");
  }
}
