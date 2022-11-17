package com.fibersim.core.model.element;

import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.condition.Condition;
import com.fibersim.core.utils.VectorUtils;
import com.fibersim.server.util.MathUtils;

public class DyeDopantElement implements Element {
    private final Condition condition;
    private final double alpha;
    private final double quantumYield;

    public DyeDopantElement(Condition condition, double N, double sigmaAbs, double quantumYield) {
        this.condition = condition;
        this.alpha = N*sigmaAbs;
        this.quantumYield = quantumYield;
    }

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

        if(Math.random() < quantumYield) {
            ray.setVel(VectorUtils.randomDirection());

            double newLambda = ray.getLambda(); //TODO

            ray.setLambda(newLambda);
        } else {
            ray.kill();
        }
    }
}
