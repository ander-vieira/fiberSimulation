package com.fibersim.core.raytracing.interphase;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;

public interface Interphase {
    double intersect(Ray ray);
    Vector3 getNormalVector(Vector3 pos);
}
