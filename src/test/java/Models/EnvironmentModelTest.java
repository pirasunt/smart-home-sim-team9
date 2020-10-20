package Models;

import Custom.CustomXStream;
import Enums.ProfileType;
import Views.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;

public class EnvironmentModelTest {

    @BeforeEach
    public void initEach(){
        Console c = new Console();
        EnvironmentModel.resetInstance();
    }

    @Test
    void testOutsideTemperature() {
        UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", -1);
        EnvironmentModel env = EnvironmentModel.createSimulation(new House(), u);
        env.setTemperature(23);
        assert env.getOutsideTemp() == 23;
    }

    @Test
    void setHouseLocation(){
        CustomXStream cxs = new CustomXStream();
        House testHouse;
        testHouse = (House) cxs.fromXML(new File("House.xml"));
        UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
        EnvironmentModel env = EnvironmentModel.createSimulation(testHouse,u);
        env.setCurrentUser(u);
        assert env.getCurrentUser().getRoomID() ==  2;
    }

    @Test
    void updateHouseLocation(){
        CustomXStream cxs = new CustomXStream();
        House testHouse;
        testHouse = (House) cxs.fromXML(new File("House.xml"));
        UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
        EnvironmentModel env = EnvironmentModel.createSimulation(testHouse,u);
        env.setCurrentUser(u);
        UserProfileModel tmp = env.getCurrentUser().modifyLocation(3);
        assert tmp.getRoomID() ==  3;
    }

    @Test
    void setDateTime(){
        CustomXStream cxs = new CustomXStream();
        House testHouse;
        testHouse = (House) cxs.fromXML(new File("House.xml"));
        UserProfileModel u = new UserProfileModel(ProfileType.ADULT, "James", 2);
        EnvironmentModel env = EnvironmentModel.createSimulation(testHouse,u);
        Date d = new Date();
        env.setTime(d);
        assert  env.getDateObject().equals(d);
    }
}
