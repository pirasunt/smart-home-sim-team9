package Models;

import Models.Walls.*;

public class Room {
    private final Wall left, right, top, bottom;
    private final String name;
    private final int id;
    private int temperature = 20;


    public Room(String name, Wall left, Wall bottom, Wall right, Wall top, int id) {
        this.name = name;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.id = id;
    }

    public Wall getLeft() {
        return left;
    }

    public Wall getRight() {
        return right;
    }

    public Wall getTop() {
        return top;
    }

    public Wall getBottom() {
        return bottom;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
