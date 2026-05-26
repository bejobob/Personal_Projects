package io.github.benjamin_kealey.PhysicalObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.benjamin_kealey.Interfaces.*;

public class ndParticle extends physicalObject implements Printable, Renderable, Updateable, Collidable{
    public float terminalVelocity;
    public boolean fixed;

    public ndParticle(float x, float y, float radius, boolean fixed, float mass, float bounciness, float terminalVelocity) {
        super(x, y, mass, radius, bounciness, fixed);
        this.terminalVelocity = terminalVelocity;
        //System.out.println("ndParticle: (" + x + ", " + y + ")");
    }

    public void print(){
        System.out.println("Position: (" + x + ", " + y + ")\nRadius: " + radius + "\nMass: " + mass + "\nBounciness: " + bounciness + "\nTerminal Velocity: " + terminalVelocity);
    }


    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.circle(x, y, radius);
    }

    public float getRadius(){
        return radius;
    }

    public void setVelVec(float x, float y){
        this.velVec.x = x;
        this.velVec.y = y;
    }
}