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

  // 3.2 -> modify
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

  // unit test for 3.2 -> add
  @Test
  void addUserProfile() throws Exception {
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "Old", 0);
    House house = new House();
    HouseGraphic hg = new HouseGraphic(house);
    EnvironmentModel env = EnvironmentModel.createSimulation(house, hg, u);
    env.addUserProfile(u);
    assert env.getUserByID(u.getProfileID()) != null;
  }

  // unit test for 3.3
  @Test
  void deleteUserProfile() throws Exception {
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "Rold", 0);
    UserProfileModel u1 = new UserProfileModel(ProfileType.ADULT, "Mike", 0);
    House house = new House();
    HouseGraphic hg = new HouseGraphic(house);
    EnvironmentModel env = EnvironmentModel.createSimulation(house, hg, u);
    env.addUserProfile(u1);
    env.removeUserProfile(u1);
    assert env.getAllUserProfiles().length == 1;
  }
}
