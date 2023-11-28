package fr.bebedlastreat.slkart.entity;

import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class Entity {

    public double x,y;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int getRoundX() {
        return (int) Math.round(x);
    }

    public int getRoundY() {
        return (int) Math.round(y);
    }
}
