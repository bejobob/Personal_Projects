package io.github.benjamin_kealey.PhysicalObjects;

import io.github.benjamin_kealey.worldObject;
import io.github.benjamin_kealey.Interfaces.*;
import io.github.benjamin_kealey.Utility.*;

import com.badlogic.gdx.math.Vector2;

public class physicalObject extends worldObject{
    public float mass, radius, bounciness;
    public Vector2 forceVec;
    public Vector2 accVec;
    public Vector2 velVec;
    public boolean fixed;
    
    public physicalObject(float x, float y, float mass, float radius, float bounciness, boolean fixed){
        super(x, y);
        this.mass = mass;
        this.radius = radius;
        this.bounciness = bounciness;
        this.forceVec = new Vector2(0, fixed? 0 : (float)(-9.81*mass*100));

        this.accVec = new Vector2(this.forceVec.x / (float) mass, this.forceVec.y / (float) mass);
        this.velVec = new Vector2(0, 0);
        //System.out.println("pysicalObject: (" + x + ", " + y + ")");

    }

    public void update(float delta, World world){
        if (!fixed){
            x += velVec.x * delta;
            if (x + radius > world.worldWidth){
                x = world.worldWidth - radius;
                velVec.x *= -bounciness;
            } else if (x - radius < 0){
                x = radius;
                velVec.x *= -bounciness;
            }
            
            y += velVec.y * delta;
            if (y + radius > world.worldHeight){
                y = world.worldHeight - radius;
                velVec.y *= -bounciness;
            } else if (y - radius < 0){
                y = radius;
                velVec.y *= -bounciness;
            }

            velVec.x += accVec.x * delta;
            velVec.y += accVec.y * delta;

            accVec.x = forceVec.x / (float) mass;
            accVec.y = forceVec.y / (float) mass;
        }
    }
        
}
