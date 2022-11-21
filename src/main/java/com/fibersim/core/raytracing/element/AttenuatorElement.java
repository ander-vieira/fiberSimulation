package com.fibersim.core.raytracing.element;

import com.fibersim.core.data.medium.Medium;
import com.fibersim.core.raytracing.condition.Condition;
import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.utils.MathUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AttenuatorElement implements Element {
    private final Condition condition;
    private final Medium medium;

    @Override
    public double intersect(Ray ray) {
        if(!condition.check(ray)) {
            return Double.POSITIVE_INFINITY;
        }

        return MathUtils.randomExponential(this.medium.getAlpha().evaluate(ray.getWavelength()));
    }

    @Override
    public void process(Ray ray) {
        if(!condition.check(ray)) {
            return;
        }

        ray.kill();
    }
}
