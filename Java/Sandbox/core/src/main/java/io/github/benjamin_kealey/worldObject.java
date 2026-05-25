package io.github.benjamin_kealey;

import io.github.benjamin_kealey.Interfaces.*;

public abstract class worldObject{

    protected float x, y;
    protected boolean permitted_to_live = true;

    public worldObject (float x, float y){
        this.x = x;
        this.y = y;
        //System.out.println("worldObject: (" + x + ", " + y + ")");

    }

    public void remove(){
        permitted_to_live = false;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

}
