package com.epam.mjc.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileReader {

    public Profile getDataFromFile(File file) {
        Path filePath = file.toPath();
        String content;
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            content = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }

        Map<String, String> dataMap = parseContent(content);

        String name = dataMap.get("Name").trim();
        Integer age = Integer.parseInt(dataMap.get("Age").trim());
        String email = dataMap.get("Email").trim();
        Long phone = Long.parseLong(dataMap.get("Phone").trim());

        return new Profile(name, age, email, phone);
    }

    private Map<String, String> parseContent(String content) {
        Map<String, String> dataMap = new HashMap<>();
        String[] lines = content.split("\n");
        for (String line : lines) {
            String[] keyValue = line.split(": ");
            if (keyValue.length == 2) {
                dataMap.put(keyValue[0], keyValue[1]);
            }
        }
        return dataMap;
    }
}
