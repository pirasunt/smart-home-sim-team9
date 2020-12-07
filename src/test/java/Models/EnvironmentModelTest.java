package Models;

import Custom.CustomXStream.CustomHouseXStream;
import Enums.ProfileType;
import Views.CustomConsole;
import Views.HouseGraphic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

public class EnvironmentModelTest {

  @BeforeEach
  public void initEach() {
    CustomConsole.init();
    EnvironmentModel.resetInstance();
  }

  // use case 3.7
  @Test
  void testOutsideTemperature() {
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", -1);
    House house = new House();
    HouseGraphic hg = new HouseGraphic(house);
    EnvironmentModel env = EnvironmentModel.createSimulation(house, hg, u);
    env.setTemperature(23);
    assert env.getOutsideTemp() == 23;
  }

  //use case 3.12
  @Test
  void setHouseLocation() {
    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse;
    testHouse = (House) cxs.fromXML(new File("House.xml"));
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
    EnvironmentModel env =
        EnvironmentModel.createSimulation(testHouse, new HouseGraphic(testHouse), u);
    env.setCurrentUser(u);
    assert Context.getCurrentUser().getRoomID() == 2;
  }

  //use case 3.12
  @Test
  void updateHouseLocation() {
    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse;
    testHouse = (House) cxs.fromXML(new File("House.xml"));
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
    EnvironmentModel env =
        EnvironmentModel.createSimulation(testHouse, new HouseGraphic(testHouse), u);
    env.setCurrentUser(u);
    UserProfileModel tmp = Context.getCurrentUser().modifyLocation(3);
    assert tmp.getRoomID() == 3;
  }


  //use case 3.5
  @Test
  void setDateTime() {
    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse;
    testHouse = (House) cxs.fromXML(new File("House.xml"));
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
    EnvironmentModel env =
        EnvironmentModel.createSimulation(testHouse, new HouseGraphic(testHouse), u);
    Date d = new Date();
    Context.setTime(d);
    assert Context.getDateObject().equals(d);
  }

  // use case 3 delivery 2
  @Test
  void testTimer() {
    UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", -1);
    House house = new House();
    HouseGraphic hg = new HouseGraphic(house);
    EnvironmentModel env = EnvironmentModel.createSimulation(house, hg, u);
    ActionListener a = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    };
    env.initializeTimer(a);
    Context.setDelay(10);
    assert Context.getDelay() == 10;
  }
}
