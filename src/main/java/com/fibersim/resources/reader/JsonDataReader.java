package com.fibersim.resources.reader;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public abstract class JsonDataReader<T> {
    private static final String DATA_PREFIX = "/data/";
    private static final String DATA_SUFFIX = ".json";

    protected List<T> elementList;

    @Autowired
    ObjectMapper objectMapper;

    protected abstract String jsonDataFile();

    protected abstract T mapToObject(LinkedHashMap<String, Object> hashMap);

    @PostConstruct
    private void readJsonData() {
        Resource resource = new ClassPathResource(DATA_PREFIX+jsonDataFile()+DATA_SUFFIX);
        String jsonString = "";

        try(Scanner scanner = new Scanner(resource.getInputStream()).useDelimiter("\\A")) {
            jsonString = scanner.hasNext() ? scanner.next() : "";
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            List<LinkedHashMap<String, Object>> jsonDataList = objectMapper.readValue(jsonString, new TypeReference<>() {});
            elementList = jsonDataList.stream().map(this::mapToObject).collect(Collectors.toList());
        } catch(JacksonException e) {
            e.printStackTrace();
        }
    }
}
