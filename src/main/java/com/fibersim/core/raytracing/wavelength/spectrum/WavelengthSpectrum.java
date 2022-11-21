package com.fibersim.core.raytracing.wavelength.spectrum;

import com.fibersim.core.raytracing.wavelength.WValue;
import com.fibersim.core.raytracing.wavelength.Wavelength;
import com.fibersim.core.raytracing.wavelength.function.WFunction;
import com.fibersim.core.raytracing.wavelength.provider.WavelengthProvider;

public class WavelengthSpectrum {
    private final WValue[] wValues;
    private final double total;

    public WavelengthSpectrum(WavelengthProvider provider, WFunction function) {
        Wavelength[] wavelengths = provider.getWavelengths();

        this.wValues = new WValue[wavelengths.length];
        double total = 0;

        for(int i = 0 ; i < wavelengths.length ; i++) {
            double value = function.evaluate(wavelengths[i]);
            this.wValues[i] = new WValue(wavelengths[i], value);
            total += value;
        }

        this.total = total;
    }

    public Wavelength generateWavelength() {
        double rand = Math.random()*this.total;
        double subtotal = 0;

        for(WValue wValue : this.wValues) {
            subtotal += wValue.getValue();

            if(subtotal >= rand) {
                return wValue.getWavelength();
            }
        }

        return null;
    }
}
