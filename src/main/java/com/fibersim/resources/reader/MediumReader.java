package com.fibersim.resources.reader;

import com.fibersim.core.raytracing.wavelength.function.WFunction;
import com.fibersim.core.resources.medium.Medium;
import com.fibersim.resources.parser.WFunctionParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class MediumReader extends JsonDataReader<Medium> {
    @Autowired
    WFunctionParser wFunctionParser;

    public Medium read(String name) {
        return elementList.stream().filter(medium -> medium.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    protected String jsonDataFile() {
        return "mediums";
    }

    @Override
    protected Medium mapToObject(LinkedHashMap<String, Object> hashMap) {
        String name = (String)hashMap.get("name");
        WFunction refractionIndex = wFunctionParser.mapToObject((LinkedHashMap<String, Object>)hashMap.get("refractionIndex"));
        WFunction attenuation = wFunctionParser.mapToObject((LinkedHashMap<String, Object>)hashMap.get("attenuation"));
        return new Medium(name, refractionIndex, attenuation);
    }
}
