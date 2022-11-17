package com.fibersim.core.model.common;

import lombok.Getter;
import lombok.Setter;

public class Ray {
    @Getter
    private Vector3 pos;
    @Getter
    @Setter
    private Vector3 vel;
    @Getter
    private double lambda;
    @Getter
    private double power;
    private boolean alive;

    public Ray(Vector3 pos, Vector3 vel, double lambda, double power) {
        this.pos = pos;
        this.vel = vel.unit();
        this.lambda = lambda;
        this.power = power;
        this.alive = true;
    }

    public void move(double ds) {
        this.pos = Vector3.add(this.pos, this.vel.scale(ds));
    }

    public void setLambda(double lambda) {
        //Apply Stokes shift
        this.power = this.power*this.lambda/lambda;

        this.lambda = lambda;
    }

    public boolean alive() {
        return this.alive;
    }

    public void kill() {
        this.alive = false;
    }
}
