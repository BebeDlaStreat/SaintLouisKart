package fr.bebedlastreat.slkart.entity;

import lombok.Data;

@Data
public class Location {

    private double x;
    private double y;
    private double rotation;

    public Location(double x, double y, double rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public Location(double x, double y) {
        this(x, y, 0);
    }

    public Location() {
        this(0, 0, 0);
    }

    public void rotate(double rad) {
        rotation += rad;
        while (rotation > Math.PI) {
            rotation -= 2*Math.PI;
        }
        while (rotation <= Math.PI) {
            rotation += 2*Math.PI;
        }
    }
}
