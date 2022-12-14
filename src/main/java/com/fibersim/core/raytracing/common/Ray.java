package com.fibersim.core.raytracing.common;

import com.fibersim.core.raytracing.wavelength.Wavelength;
import lombok.Getter;

public class Ray {
    @Getter
    private Vector3 pos;
    @Getter
    private Vector3 vel;
    @Getter
    private Wavelength wavelength;
    @Getter
    private double power;
    private boolean alive;

    public Ray(Vector3 pos, Vector3 vel, Wavelength wavelength, double power) {
        this.pos = pos;
        this.vel = vel.unit();
        this.wavelength = wavelength;
        this.power = power;
        this.alive = true;
    }

    public void move(double ds) {
        this.pos = Vector3.add(this.pos, this.vel.scale(ds));
    }

    public void setVel(Vector3 vel) {
        this.vel = vel.unit();
    }

    public void setWavelength(Wavelength wavelength) {
        //Apply Stokes shift
        this.power = this.power*this.wavelength.getValue()/wavelength.getValue();

        this.wavelength = wavelength;
    }

    public boolean alive() {
        return this.alive;
    }

    public void kill() {
        this.alive = false;
    }
}
