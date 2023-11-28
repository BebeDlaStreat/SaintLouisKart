package fr.bebedlastreat.slkart.entity;

import fr.bebedlastreat.slkart.main.GamePanel;
import fr.bebedlastreat.slkart.main.KeyHandler;
import fr.bebedlastreat.slkart.map.CircuitTile;
import fr.bebedlastreat.slkart.sound.Sound;
import fr.bebedlastreat.slkart.tools.ImageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Data
public class Player extends Entity {

    private GamePanel panel;
    private KeyHandler keyHandler;

    private double rotation = -Math.PI/2;
    private double speed = 0;
    private final double maxSpeed = 1000;
    private final double maxAcceleration = 10;
    private BufferedImage kart;
    private CircuitTile prevTile = CircuitTile.ROAD;
    private double prevSpeed = 0;

    public Player(GamePanel panel, KeyHandler keyHandler) {
        super(true);
        this.panel = panel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
        direction = "up";
        solidArea = new Rectangle(panel.getTileSize()/4, panel.getTileSize()/4, panel.getTileSize()/2, panel.getTileSize()/2);
    }

    public double calculateAccel() {
        return maxAcceleration - Math.pow(speed/maxSpeed, 2)*maxAcceleration;
    }

    public double calculateRevertAccel() {
        return (maxAcceleration/3) - Math.pow(-speed/(maxSpeed/3), 2)*(maxAcceleration/3);
    }

    public void setDefaultValues() {
        setX(0);
        setY(0);
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            kart = ImageIO.read(getClass().getResourceAsStream("/player/kart.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void update() {
        CircuitTile tile = panel.getCircuit().getTile(getRoundX(), getRoundY());
        if (keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed() || keyHandler.isRightPressed()) {
            if (keyHandler.isUpPressed()) {
                if (speed < maxSpeed) {
                    speed = Math.min(maxSpeed, speed + calculateAccel());
                }
            }
            if (keyHandler.isDownPressed()) {
                if (speed <= 0) {
                    speed = Math.max(-maxSpeed/3, speed - calculateRevertAccel());
                } else {
                    speed = Math.max(0, speed-15);
                }
            }
            if (keyHandler.isLeftPressed()) {
                rotation -= Math.PI/120;
                speed -= speed/250;
            }
            if (keyHandler.isRightPressed()) {
                rotation += Math.PI/120;
                speed -= speed/250;
            }
        }
        if (!getKeyHandler().isUpPressed() && !keyHandler.isDownPressed()){
            if (speed > 0) {
                speed = Math.max(0, speed - 1);
            } else if (speed < 0) {
                speed = Math.min(0, speed + 1);
            }
        }
        if (tile == CircuitTile.SPEED) {
            if (prevTile != CircuitTile.SPEED) {
                panel.playSound(Sound.SPEED);
            }
            speed = Math.min(speed + 10, maxSpeed*1.5);
        }
        if (tile == CircuitTile.SLOW) {
            if (prevTile != CircuitTile.SLOW) {
                panel.playSound(Sound.SLOW);
            }
            if (speed > 0) {
                speed = Math.max(0, speed-speed/100);
            } else if (speed < 0) {
                speed = Math.min(0, speed-speed/100);
            }
        }
        if (tile == CircuitTile.ROAD) {
            if (speed > maxSpeed) {
                speed = Math.max(0, speed - 3);
            }
            if (speed < -maxSpeed/3) {
                speed = Math.max(0, speed + 3);
            }
        }
        if (speed != 0) {
            Complex pos = new Complex(x, y);
            Complex move = ComplexUtils.polar2Complex(Math.abs(speed/panel.getCircuit().getSpeedReducer()), speed > 0 ? rotation : rotation + Math.PI);
            pos = pos.add(move);
            double prevX = x;
            double prevY = y;
            x = pos.getReal();
            y = pos.getImaginary();
            x = Math.max(0, x);
            y = Math.max(0, y);
            x = Math.min(x, panel.getCircuit().getWidth());
            y = Math.min(y, panel.getCircuit().getHeight());
            if (panel.getCircuit().getTile(getRoundX(), getRoundY()) == CircuitTile.WALL) {
                if (Math.abs(prevSpeed) > maxSpeed/4) {
                    panel.playSound(Sound.BLOCK);
                }
                x = prevX;
                y = prevY;
                speed = 0;
            }
        }
        prevTile = tile;
        prevSpeed = speed;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.drawString(String.valueOf(speed/maxSpeed * 100), 10, 20);
        g2.drawString(String.valueOf(Math.round(x)), 10, 30);
        g2.drawString(String.valueOf(Math.round(y)), 10, 40);
        Graphics2D kartGraph = kart.createGraphics();
        //kartGraph.rotate(-Math.PI/2 + rotation);

        //g2.drawImage(kartGraph.getDeviceConfiguration().createCompatibleImage(panel.getOriginalTileSize(), panel.getOriginalTileSize()),
        //        (int) (Math.round(x)-panel.getOriginalTileSize()/2), (int) Math.round(y)-panel.getOriginalTileSize()/2, panel.getOriginalTileSize(), panel.getOriginalTileSize(), null);
        g2.drawImage(ImageUtils.rotate(kart, 0),  panel.getScreenWidth()/2 - panel.getOriginalTileSize()/2, panel.getScreenHeight()/2-panel.getOriginalTileSize()/2, panel.getOriginalTileSize(), panel.getOriginalTileSize(), null);
        //kartGraph.dispose();
        //g2.fillRect((int) Math.round(x-5), (int) Math.round(y-5), 11, 11);
    }
}
