package io.github.benjamin_kealey.PhysicalObjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import io.github.benjamin_kealey.Interfaces.*;

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
        cornerCords[0] = new Vector2((float)(x+width/2), (float)(y+height/2)); // top left
        cornerCords[1] = new Vector2((float)(x-width/2), (float)(y+height/2)); // top right
        cornerCords[2] = new Vector2((float)(x-width/2), (float)(y-height/2)); // bottom right
        cornerCords[3] = new Vector2((float)(x+width/2), (float)(y-height/2)); // bottom left
        
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        
        for (int i = 0; i < cornerCords.length; i++) {
            cornerCords[i] = new Vector2(
                cornerCords[i].x * cos - cornerCords[i].y * sin,
                cornerCords[i].x * sin + cornerCords[i].y * cos
            );
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
}
