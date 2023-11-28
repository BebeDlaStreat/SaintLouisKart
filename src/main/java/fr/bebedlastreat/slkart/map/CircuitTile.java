package fr.bebedlastreat.slkart.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CircuitTile {
    ROAD(Color.white), WALL(Color.black), SPEED(Color.GREEN), SLOW(Color.RED);

    private final Color color;

    public static CircuitTile getByColor(Color color) {
        return Arrays.stream(values()).filter(circuitTile -> circuitTile.getColor().getRGB() == color.getRGB()).findFirst().orElse(CircuitTile.WALL);
    }

    @Override
    public String toString() {
        return "CircuitTile{" +
                "color=" + color +
                '}';
    }
}
