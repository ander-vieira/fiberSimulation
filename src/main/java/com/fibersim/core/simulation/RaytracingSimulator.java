package com.fibersim.core.simulation;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.element.Element;
import com.fibersim.core.raytracing.source.Source;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RaytracingSimulator {
    @Async
    public void simulate(RaytracingSimulation raytracingSimulation) {
        raytracingSimulation.initialize();

        for(Source source : raytracingSimulation.getModel().getSources()) {
            for(int i = 0; i < source.numRays() ; i++) {
                processRay(source.getRay(i), raytracingSimulation);

                raytracingSimulation.increment();
            }
        }
    }

    private void processRay(Ray ray, RaytracingSimulation raytracingSimulation) {
        StuckDetector stuckDetector = new StuckDetector();

        while(ray.alive()) {
            Element firstElement = null;
            double ds = Double.POSITIVE_INFINITY;

            for(Element element : raytracingSimulation.getModel().getElements()) {
                double dsComponent = element.intersect(ray);

                if(stuckDetector.check(element, dsComponent)) {
                    dsComponent = Double.POSITIVE_INFINITY;
                }

                if(dsComponent < ds) {
                    firstElement = element;
                    ds = dsComponent;
                }
            }

            if(firstElement == null) {
                ray.kill();

                break;
            }

            stuckDetector.update(firstElement, ds);

            ray.move(ds);

            firstElement.process(ray);
        }
    }
}
