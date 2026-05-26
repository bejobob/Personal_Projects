package io.github.benjamin_kealey.EffectsObjects;

import java.lang.Math;

import io.github.benjamin_kealey.worldObject;
import io.github.benjamin_kealey.PhysicalObjects.*;
import io.github.benjamin_kealey.Interfaces.*;
import io.github.benjamin_kealey.Utility.World;

public class particleEmitter extends worldObject implements Updateable{
    public float duration, sinceLast, elapsed;
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
        this.sinceLast = 0;
        this.elapsed = 0;
    }

    public ndParticle createParticle() {
        float speed = minSpeed + (float)(Math.random()*(maxSpeed - minSpeed + 1));
        float speedX = minSpeed + (float)(Math.random()*(speed - minSpeed + 1));
        float speedY = (float)Math.sqrt(speed*speed - speedX*speedX);
        ndParticle p = new ndParticle(
            x, y,
            (float)(Math.random() * (maxRadius - minRadius) + minRadius),
            false,
            (float)(Math.random() * (maxMass - minMass) + minMass),
            (float)(Math.random() * (maxBounciness - minBounciness) + minBounciness),
            (float)(Math.random() * (maxTermVel - minTermVel) + minTermVel)
        );
        p.setVelVec(speedX, speedY);
        return(p);
    }

    public void update(float delta, World world) {
        double interval = 1.0/pps;
        if (elapsed < duration) {
            sinceLast += delta;
            elapsed += delta;
            if (sinceLast >= interval) {
                world.toAdd.add(createParticle());
                sinceLast = 0;
            }
            if (elapsed >= duration){
                return;
            }
        }
    }
}
