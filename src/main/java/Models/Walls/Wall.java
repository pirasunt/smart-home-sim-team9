package Models.Walls;

import Enums.WallType;

/**
 * The type Wall.
 */
public class Wall {
	private WallType type;

	/**
	 * Instantiates a new Wall.
	 */
	public Wall(){
		this.type = WallType.WALL;
	}

	/**
	 * Instantiates a new Wall.
	 *
	 * @param type the type of the wall being instantiated
	 */
	public Wall(WallType type) {
		this.type = type;
	}

	/**
	 * Gets type of the wall.
	 *
	 * @return the type of the wall
	 */
	public WallType getType() {
		return type;
	}
}
