package com.fibersim.core.raytracing.element;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;
import com.fibersim.core.raytracing.condition.Condition;
import com.fibersim.core.resources.dopant.DyeDopant;
import com.fibersim.core.raytracing.wavelength.Wavelength;
import com.fibersim.core.raytracing.wavelength.provider.WavelengthProvider;
import com.fibersim.core.raytracing.wavelength.spectrum.WavelengthSpectrum;
import com.fibersim.core.utils.MathUtils;

public class DyeDopantElement implements Element {
    private final Condition condition;
    private final DyeDopant dyeDopant;
    private final double concentration;
    private final WavelengthSpectrum emissionSpectrum;

    public DyeDopantElement(Condition condition, DyeDopant dyeDopant, double concentration, WavelengthProvider provider) {
        this.condition = condition;
        this.dyeDopant = dyeDopant;
        this.concentration = concentration;
        this.emissionSpectrum = new WavelengthSpectrum(provider, dyeDopant.getSigmaemi());
    }

    @Override
    public double intersect(Ray ray) {
        if(!condition.check(ray)) {
            return Double.POSITIVE_INFINITY;
        }

        double alpha = concentration*dyeDopant.getSigmaAbs().evaluate(ray.getWavelength());

        return MathUtils.randomExponential(alpha);
    }

    @Override
    public void process(Ray ray) {
        if(!condition.check(ray)) {
            return;
        }

        if(Math.random() < dyeDopant.getQuantumYield()) {
            ray.setVel(Vector3.randomDirection());

            Wavelength newWavelength = this.emissionSpectrum.generateWavelength();

            ray.setWavelength(newWavelength);
        } else {
            ray.kill();
        }
    }
}
