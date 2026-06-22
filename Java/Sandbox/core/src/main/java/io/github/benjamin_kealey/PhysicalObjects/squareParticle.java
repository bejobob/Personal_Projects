package io.github.benjamin_kealey.PhysicalObjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import io.github.benjamin_kealey.Interfaces.*;
import io.github.benjamin_kealey.Utility.World;

/**
 * @author Benjamin Kealey
 * @version May 29th, 2026
 * 
 * A rectangluar particle. Please note that "radius" refers to the distance from the centre of the box to one of the corners
 */

public class squareParticle extends physicalObject implements Printable, Updateable, Collidable, Rectangular{
    public float terminalVelocity;
    
    public squareParticle(float x, float y, boolean fixed, float mass, float bounciness, float terminalVelocity, float angle, float height, float width){
        super(x, y, mass, 0, bounciness, fixed, height, width, "rectangular", angle, (float)(mass*(width*width+height*height))/12);
        this.terminalVelocity = terminalVelocity;

    }

    public float getRadius(){
        return radius;
    }

    public Vector2[] corners(){
        Vector2[] cornerCords = new Vector2[4];
        cornerCords[0] = new Vector2((float)(x+width/2), (float)(y+height/2)); // top right
        cornerCords[1] = new Vector2((float)(x-width/2), (float)(y+height/2)); // top left
        cornerCords[2] = new Vector2((float)(x-width/2), (float)(y-height/2)); // bottom left
        cornerCords[3] = new Vector2((float)(x+width/2), (float)(y-height/2)); // bottom right
        
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        
        for (int i = 0; i < cornerCords.length; i++) {
            float px = cornerCords[i].x - x;
            float py = cornerCords[i].y - y;

            cornerCords[i].x = x + (px * cos - py * sin);
            cornerCords[i].y = y + (px * sin + py * cos);
    }
        return cornerCords;
    }

    public float minX(){
        float minX = Float.MAX_VALUE;
        for (Vector2 corner : corners()){
            if (corner.x < minX){
                minX = corner.x;
            }
        }
        return minX;
    }

    public float maxX(){
        float maxX = Float.MIN_VALUE;
        for (Vector2 corner : corners()){
            if (corner.x > maxX){
                maxX = corner.x;
            }
        }
        return maxX;
    }

    public float minY(){
        float minY = Float.MAX_VALUE;
        for (Vector2 corner : corners()){
            if (corner.y < minY){
                minY = corner.y;
            }
        }
        return minY;
    }

    public float maxY(){
        float maxY = Float.MIN_VALUE;
        for (Vector2 corner : corners()){
            if (corner.y > maxY){
                maxY = corner.y;
            }
        }
        return maxY;
    }

    public void print(){
        System.out.println("Position: (" + x + ", " + y + ")\nHeight: " + height + "\nWidth: " + width + "\nMass: " + mass + "\nBounciness: " + bounciness + "\nTerminal Velocity: " + terminalVelocity);

    }

    public void update(float delta, World world){ 
        if (!fixed){

            accVec.x = forceVec.x / (float) mass;
            accVec.y = forceVec.y / (float) mass;
            velVec.x += accVec.x * delta;
            velVec.y += accVec.y * delta;

            x += velVec.x * delta;
            if (maxX() > world.worldWidth){
                x -= (maxX() - world.worldWidth);
                velVec.x *= -bounciness;
            } else if (minX() < 0){
                x -= minX();
                velVec.x *= -bounciness;
            }
            
            y += velVec.y * delta;
            if (maxY() > world.worldHeight){
                y -= (maxY() - world.worldHeight);
                velVec.y *= -bounciness;
            } else if (minY() < 0){
                y -= minY();
                velVec.y *= -bounciness;
            }

            float angAccVec = torque/inertia;
            angularVelocity += angAccVec * delta;
            angle += angularVelocity * delta;

            //torque = r x F
        }
    }
}
