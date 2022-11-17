package com.fibersim.core.model.interphase;

import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.common.Vector3;

public interface Interphase {
    double intersect(Ray ray);
    Vector3 getNormalVector(Vector3 pos);
}
