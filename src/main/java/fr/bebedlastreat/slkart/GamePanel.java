package fr.bebedlastreat.slkart;

import fr.bebedlastreat.slkart.entity.Player;
import fr.bebedlastreat.slkart.map.Circuit;
import fr.bebedlastreat.slkart.sound.Sound;
import fr.bebedlastreat.slkart.tools.ImageUtils;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

@Getter
@Setter
public class GamePanel extends JPanel implements Runnable {

    private final int originalTileSize = 32;
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 12;
    private final int maxScreenRow = 8;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    private final int fps = 60;

    private final KeyHandler keyHandler = new KeyHandler();
    private Thread gameThread;
    private Sound sound = new Sound();
    private Circuit circuit = new Circuit("/maps/circuit_2.png", "/maps/circuit_2_tiles.png", this, 10, 1000);
    private Player player = new Player(this, keyHandler);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
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
        player.setX(604);
        player.setY(263);
        //player.setX(circuit.getWidth()/2);
        //player.setY(circuit.getHeight()/2);

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
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        int x = player.getRoundX(), y = player.getRoundY();

        double rot = -(Math.PI/2 + player.getRotation());
        AffineTransform transform1 = new AffineTransform();
        transform1.rotate(rot, (double) screenWidth /2, (double) screenHeight /2);
        g2.transform(transform1);
        g2.drawImage(circuit.getImage(), (int) (screenWidth/2 - x*circuit.getSizeMultiplier()), (int) (screenHeight/2 - y*circuit.getSizeMultiplier()), circuit.getImage().getWidth(), circuit.getImage().getHeight(), null);
        //ztransform.rotate(0, (double) screenWidth /2, (double) screenHeight /2);
        AffineTransform transform2 = new AffineTransform();
        transform2.rotate(-rot, (double) screenWidth /2, (double) screenHeight /2);
        g2.transform(transform2);

        player.draw(g2);
        g2.dispose();
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
