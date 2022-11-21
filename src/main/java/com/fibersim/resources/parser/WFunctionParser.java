package com.fibersim.resources.parser;

import com.fibersim.core.raytracing.wavelength.WValue;
import com.fibersim.core.raytracing.wavelength.Wavelength;
import com.fibersim.core.raytracing.wavelength.function.*;
import com.fibersim.resources.csv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class WFunctionParser {
    private static final String CONSTANT_TYPE = "constant";
    private static final String INTERPOLATED_TYPE = "interpolated";

    @Autowired
    CSVReader csvReader;

    public WFunction mapToObject(LinkedHashMap<String, Object> hashMap) {
        String type = (String)hashMap.get("type");

        if(CONSTANT_TYPE.equals(type)) {
            double value = (double)hashMap.get("value");

            return new ConstantWFunction(value);
        } else if(INTERPOLATED_TYPE.equals(type)) {
            String path = (String)hashMap.get("path");
            double peakWavelength = (double)hashMap.get("peakWavelength");
            double peakValue = (double)hashMap.get("peakValue");
            int wavelengthColumn = (int)hashMap.get("wavelengthColumn");
            int valueColumn = (int)hashMap.get("valueColumn");

            return getInterpolatedFunction(path, peakWavelength, peakValue, wavelengthColumn, valueColumn);
        } else {
            return null;
        }
    }

    private WFunction getInterpolatedFunction(String path, double peakWavelength, double peakValue, int wavelengthColumn, int valueColumn) {
        List<WValue> values = csvReader.readData(path, wavelengthColumn, valueColumn);

        WFunction function1 = new InterpolatedWFunction(values);
        WFunction function2;

        double currentPeakValue = function1.evaluate(new Wavelength(0, peakWavelength));
        if(currentPeakValue != 0) {
            function2 = new ScaledWFunction(function1, peakValue/currentPeakValue);
        } else {
            function2 = function1;
        }

        return new CachedWFunction(function2);
    }
}
