package io.github.benjamin_kealey.Interfaces;

import com.badlogic.gdx.math.Vector2;

public interface Rectangular {
    public Vector2[] corners();
    public float minX();
    public float maxX();
    public float minY();
    public float maxY();
}
