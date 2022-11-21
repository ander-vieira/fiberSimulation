package com.fibersim.core.raytracing.condition;

import com.fibersim.core.raytracing.common.Ray;

public class NotCondition implements Condition {
    private final Condition condition;

    public NotCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public boolean check(Ray ray) {
        return !condition.check(ray);
    }
}
