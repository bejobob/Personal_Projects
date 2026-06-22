package io.github.benjamin_kealey.PhysicalObjects;

import io.github.benjamin_kealey.worldObject;
import io.github.benjamin_kealey.Interfaces.*;
import io.github.benjamin_kealey.Utility.*;

import com.badlogic.gdx.math.Vector2;

public class physicalObject extends worldObject implements Renderable{
    public float mass, invMass, radius, bounciness, angle, angularVelocity, torque, inertia, invInertia, height, width, terminalVelocity;
    public Vector2 forceVec;
    public Vector2 accVec;
    public Vector2 velVec;
    public boolean fixed;
    public String shape;
    
    public physicalObject(float x, float y, float mass, float radius, float bounciness, boolean fixed, float height, float width, String shape, float angle, float inertia){
        super(x, y);
        this.mass = mass;
        this.invMass = 1/mass;
        this.radius = radius;
        this.height = height;
        this.width = width;
        this.bounciness = bounciness;
        this.angle = angle;
        this.angularVelocity = 0;
        this.torque = 0;
        this.inertia = inertia;
        this.invInertia = 1/inertia;
        this.forceVec = new Vector2(0, fixed? 0 : (float)(-9.81*mass*100));
        this.accVec = new Vector2(this.forceVec.x / (float) mass, this.forceVec.y / (float) mass);
        this.velVec = new Vector2(0, 0);
        this.fixed = fixed;
        this.shape = shape;
        //System.out.println("pysicalObject: (" + x + ", " + y + ")");
    }
    /**
     * @param delta the time since the last frame
     * @param world the world. Contains more information than needed but we only use what we need
     * @param r // the collision normal. Vector from COM to contact point
     */
    

    public void render(com.badlogic.gdx.graphics.glutils.ShapeRenderer shapeRenderer){
        switch (shape) {
            case "circular":
                shapeRenderer.circle(x, y, radius);
                break;
            case "rectangular":
                shapeRenderer.rect(x-width/2, y-height/2, width/2, height/2, width, height, 1, 1, angle);
                break;
            default:
                break;
        }
    }
}