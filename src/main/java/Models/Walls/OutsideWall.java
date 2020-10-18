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

	/**
	 * Get is garage boolean value. This value tells the system if the OutsideWall is a garage door or not.
	 *
	 * @return the boolean value describing if this door is a garage door or not
	 */
	public boolean getIsGarage(){
		return this.isGarage;
	}
}
