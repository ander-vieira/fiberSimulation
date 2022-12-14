package com.fibersim.core.simulation;

import com.fibersim.core.raytracing.element.Element;

import java.util.HashSet;
import java.util.Set;

public class StuckDetector {
    public static final double STUCK_LIMIT = 1e-13;

    private final Set<Element> stuckElements;

    public StuckDetector() {
        this.stuckElements = new HashSet<>();
    }

    public double limit(Element element) {
        return stuckElements.contains(element) ? STUCK_LIMIT : 0;
    }

    public void update(Element element, double ds) {
        if(ds >= STUCK_LIMIT) {
            stuckElements.clear();
        }

        stuckElements.add(element);
    }
}
