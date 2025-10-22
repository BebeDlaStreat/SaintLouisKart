package fr.bebedlastreat.slkart.entity;

import fr.bebedlastreat.slkart.map.Circuit;
import fr.bebedlastreat.slkart.map.Game;
import fr.bebedlastreat.slkart.panel.GamePanel;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Entity {
    public GamePanel gp;
    public Location location;
    public Game game;
    public Circuit circuit;
    public List<Integer> lastX = new ArrayList<>();
    public List<Integer> lastY = new ArrayList<>();

    public Rectangle solidArea;
    public boolean collision;

    public Entity(GamePanel gp, boolean collision) {
        this.gp = gp;
        this.game = gp.getGame();
        this.circuit = game.getCircuit();
        this.collision = collision;
        this.location = new Location(0, 0, 0);
    }

    public int getRoundX() {
        return (int) Math.round(location.getX());
    }

    public int getRoundY() {
        return (int) Math.round(location.getY());
    }

    public double getX() {
        return location.getX();
    }

    public double getY() {
        return location.getY();
    }

    public double getRotation() {
        return location.getRotation();
    }

    public void setX(double x) {
        location.setX(x);
    }

    public void setY(double y) {
        location.setY(y);
    }

    public void setRotation(double rad) {
        location.setRotation(rad);
    }

    abstract void draw(Graphics2D g2);
}
