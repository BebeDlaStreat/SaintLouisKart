package fr.bebedlastreat.slkart.kart;

import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Data
public abstract class Kart {

    public abstract String getName();
    public abstract String getIconPath();
    public abstract int getSpeed();
    public abstract int getAcceleration();
    public abstract int getRotationSpeed();

    private BufferedImage icon;

    public void load() {
        try {
            icon = ImageIO.read(getClass().getResourceAsStream(getIconPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
