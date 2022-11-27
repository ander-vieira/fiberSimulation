package com.fibersim.core.raytracing.interphase;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;

import java.util.List;

public interface Interphase {
    List<Double> intersect(Ray ray);
    Vector3 getNormalVector(Vector3 pos);
}
