package com.fibersim.core.model.element;

import com.fibersim.core.model.common.Ray;
import com.fibersim.core.model.condition.Condition;
import com.fibersim.core.data.dopant.DyeDopant;
import com.fibersim.core.model.wavelength.Wavelength;
import com.fibersim.core.model.wavelength.provider.WavelengthProvider;
import com.fibersim.core.model.wavelength.spectrum.WavelengthSpectrum;
import com.fibersim.core.utils.VectorUtils;
import com.fibersim.server.util.MathUtils;

public class DyeDopantElement implements Element {
    private final Condition condition;
    private final DyeDopant dyeDopant;
    private final WavelengthSpectrum emissionSpectrum;

    public DyeDopantElement(Condition condition, DyeDopant dyeDopant, WavelengthProvider provider) {
        this.condition = condition;
        this.dyeDopant = dyeDopant;
        this.emissionSpectrum = new WavelengthSpectrum(provider, dyeDopant.getSigmaemi());
    }

    @Override
    public double intersect(Ray ray) {
        if(!condition.check(ray)) {
            return Double.POSITIVE_INFINITY;
        }

        double alpha = dyeDopant.getN()*dyeDopant.getSigmaAbs().evaluate(ray.getWavelength());

        return MathUtils.randomExponential(alpha);
    }

    @Override
    public void process(Ray ray) {
        if(!condition.check(ray)) {
            return;
        }

        if(Math.random() < dyeDopant.getQuantumYield()) {
            ray.setVel(VectorUtils.randomDirection());

            Wavelength newWavelength = this.emissionSpectrum.generateWavelength();

            ray.setWavelength(newWavelength);
        } else {
            ray.kill();
        }
    }
}
