package com.fibersim.core.model.condition;


import com.fibersim.core.model.common.Ray;

public class OrCondition implements Condition {
    private final Condition[] conditions;

    public OrCondition(Condition... conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean check(Ray ray) {
        for(Condition condition : conditions) {
            if(condition.check(ray)) {
                return true;
            }
        }
        return false;
    }
}
