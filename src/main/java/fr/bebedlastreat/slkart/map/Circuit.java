package fr.bebedlastreat.slkart.map;

import fr.bebedlastreat.slkart.entity.Location;
import fr.bebedlastreat.slkart.panel.GamePanel;
import fr.bebedlastreat.slkart.tools.ImageUtils;
import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Data
public class Circuit {

    private final BufferedImage image;
    private final int width;
    private final int height;
    private final CircuitTile[][] tiles;
    private final BufferedImage tileImg;
    private final float sizeMultiplier;
    private final int speedReducer;
    private final Location[] spawns;

    public Circuit(String path, String tilePath, float sizeMultiplier, int speedReducer, Location[] spawns) {
        this.sizeMultiplier = sizeMultiplier;
        this.speedReducer = speedReducer;
        this.spawns = spawns;
        BufferedImage img;
        BufferedImage imgTile;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            imgTile = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(tilePath)));
        } catch (IOException ex) {
            ex.printStackTrace();
            img = null;
            imgTile = null;
        }
        assert img != null;
        assert imgTile != null;
        assert img.getWidth() == imgTile.getWidth();
        assert img.getHeight() == imgTile.getHeight();
        image = ImageUtils.resizeImage(img, (int) (img.getWidth()*sizeMultiplier), (int) (img.getHeight()*sizeMultiplier));
        tileImg = imgTile;
        width = img.getWidth();
        height = img.getHeight();
        tiles = new CircuitTile[height][width];

        loadTiles();
    }

    private void loadTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[y][x] = CircuitTile.getByColor(new Color(tileImg.getRGB(x, y)));
                if (tileImg.getRGB(x, y) == CircuitTile.ROAD.getColor().getRGB() || tileImg.getRGB(x, y) == CircuitTile.SPEED.getColor().getRGB()) {
                    tileImg.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    tileImg.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
                }
            }
        }
    }

    public CircuitTile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return CircuitTile.WALL;
        }
        return tiles[y][x];
    }
}
