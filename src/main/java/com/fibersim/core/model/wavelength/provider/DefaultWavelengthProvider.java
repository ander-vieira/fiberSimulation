package com.fibersim.core.model.wavelength.provider;

import com.fibersim.core.model.wavelength.Wavelength;

public class DefaultWavelengthProvider implements WavelengthProvider {
    private final Wavelength[] wavelengths;

    public DefaultWavelengthProvider(double minW, double maxW, int numW) {
        this.wavelengths = new Wavelength[numW];

        for(int i = 0 ; i < numW ; i++) {
            double w = minW+(maxW-minW)*(i+0.5)/numW;

            this.wavelengths[i] = new Wavelength(i, w);
        }
    }

    @Override
    public Wavelength[] getWavelengths() {
        return this.wavelengths;
    }
}
