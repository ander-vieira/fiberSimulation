package com.fibersim.core.model.element;

import com.fibersim.core.model.condition.Condition;
import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.common.Vector3;
import com.fibersim.core.model.interphase.Interphase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MirrorElement implements Element {
    private final Interphase interphase;
    private final Condition condition;

    @Override
    public double intersect(Ray ray) {
        return interphase.intersect(ray);
    }

    @Override
    public void process(Ray ray) {
        if(!condition.check(ray)) {
            return;
        }

        Vector3 normalVector = interphase.getNormalVector(ray.getPos());

        ray.setVel(Vector3.reflectOnSurface(ray.getVel(), normalVector));
    }
}
