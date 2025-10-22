package fr.bebedlastreat.slkart.panel;

import fr.bebedlastreat.slkart.collision.CollisionChecker;
import fr.bebedlastreat.slkart.entity.Location;
import fr.bebedlastreat.slkart.entity.Player;
import fr.bebedlastreat.slkart.items.Item;
import fr.bebedlastreat.slkart.main.KartGame;
import fr.bebedlastreat.slkart.main.KeyHandler;
import fr.bebedlastreat.slkart.map.Circuit;
import fr.bebedlastreat.slkart.content.SoundOld;
import fr.bebedlastreat.slkart.map.Game;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GamePanel extends KartPanel implements Runnable {

    private final int originalTileSize = 32;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 12;
    private final int maxScreenRow = 8;
    //private final int screenWidth = tileSize * maxScreenCol;
    //private final int screenHeight = tileSize * maxScreenRow;

    private final int fps = 60;

    private final KeyHandler keyHandler = new KeyHandler();
    private Thread gameThread;
    private SoundOld sound = new SoundOld();
    private final Game game;
    private final Circuit circuit;
    private final CollisionChecker collisionChecker;
    private Player player;
    private int tick = 0;

    public GamePanel(KartGame kartGame, JFrame window, Game game) {
        super(kartGame, window);
        this.game = game;
        this.circuit = game.getCircuit();
        this.collisionChecker = new CollisionChecker(this);
        this.player = new Player(this, keyHandler);
        this.setPreferredSize(window.getPreferredSize());
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        playMusic(0);
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        player.setLocation(circuit.getSpawns()[0]);
        //player.setX(circuit.getWidth()/2);
        //player.setY(circuit.getHeight()/2);

        double tickDuration = 1000000000d/fps;
        double delta = 1;
        long lastTime = System.nanoTime();
        long currentTime;

        List<Long> frames = new ArrayList<>();

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / tickDuration;
            lastTime = currentTime;

            if (delta > 1) {
                long start = System.nanoTime();
                update();
                repaint();
                long end = System.nanoTime();
                frames.add(Math.round(1000000000d/(end-start)));
                if (frames.size() >= 60) {
                    System.out.println("Possible frames: " + Math.round(frames.stream()
                            .mapToDouble(d -> d)
                            .average()
                            .orElse(0.0)));
                    frames.clear();
                }

                delta--;
            }
        }

    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        int x = player.getRoundX(), y = player.getRoundY();

        double rot = -(Math.PI/2 + player.getRotation());
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(rot, (double) getWidth() /2, (double) getHeight() /2);
        BufferedImage img = circuit.getImage();

        g2.transform(transform1);
        g2.drawImage(circuit.getImage(), (int) (getWidth()/2 - x*circuit.getSizeMultiplier()), (int) (getHeight()/2 - y*circuit.getSizeMultiplier()), circuit.getImage().getWidth(), circuit.getImage().getHeight(), null);

        // show blue collision pixels
        /*g2.setColor(Color.BLUE);
        for (int i = 0; i < player.getLastX().size(); i++) {
            g2.fillRect((int) ((int) (getWidth()/2 - x*circuit.getSizeMultiplier()) + player.getLastX().get(i)*circuit.getSizeMultiplier()), (int) ((int) (getHeight()/2 - y*circuit.getSizeMultiplier()) + player.getLastY().get(i)*circuit.getSizeMultiplier()), (int) circuit.getSizeMultiplier(), (int) circuit.getSizeMultiplier());
        }*/

        player.draw(g2);

        AffineTransform transform2 = new AffineTransform();
        transform2.rotate(-rot, (double) getWidth() /2, (double) getHeight() /2);
        g2.transform(transform2);

        g2.setColor(Color.white);

        g2.drawRect(getWidth()/2 - getTileSize()/2 + player.getSolidArea().x,
                getHeight()/2 - getTileSize()/2 + player.getSolidArea().y,
                player.getSolidArea().width, player.getSolidArea().height);

        g2.drawString(String.valueOf(player.getSpeed()/player.getMaxSpeed() * 100), 10, 20);
        g2.drawString(String.valueOf(Math.round(player.getX())), 10, 30);
        g2.drawString(String.valueOf(Math.round(player.getY())), 10, 40);

        int imageWidth = getWidth()/10;
        int imageHeight = imageWidth * circuit.getWidth()/circuit.getHeight();
        int playerX = (int) Math.round(player.getX()/circuit.getWidth() * imageWidth);
        int playerY = (int) Math.round(player.getY()/circuit.getHeight() * imageHeight);
        g2.drawImage(circuit.getTileImg(), getWidth() - imageWidth, 0, imageWidth, imageHeight, null);
        g2.setColor(Color.RED);
        g2.drawOval(getWidth() - imageWidth + playerX - 2, playerY - 2, 5, 5);
        g2.drawImage(KartGame.getInstance().getKartImage().getImage(Item.values()[tick/60 % Item.values().length].getImage()), 0, 0, 100, 100, null);

        g2.dispose();
        tick++;
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSound(int i) {
        sound.setFile(i);
        sound.play();
    }
}
