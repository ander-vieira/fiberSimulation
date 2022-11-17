package com.fibersim.core.model.element;

import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.condition.Condition;
import com.fibersim.core.model.interphase.Interphase;
import com.fibersim.core.model.detector.Detector;
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
