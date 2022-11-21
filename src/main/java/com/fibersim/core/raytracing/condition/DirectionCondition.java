package com.fibersim.core.raytracing.condition;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;
import com.fibersim.core.raytracing.interphase.Interphase;

public class DirectionCondition implements Condition {
    private final Interphase interphase;

    public DirectionCondition(Interphase interphase) {
        this.interphase = interphase;
    }

    @Override
    public boolean check(Ray ray) {
        Vector3 normalVector = this.interphase.getNormalVector(ray.getPos());

        return Vector3.dot(ray.getVel(), normalVector) > 0;
    }
}
