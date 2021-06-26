package org.eztools.storage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eztools.api.storage.Storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class JsonStorage implements Storage {

    private final File file;
    private JsonObject jsonObject;

    public JsonStorage(File file) {
        this.file = file;
        if (!(file.exists() || file.isFile())) {
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("{}");
                fileWriter.close();
            } catch (IOException ignored) {
            }
        }
        try {
            this.jsonObject = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
        } catch (FileNotFoundException e) {
            this.jsonObject = new JsonObject();
        }
    }

    @Override
    public boolean has(String key) {
        return jsonObject.has(key);
    }

    @Override
    public String remove(String key) {
        if (jsonObject.has(key) && jsonObject.get(key).isJsonPrimitive()) {
            String string = jsonObject.remove(key).getAsString();
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(new Gson().toJson(this.jsonObject));
                fileWriter.close();
                return string;
            } catch (IOException ignored) {
                return string;
            }
        }
        return null;
    }

    @Override
    public void removeAll() {
        this.jsonObject = new JsonObject();
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new Gson().toJson(this.jsonObject));
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public String get(String key) {
        if (jsonObject.has(key) && jsonObject.get(key).isJsonPrimitive()) {
            return jsonObject.get(key).getAsString();
        }
        return null;
    }

    @Override
    public void set(String key, String value) {
        this.jsonObject.addProperty(key, value);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new Gson().toJson(this.jsonObject));
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public List<String> keys() {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : this.jsonObject.entrySet()) {
            keys.add(entry.getKey());
        }
        return keys;
    }

    @Override
    public List<String> values() {
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : this.jsonObject.entrySet()) {
            String value = this.get(entry.getKey());
            if (value != null) {
                values.add(value);
            }
        }
        return values;
    }
}
