package fr.bebedlastreat.slkart.content;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class KartImage {

    public static final int LOGO = 0;
    public static final int LOGO_ROUND = 1;
    public static final int MAIN_BG = 2;
    public static final int ITEM_BOMB = 3;
    public static final int ITEM_EXPLOSION = 4;
    public static final int ITEM_FLAME = 5;
    public static final int ITEM_BOX = 6;
    public static final int ITEM_SHIELD = 7;
    public static final int ITEM_SHOES = 8;

    private final BufferedImage[] images = new BufferedImage[32];
    private int index = 0;

    public KartImage() {
        loadImage("/design/logo.png");
        loadImage("/design/logo_round.png");
        loadImage("/design/main_menu.png");
        loadImage("/items/bomb.png");
        loadImage("/items/explosion.png");
        loadImage("/items/flame.png");
        loadImage("/items/itembox.png");
        loadImage("/items/shield.png");
        loadImage("/items/shoes.png");
    }

    private void loadImage(String path) {
        URL url = getClass().getResource(path);
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
            images[index] = image;
            index++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getImage(int i) {
        return images[i];
    }
}
