package Custom;

import Models.House;
import org.junit.jupiter.api.Test;

import java.io.File;

public class CustomXStreamTest {
    @Test
    void loadsFileWithAliasesCorrectly() {
        CustomXStream cxs = new CustomXStream();
        House testHouse;
        testHouse = (House) cxs.fromXML(new File("House.xml"));
        assert testHouse.getRooms().size() == 9;
    }

}
