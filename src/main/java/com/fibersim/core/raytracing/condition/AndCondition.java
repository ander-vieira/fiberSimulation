package com.fibersim.core.raytracing.condition;

import com.fibersim.core.raytracing.common.Ray;

public class AndCondition implements Condition {
    private final Condition[] conditions;

    public AndCondition(Condition... conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean check(Ray ray) {
        for(Condition condition : conditions) {
            if(!condition.check(ray)) {
                return false;
            }
        }
        return true;
    }
}
