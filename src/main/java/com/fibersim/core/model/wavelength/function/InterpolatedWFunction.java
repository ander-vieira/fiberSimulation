package com.fibersim.core.model.wavelength.function;

import com.fibersim.core.model.wavelength.WValue;
import com.fibersim.core.model.wavelength.Wavelength;

import java.util.Comparator;
import java.util.List;

public class InterpolatedWFunction implements WFunction {
    private final WValue[] wValues;

    public InterpolatedWFunction(List<WValue> wValues) {
        this.wValues = (WValue[])wValues.stream().sorted(Comparator.comparingDouble(wValue -> wValue.getWavelength().getValue())).toArray();
    }

    @Override
    public double evaluate(Wavelength wavelength) {
        double lowW = Double.NEGATIVE_INFINITY, highW = Double.POSITIVE_INFINITY;
        double lowValue = 0, highValue = 0;
        for(WValue wValue : wValues) {
            double w = wValue.getWavelength().getValue();
            if(w <= wavelength.getValue() && w > lowW) {
                lowW = w;
                lowValue = wValue.getValue();
            }
            if(w > wavelength.getValue() && w <= highW) {
                highW = w;
                highValue = wValue.getValue();
            }
        }

        if(lowW > Double.NEGATIVE_INFINITY && highW < Double.POSITIVE_INFINITY) {
            double u = (wavelength.getValue()-lowW)/(highW-lowW);

            return lowValue*(1-u)+highValue*u;
        } else {
            return 0;
        }
    }
}
