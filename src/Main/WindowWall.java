package Main;

public class WindowWall extends Wall{
	boolean windowOpen = false;
	boolean windowObstructed = false;
	
	public WindowWall() {
		super(WallType.WINDOWS);
	}
}
