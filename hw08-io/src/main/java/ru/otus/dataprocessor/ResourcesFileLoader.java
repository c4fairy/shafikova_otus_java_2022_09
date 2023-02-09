package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    //читает файл, парсит и возвращает результат
    @Override
    public List<Measurement> load() {
        List<Measurement> dataResult = new ArrayList<>();
        try (var inputStream = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            JsonNode nodes = mapper.readTree(inputStream);
            for (JsonNode node : nodes)
                dataResult.add(new Measurement(node.get("name").asText(), node.get("value").doubleValue()));
        } catch (IOException e) {
            throw new FileProcessException("Не удалось прочитать файл:" + e.getMessage());
        }
        return dataResult;
    }
}
