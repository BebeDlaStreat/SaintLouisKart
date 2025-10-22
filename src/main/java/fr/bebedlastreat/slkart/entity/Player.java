package fr.bebedlastreat.slkart.entity;

import fr.bebedlastreat.slkart.collision.CollisionResult;
import fr.bebedlastreat.slkart.panel.GamePanel;
import fr.bebedlastreat.slkart.main.KeyHandler;
import fr.bebedlastreat.slkart.map.Circuit;
import fr.bebedlastreat.slkart.map.CircuitTile;
import fr.bebedlastreat.slkart.content.SoundOld;
import fr.bebedlastreat.slkart.tools.ImageUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Player extends Entity {

    private GamePanel panel;
    private KeyHandler keyHandler;

    private double speed = 0;
    private final double maxSpeed = 1000;
    private final double maxAcceleration = 10;
    private BufferedImage kart;
    private int prevCollision;
    private double prevSpeed = 0;

    public Player(GamePanel panel, KeyHandler keyHandler) {
        super(panel, true);
        this.panel = panel;
        this.keyHandler = keyHandler;

        solidArea = new Rectangle(panel.getTileSize()/3, panel.getTileSize()/3, panel.getTileSize()/3, panel.getTileSize()/3);
        getPlayerImage();
    }



    public void getPlayerImage() {
        try {
            kart = ImageIO.read(getClass().getResourceAsStream("/player/new_kart.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public double calculateAccel() {
        return maxAcceleration - Math.pow(speed/maxSpeed, 2)*maxAcceleration;
    }

    public double calculateRevertAccel() {
        return (maxAcceleration/3) - Math.pow(-speed/(maxSpeed/3), 2)*(maxAcceleration/3);
    }

    public void update() {
        //CircuitTile tile = circuit.getTile(getRoundX(), getRoundY());
        int collision = panel.getCollisionChecker().checkEntity(this);
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
                location.rotate(-Math.PI/120);
                speed -= speed/250;
            }
            if (keyHandler.isRightPressed()) {
                location.rotate(Math.PI/120);
                speed -= speed/250;
            }
        }
        if (!getKeyHandler().isUpPressed() && !keyHandler.isDownPressed()){
            speed *= 0.98;
            if (Math.abs(speed) < 5) {
                speed = 0;
            }
        }
        if (CollisionResult.SPEED.fail(collision)) {
            if (!CollisionResult.SPEED.fail(prevCollision)) {
                panel.playSound(SoundOld.SPEED);
            }
            speed = Math.min(speed + 50, maxSpeed*1.5);
        }
        if (CollisionResult.SLOW.fail(collision)) {
            if (!CollisionResult.SLOW.fail(prevCollision)) {
                panel.playSound(SoundOld.SLOW);
            }
            speed *= 0.98;
        }
        if (CollisionResult.ROAD.fail(collision)) {
            if (speed > maxSpeed) {
                speed = Math.max(0, speed - 3);
            }
            if (speed < -maxSpeed/3) {
                speed = Math.max(0, speed + 3);
            }
        }
        if (keyHandler.isSpacePressed()) {
            speed = Math.min(speed + 50, maxSpeed*1.5);
        }
        if (speed != 0) {
            Complex pos = new Complex(getX(), getY());
            Complex move = ComplexUtils.polar2Complex(Math.abs(speed/circuit.getSpeedReducer()), speed > 0 ? getRotation() : getRotation() + Math.PI);
            pos = pos.add(move);
            double prevX = getX();
            double prevY = getY();
            setX(pos.getReal());
            setY(pos.getImaginary());
            setX(Math.max(0, getX()));
            setY(Math.max(0, getY()));
            setX(Math.min(getX(), circuit.getWidth()));
            setY(Math.min(getY(), circuit.getHeight()));
            if (CollisionResult.WALL.fail(panel.getCollisionChecker().checkEntity(this))) {
                if (Math.abs(prevSpeed) > maxSpeed/4) {
                    panel.playSound(SoundOld.BLOCK);
                }
                setX(prevX);
                setY(prevY);
                speed = 0;
            }
        }
        prevCollision = collision;
        prevSpeed = speed;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(ImageUtils.rotate(kart, getRotation() + Math.PI/2),  panel.getWidth()/2 - panel.getTileSize()/2, panel.getHeight()/2-panel.getTileSize()/2, panel.getTileSize(), panel.getTileSize(), null);
    }
}
