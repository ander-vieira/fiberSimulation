package com.fibersim.core.resources.variableemission;

import com.fibersim.core.raytracing.wavelength.Wavelength;
import com.fibersim.core.raytracing.wavelength.function.CombinedWFunction;
import com.fibersim.core.raytracing.wavelength.function.WFunction;
import com.fibersim.core.raytracing.wavelength.provider.WavelengthProvider;
import com.fibersim.core.raytracing.wavelength.spectrum.WavelengthSpectrum;
import com.fibersim.core.utils.MathUtils;

import java.util.HashMap;
import java.util.Map;

public class VariableSpectrumProvider {
    private final double Em;
    private final WavelengthProvider wavelengthProvider;
    private final Map<Integer, WavelengthSpectrum> cachedFunctions;

    private static final double hc = MathUtils.h*MathUtils.c;

    public VariableSpectrumProvider(WFunction sigmaAbs, WFunction sigmaEmi, WavelengthProvider wavelengthProvider) {
        Wavelength[] wavelengths = wavelengthProvider.getWavelengths();
        double totalAbs0 = 0, totalAbs1 = 0, totalEmi0 = 0, totalEmi1 = 0;

        for(Wavelength wavelength : wavelengths) {
            double sAbs = sigmaAbs.evaluate(wavelength);
            double sEmi = sigmaEmi.evaluate(wavelength);
            double E = getTotalEnergy(wavelength.getValue());

            totalAbs0 += sAbs;
            totalAbs1 += E*sAbs;
            totalEmi0 += sEmi;
            totalEmi1 += E*sEmi;
        }

        this.Em = totalAbs1/totalAbs0-totalEmi1/totalEmi0;
        this.wavelengthProvider = wavelengthProvider;
        this.cachedFunctions = new HashMap<>();
    }

    public WavelengthSpectrum getEmission(Wavelength wavelengthAbs) {
        if(this.cachedFunctions.containsKey(wavelengthAbs.getId())) {
            return this.cachedFunctions.get(wavelengthAbs.getId());
        } else {
            WavelengthSpectrum result = this.getEmission(wavelengthAbs.getValue());

            this.cachedFunctions.put(wavelengthAbs.getId(), result);

            return result;
        }
    }

    private WavelengthSpectrum getEmission(double lambdaAbs) {
        Wavelength[] wavelengths = wavelengthProvider.getWavelengths();
        WFunction functionA = getFunctionA(lambdaAbs);
        WFunction functionB = getFunctionB(lambdaAbs);
        double Eabs = getTotalEnergy(lambdaAbs);

        double F0 = 0, F1 = 0, G0 = 0, G1 = 0;

        for(Wavelength wavelength : wavelengths) {
            double valueA = functionA.evaluate(wavelength);
            double valueB = functionB.evaluate(wavelength);
            double E = Eabs-getTotalEnergy(wavelength.getValue());

            F0 += valueA;
            F1 += E*valueA;
            G0 += valueB;
            G1 += E*valueB;
        }

        double determinant = F0*G1-F1*G0;

        if(determinant != 0) {
            double A = (G1-this.Em*G0)/determinant;
            double B = (this.Em*F0-F1)/determinant;

            WFunction resultFunction = new CombinedWFunction(A, B, functionA, functionB);

            return new WavelengthSpectrum(wavelengthProvider, resultFunction);
        } else {
            return null;
        }
    }

    private double getTotalEnergy(double lambda) {
        return hc/lambda;
    }

    private WFunction getFunctionA(double lambdaAbs) {
        return wavelength -> {
            if(wavelength.getValue() >= lambdaAbs) {
                return 1/Math.pow(wavelength.getValue(), 2);
            } else {
                return 0;
            }
        };
    }

    private WFunction getFunctionB(double lambdaAbs) {
        return wavelength -> {
            if(wavelength.getValue() >= lambdaAbs) {
                return 1/Math.pow(wavelength.getValue(), 3);
            } else {
                return 0;
            }
        };
    }
}
