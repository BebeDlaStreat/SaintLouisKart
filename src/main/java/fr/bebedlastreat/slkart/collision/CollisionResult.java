package fr.bebedlastreat.slkart.collision;

import lombok.Getter;

@Getter
public enum CollisionResult {
    WALL(1),
    ROAD(2),
    SPEED(4),
    SLOW(8),
    ENTITY(16),

    ;

    private final int data;

    CollisionResult(int data) {
        this.data = data;
    }

    public boolean fail(int i) {
        return (i & data) == data;
    }
}
