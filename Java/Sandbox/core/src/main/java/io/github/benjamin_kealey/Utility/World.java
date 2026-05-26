package io.github.benjamin_kealey.Utility;

import io.github.benjamin_kealey.worldObject;
import java.util.ArrayList;

public class World {
    public float worldWidth, worldHeight, gravity;
    public ArrayList<worldObject> list;
    public ArrayList<worldObject> toAdd;
    
    public World(float worldWidth, float worldHeight, float gravity, ArrayList<worldObject> list, ArrayList<worldObject> toAdd){
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.gravity = gravity;
        this.list = list;
        this.toAdd = toAdd;
    }
    
}
