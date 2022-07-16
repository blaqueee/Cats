package com.FileService;

import com.Cat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReaderWriter {
    private Path PATH;
    private Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public FileReaderWriter(String path) {
        this.PATH = Paths.get(path);
    }

    public List<Cat> readFile() {
        Type itemsListType = new TypeToken<List<Cat>>() {}.getType();
        String json = "";
        try {
            json = Files.readString(PATH);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return GSON.fromJson(json, itemsListType);
    }

    public void writeFile(List<Cat> cats) {
        String json = GSON.toJson(cats);
        try {
            byte[] jsonArray = json.getBytes();
            Files.write(PATH, jsonArray);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
