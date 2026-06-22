package io.github.benjamin_kealey.PhysicalObjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.benjamin_kealey.Interfaces.*;
import io.github.benjamin_kealey.Utility.World;

public class ndParticle extends physicalObject implements Printable, Collidable, Circular, Updateable{
    public boolean fixed;
    public float terminalVelocity;

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

    public void update(float delta, World world){ 
        if (!fixed){

            accVec.x = forceVec.x / (float) mass;
            accVec.y = forceVec.y / (float) mass;
            velVec.x += accVec.x * delta;
            velVec.y += accVec.y * delta;

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

            float angAccVec = torque/inertia;
            angularVelocity += angAccVec * delta;
            angle += angularVelocity * delta;
        }
    }
}