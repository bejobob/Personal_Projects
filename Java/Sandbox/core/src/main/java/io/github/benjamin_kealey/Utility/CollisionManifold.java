package io.github.benjamin_kealey.Utility;

import com.badlogic.gdx.math.Vector2;

public class CollisionManifold {
    boolean collided;
    Vector2 normal;
    float penetration;
    Vector2 contactPoint;
    
    public CollisionManifold(boolean collided, Vector2 normal, float penetration, Vector2 contactPoint) {
        this.collided = collided;
        this.normal = normal;
        this.penetration = penetration;
        this.contactPoint = contactPoint;
    }
}
