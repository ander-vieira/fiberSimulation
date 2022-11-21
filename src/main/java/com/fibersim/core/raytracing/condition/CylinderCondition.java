package com.fibersim.core.raytracing.condition;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;

public class CylinderCondition implements Condition {
    private final Vector3 origin;
    private final Vector3 axis;
    private final double R2;

    public CylinderCondition(Vector3 origin, Vector3 axis, double R) {
        this.origin = origin;
        this.axis = axis.unit();
        this.R2 = R*R;
    }

    @Override
    public boolean check(Ray ray) {
        Vector3 relativePos = Vector3.sub(ray.getPos(), this.origin);

        double posAxis = Vector3.dot(relativePos, this.axis);

        return relativePos.norm2()-posAxis*posAxis < R2;
    }
}
