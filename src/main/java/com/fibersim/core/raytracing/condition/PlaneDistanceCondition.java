package com.fibersim.core.raytracing.condition;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;

public class PlaneDistanceCondition implements Condition {
    private final Vector3 origin;
    private final Vector3 normalVector;
    private final double maxZ;

    public PlaneDistanceCondition(Vector3 origin, Vector3 normalVector, double maxZ) {
        this.origin = origin;
        this.normalVector = normalVector.unit();
        this.maxZ = maxZ;
    }

    @Override
    public boolean check(Ray ray) {
        Vector3 relativePos = Vector3.sub(ray.getPos(), this.origin);

        double Z = Vector3.dot(relativePos, this.normalVector);

        return Z > 0 && Z < maxZ;
    }
}
