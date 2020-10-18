package Models.Walls;

import Enums.WallType;

/**
 * The type Window wall.
 */
public class WindowWall extends Wall{
	/**
	 * The Window open.
	 */
	boolean windowOpen = false;
	/**
	 * The Window obstructed.
	 */
	boolean windowObstructed = false;

	/**
	 * Instantiates a new Window wall.
	 */
	public WindowWall() {
		super(WallType.WINDOWS);
	}
}
