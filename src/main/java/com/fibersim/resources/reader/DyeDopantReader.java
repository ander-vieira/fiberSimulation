package com.fibersim.resources.reader;

import com.fibersim.core.raytracing.wavelength.function.WFunction;
import com.fibersim.core.resources.dopant.DyeDopant;
import com.fibersim.resources.parser.WFunctionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class DyeDopantReader extends JsonDataReader<DyeDopant> {
    @Autowired
    WFunctionParser wFunctionParser;

    public DyeDopant read(String name) {
        return elementList.stream().filter(dyeDopant -> dyeDopant.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    protected String jsonDataFile() {
        return "dyeDopants";
    }

    @Override
    protected DyeDopant mapToObject(LinkedHashMap<String, Object> hashMap) {
        String name = (String)hashMap.get("name");
        double quantumYield = (double)hashMap.get("quantumYield");
        WFunction sigmaAbs = wFunctionParser.mapToObject((LinkedHashMap<String, Object>)hashMap.get("sigmaAbs"));
        WFunction sigmaEmi = wFunctionParser.mapToObject((LinkedHashMap<String, Object>)hashMap.get("sigmaEmi"));
        return new DyeDopant(name, quantumYield, sigmaAbs, sigmaEmi);
    }
}
