package io.github.benjamin_kealey.Interfaces;

import io.github.benjamin_kealey.PhysicalObjects.physicalObject;

public interface Factory {
    physicalObject create(float x, float y);
    
}
