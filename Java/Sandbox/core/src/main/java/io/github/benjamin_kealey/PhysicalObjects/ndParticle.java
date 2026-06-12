package io.github.benjamin_kealey.PhysicalObjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.benjamin_kealey.Interfaces.*;

public class ndParticle extends physicalObject implements Printable, Updateable, Collidable, Circular{
    public float terminalVelocity;
    public boolean fixed;

    public ndParticle(float x, float y, float radius, boolean fixed, float mass, float bounciness, float terminalVelocity) {
        super(x, y, mass, radius, bounciness, fixed, 0, 0, "circular", 0, (float)(mass*radius*radius)/2);
        this.terminalVelocity = terminalVelocity;
        //System.out.println("ndParticle: (" + x + ", " + y + ")");
    }

    public void print(){
        System.out.println("Position: (" + x + ", " + y + ")\nRadius: " + radius + "\nMass: " + mass + "\nBounciness: " + bounciness + "\nTerminal Velocity: " + terminalVelocity);
    }

    public float getRadius(){
        return radius;
    }

    public void setVelVec(float x, float y){
        this.velVec.x = x;
        this.velVec.y = y;
    }
}