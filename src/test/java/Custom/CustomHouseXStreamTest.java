package Custom;

import Custom.CustomXStream.CustomHouseXStream;
import Models.House;
import org.junit.jupiter.api.Test;

import java.io.File;

public class CustomHouseXStreamTest {
  @Test
  void loadsFileWithAliasesCorrectly() {
    CustomHouseXStream cxs = new CustomHouseXStream();
    House testHouse;
    testHouse = (House) cxs.fromXML(new File("House.xml"));
    assert testHouse.getRooms().size() == 9;
  }
}