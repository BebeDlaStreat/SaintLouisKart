package fr.bebedlastreat.slkart;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final int originalTileSize = 16;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    private final int fps = 60;

    private final KeyHandler keyH = new KeyHandler();
    private Thread gameThread;

    private int playerX = 100;
    private int playerY = 100;
    private final int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double tickDuration = 1000000000d/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / tickDuration;
            lastTime = currentTime;

            if (delta > 1) {

                update();
                repaint();

                delta--;
            }
        }

    }

    public void update() {
        if (keyH.isUpPressed()) {
            playerY -= playerSpeed;
        }
        if (keyH.isDownPressed()) {
            playerY += playerSpeed;
        }
        if (keyH.isLeftPressed()) {
            playerX -= playerSpeed;
        }
        if (keyH.isRightPressed()) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
    }
}
