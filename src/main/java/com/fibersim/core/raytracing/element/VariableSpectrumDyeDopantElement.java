package com.fibersim.core.raytracing.element;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.common.Vector3;
import com.fibersim.core.raytracing.condition.Condition;
import com.fibersim.core.raytracing.wavelength.Wavelength;
import com.fibersim.core.raytracing.wavelength.provider.WavelengthProvider;
import com.fibersim.core.resources.dopant.DyeDopant;
import com.fibersim.core.resources.variableemission.VariableSpectrumProvider;
import com.fibersim.core.utils.MathUtils;

public class VariableSpectrumDyeDopantElement implements Element {
    private final Condition condition;
    private final DyeDopant dyeDopant;
    private final double concentration;
    private final VariableSpectrumProvider emissionProvider;

    public VariableSpectrumDyeDopantElement(Condition condition, DyeDopant dyeDopant, double concentration, WavelengthProvider wavelengthProvider) {
        this.condition = condition;
        this.dyeDopant = dyeDopant;
        this.concentration = concentration;
        this.emissionProvider = new VariableSpectrumProvider(dyeDopant.getSigmaAbs(), dyeDopant.getSigmaEmi(), wavelengthProvider);
    }

    @Override
    public double intersect(Ray ray, double limit) {
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

            Wavelength newWavelength = this.emissionProvider.getEmission(ray.getWavelength()).generateWavelength();

            ray.setWavelength(newWavelength);
        } else {
            ray.kill();
        }
    }
}
