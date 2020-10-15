package Custom;

import Models.House;
import Models.Room;
import Walls.OutsideWall;
import Walls.RoomWall;
import Walls.Wall;
import Walls.WindowWall;
import com.thoughtworks.xstream.XStream;

public class CustomXStream extends XStream {
    public CustomXStream(){
        XStream.setupDefaultSecurity(this);
        super.allowTypes(new Class[]{House.class, Room.class, Wall.class, WindowWall.class, RoomWall.class, OutsideWall.class});
        super.alias("House",House.class);
        super.alias("OutsideWall",OutsideWall.class);
		super.alias("Room",Room.class);
		super.alias("RoomWall",RoomWall.class);
		super.alias("Wall",Wall.class);
		super.alias("WindowWall",WindowWall.class);
    }
}
