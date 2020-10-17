package Models;

import Models.Walls.*;

/**
 * The type Room.
 */
public class Room {
    private final Wall left, right, top, bottom;
    private final String name;
    private final int id;
    private int temperature = 20;


    /**
     * Instantiates a new Room.
     *
     * @param name   the name of the room
     * @param left   the left wall
     * @param bottom the bottom wall
     * @param right  the right wall
     * @param top    the top wall
     * @param id     the id of the room
     */
    public Room(String name, Wall left, Wall bottom, Wall right, Wall top, int id) {
        this.name = name;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.id = id;
    }

    /**
     * Gets left wall.
     *
     * @return the left wall
     */
    public Wall getLeft() {
        return left;
    }

    /**
     * Gets right wall.
     *
     * @return the right wall
     */
    public Wall getRight() {
        return right;
    }

    /**
     * Gets top wall.
     *
     * @return the top wall
     */
    public Wall getTop() {
        return top;
    }

    /**
     * Gets bottom wall.
     *
     * @return the bottom wall
     */
    public Wall getBottom() {
        return bottom;
    }

    /**
     * Gets name wall.
     *
     * @return the name wall
     */
    public String getName() {
        return name;
    }

    /**
     * Gets id of the room.
     *
     * @return the id of the room
     */
    public int getId() {
        return id;
    }

    /**
     * Gets temperature in the room.
     *
     * @return the temperature of the room
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * Sets temperature of the room.
     *
     * @param temperature the temperature of the room
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Models.Room [left=" + left + ", right=" + right + ", top=" + top + ", bottom=" + bottom + ", name=" + name
                + ", id=" + id + "]";
    }
}
