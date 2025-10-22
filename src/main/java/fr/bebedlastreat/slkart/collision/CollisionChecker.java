package fr.bebedlastreat.slkart.collision;

import fr.bebedlastreat.slkart.entity.Entity;
import fr.bebedlastreat.slkart.map.CircuitTile;
import fr.bebedlastreat.slkart.panel.GamePanel;
import lombok.Data;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexUtils;

import java.awt.*;

@Data
public class CollisionChecker {

    private final GamePanel gp;

    public int checkEntity(Entity entity) {
        if (!entity.isCollision()) return 0;
        int data = 0;
        Rectangle rect = entity.getSolidArea();
        entity.getLastX().clear();
        entity.getLastY().clear();
        double minX = (entity.getX() - (gp.getTileSize()/2d - rect.x)/gp.getCircuit().getSizeMultiplier());
        double minY = (entity.getY() - (gp.getTileSize()/2d - rect.y)/gp.getCircuit().getSizeMultiplier());
        double maxX = (minX + rect.width/gp.getCircuit().getSizeMultiplier());
        double maxY = (minY + rect.height/gp.getCircuit().getSizeMultiplier());
        for (int x = (int) Math.round(minX); x <= Math.round(maxX); x++) {
            for (int y = (int) Math.round(minY); y <= Math.round(maxY); y++) {
                Complex c = new Complex(x, y);
                Complex center = new Complex(entity.getX(), entity.getY());
                c = center.add(ComplexUtils.polar2Complex(1, entity.getRotation() - Math.PI/2).multiply(c.subtract(center)));
                int realX = (int) Math.round(c.getReal());
                int realY = (int) Math.round(c.getImaginary());
                entity.getLastX().add(realX);
                entity.getLastY().add(realY);
                CircuitTile tile = gp.getCircuit().getTile(realX, realY);
                switch (tile) {
                    case WALL: {
                        data |= CollisionResult.WALL.getData();
                        break;
                    }
                    case ROAD: {
                        data |= CollisionResult.ROAD.getData();
                        break;
                    }
                    case SPEED: {
                        data |= CollisionResult.SPEED.getData();
                        break;
                    }
                    case SLOW: {
                        data |= CollisionResult.SLOW.getData();
                        break;
                    }
                }
            }
        }

        return data;
    }
}
