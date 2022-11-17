package com.fibersim.core.model.common;

public class Vector3 {
    private final double x;
    private final double y;
    private final double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 scale(double scale) {
        return new Vector3(this.x*scale, this.y*scale, this.z*scale);
    }

    public double norm() {
        return Math.sqrt(this.norm2());
    }

    public double norm2() {
        return Vector3.dot(this, this);
    }

    public Vector3 unit() {
        double norm = this.norm();
        if(norm == 0) {
            return Vector3.O;
        } else {
            return this.scale(1/norm);
        }
    }

    public static final Vector3 O = new Vector3(0,0,0);
    public static final Vector3 X = new Vector3(1,0,0);
    public static final Vector3 Y = new Vector3(0,1,0);
    public static final Vector3 Z = new Vector3(0,0,1);

    public static Vector3 add(Vector3 vec1, Vector3 vec2) {
        return new Vector3(vec1.x+vec2.x, vec1.y+vec2.y, vec1.z+vec2.z);
    }

    public static Vector3 sub(Vector3 vec1, Vector3 vec2) {
        return new Vector3(vec1.x-vec2.x, vec1.y-vec2.y, vec1.z-vec2.z);
    }

    public static double dot(Vector3 vec1, Vector3 vec2) {
        return vec1.x*vec2.x+vec1.y*vec2.y+vec1.z*vec2.z;
    }

    public static Vector3 cross(Vector3 vec1, Vector3 vec2) {
        return new Vector3(vec1.y*vec2.z-vec1.z*vec2.y,
                vec1.z*vec2.x-vec1.x*vec2.z,
                vec1.x*vec2.y-vec1.y*vec2.x);
    }
}
