package com.fibersim.core.raytracing.element;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.condition.Condition;
import com.fibersim.core.raytracing.interphase.Interphase;
import com.fibersim.core.raytracing.detector.Detector;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DetectorElement implements Element {
    private final Interphase interphase;
    private final Detector detector;
    private final Condition condition;

    @Override
    public double intersect(Ray ray) {
        return interphase.intersect(ray);
    }

    @Override
    public void process(Ray ray) {
        if(!condition.check(ray)) {
            return;
        }

        detector.detect(ray);

        ray.kill();
    }
}
