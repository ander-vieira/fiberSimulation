package com.fibersim.core.model.element;

import com.fibersim.core.model.condition.Condition;
import com.fibersim.core.model.common.Ray;
import com.fibersim.server.util.MathUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AttenuatorElement implements Element {
    private final Condition condition;
    private final double alpha;

    @Override
    public double intersect(Ray ray) {
        if(!condition.check(ray)) {
            return Double.POSITIVE_INFINITY;
        }

        return MathUtils.randomExponential(this.alpha);
    }

    @Override
    public void process(Ray ray) {
        if(!condition.check(ray)) {
            return;
        }

        ray.kill();
    }
}
