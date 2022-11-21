package com.fibersim.resources.csv;

import com.fibersim.core.raytracing.wavelength.WValue;
import com.fibersim.core.raytracing.wavelength.Wavelength;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVReader {
    private static final String CSV_PREFIX = "/csv/";
    private static final String CSV_SUFFIX = ".csv";

    public List<WValue> readData(String path, int wavelengthColumn, int valueColumn) {
        Resource resource = new ClassPathResource(CSV_PREFIX+path+CSV_SUFFIX);
        List<WValue> result = new ArrayList<>();

        try(BufferedReader csvReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            String[] elements;
            for(int i = 0 ; (line = csvReader.readLine()) != null ; i++) {
                elements = line.split(",");

                double lambda = Double.parseDouble(elements[wavelengthColumn]);
                double value = Double.parseDouble(elements[valueColumn]);

                result.add(new WValue(new Wavelength(i, lambda), value));
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
