package Walls;

import Enums.WallType;

public class Wall {
	private WallType type;
	
	public Wall(){
		this.type = WallType.WALL;
	}
	
	public Wall(WallType type) {
		this.type = type;
	}
	
	public WallType getType() {
		return type;
	}
}
