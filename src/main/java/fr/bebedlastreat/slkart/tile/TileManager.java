package fr.bebedlastreat.slkart.tile;

import fr.bebedlastreat.slkart.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private GamePanel panel;
    private Tile[] tiles;
    private int[][] mapTileNum;

    public TileManager(GamePanel panel) {
        this.panel = panel;
        tiles = new Tile[10];
        mapTileNum = new int[panel.getMaxScreenRow()][panel.getMaxScreenCol()];

        //getTileImage();
        //loadMap("/maps/map00.txt");
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")), false);
            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")), true);
            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tiles/water.png")), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadMap(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (row < panel.getMaxScreenRow()) {
                String line = br.readLine();

                while (col < panel.getMaxScreenCol()) {
                    mapTileNum[row][col] = Integer.parseInt(String.valueOf(line.charAt(2 * col)));
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while (col < panel.getMaxScreenCol() && row < panel.getMaxScreenRow()) {
            g2.drawImage(tiles[mapTileNum[row][col]].getImage(), x, y, panel.getTileSize(), panel.getTileSize(), null);
            col++;
            x += panel.getTileSize();

            if (col == panel.getMaxScreenCol()) {
                col = 0;
                x = 0;
                row++;
                y += panel.getTileSize();
            }
        }
    }
}
