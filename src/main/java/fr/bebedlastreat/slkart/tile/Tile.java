package fr.bebedlastreat.slkart.tile;

import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class Tile {

    private final BufferedImage image;
    private final boolean collision;

}
