package io.github.benjamin_kealey.EffectsObjects;

import java.lang.Math;
import com.badlogic.gdx.Gdx;

import io.github.benjamin_kealey.worldObject;
import io.github.benjamin_kealey.PhysicalObjects.*;
import io.github.benjamin_kealey.Utility.World;

public class particleEmitter extends worldObject{
    public double duration;
    public int pps; // particles per second
    public float x, y, maxSpeed, minSpeed, maxMass, minMass, maxRadius, minRadius, maxBounciness, minBounciness, maxTermVel, minTermVel;

    public particleEmitter(float x, float y, float duration, int pps, float maxSpeed, float minSpeed, float maxMass, float minMass, float maxRadius, float minRadius, float maxBounciness, float minBounciness, float maxTermVel, float minTermVel) {
        super(x, y);
        this.duration = duration;
        this.pps = pps;
        this.x = x;
        this.y = y;
        this.maxSpeed = maxSpeed;
        this.minSpeed = minSpeed;
        this.maxMass = maxMass;
        this.minMass = minMass;
        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
        this.maxBounciness = maxBounciness;
        this.minBounciness = minBounciness;
        this.maxTermVel = maxTermVel;
        this.minTermVel = minTermVel;
    }

    public ndParticle createParticle() {
        return new ndParticle(
            x, y,
            (float)(Math.random() * (maxRadius - minRadius) + minRadius),
            false,
            (float)(Math.random() * (maxMass - minMass) + minMass),
            (float)(Math.random() * (maxBounciness - minBounciness) + minBounciness),
            (float)(Math.random() * (maxTermVel - minTermVel) + minTermVel)
        );
    }

    public void emit() {
        double interval = 1.0/pps;
        double sinceLast = 0;
        double elapsed = 0;
        while (elapsed < duration) {
            sinceLast += Gdx.graphics.getDeltaTime();
            elapsed += Gdx.graphics.getDeltaTime();
            if (sinceLast >= interval) {
                createParticle();
                sinceLast = 0;
            }
            if (elapsed >= duration){
                return;
            }
        }
    }

    public void update(float delta, World world) {
        emit();
    }
}
