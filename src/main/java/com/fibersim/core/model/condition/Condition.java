package com.fibersim.core.model.condition;

import com.fibersim.core.model.common.Ray;

public interface Condition {
    boolean check(Ray ray);

    Condition ALWAYS = ray -> true;
}
