package Custom.CustomXStream;

import Models.*;
import Models.Walls.*;
import com.thoughtworks.xstream.XStream;

/** The type Custom XStream. */
public class CustomHouseXStream extends XStream {
  /** Instantiates a new Custom XStream with proper . */
  public CustomHouseXStream() {
    XStream.setupDefaultSecurity(this);
    super.allowTypes(
        new Class[] {
          House.class, Room.class, Wall.class, WindowWall.class, RoomWall.class, OutsideWall.class
        });
    super.alias("House", House.class);
    super.alias("OutsideWall", OutsideWall.class);
    super.alias("Room", Room.class);
    super.alias("RoomWall", RoomWall.class);
    super.alias("Wall", Wall.class);
    super.alias("WindowWall", WindowWall.class);
  }
}
