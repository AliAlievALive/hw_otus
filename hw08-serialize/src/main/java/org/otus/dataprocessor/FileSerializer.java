package org.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;
    private final ObjectMapper mapper;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
        this.mapper = JsonMapper.builder().build();
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        var file = new File(fileName);
        Map<String, Double> sortedData = new LinkedHashMap<>();
        data.keySet().stream()
                .sorted()
                .forEach(key -> sortedData.put(key, data.get(key)));
        mapper.writeValue(file, sortedData);
    }
}
