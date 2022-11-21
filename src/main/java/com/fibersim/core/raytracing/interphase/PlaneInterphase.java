package com.fibersim.core.raytracing.interphase;

import com.fibersim.core.raytracing.common.Vector3;
import com.fibersim.core.raytracing.common.Ray;

public class PlaneInterphase implements Interphase {
    private final Vector3 origin;
    private final Vector3 normalVector;

    public PlaneInterphase(Vector3 origin, Vector3 normalVector) {
        this.origin = origin;
        this.normalVector = normalVector.unit();
    }

    @Override
    public double intersect(Ray ray) {
        Vector3 relativePos = Vector3.sub(ray.getPos(), this.origin);
        double posNormal = Vector3.dot(relativePos, this.normalVector);
        double velNormal = Vector3.dot(ray.getVel(), this.normalVector);

        if(velNormal == 0) {
            //Ray is parallel to the plane -> no intersection
            return Double.POSITIVE_INFINITY;
        } else {
            double ds = -posNormal/velNormal;

            if(ds <= 0) {
                return Double.POSITIVE_INFINITY;
            } else {
                return ds;
            }
        }
    }

    @Override
    public Vector3 getNormalVector(Vector3 pos) {
        return this.normalVector;
    }
}