package Models.Walls;

import Enums.WallType;

/**
 * The type Outside wall.
 */
public class OutsideWall extends Wall{
	private boolean isGarage = false;

	/**
	 * Instantiates a new Outside wall.
	 */
	public OutsideWall() {
		super(WallType.OUTSIDE);
	}
}
