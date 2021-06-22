package org.eztools.api.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.Plugin;
import org.eztools.api.color.ColorFormat;

import java.io.*;

public class Language {

    private boolean firstOpenAndFileNotExist = false;
    private final File file;
    private JsonObject jsonObject = new JsonObject();

    public Language(Plugin plugin, String language) {
        File folder = new File("language/" + plugin.getName());
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            if (!folder.isDirectory()) {
                folder.mkdirs();
            }
        }
        file = new File("language/" + plugin.getName() + "/" + language + ".json");
        if (!file.exists()) {
            firstOpenAndFileNotExist = true;
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("{}");
                fileWriter.close();
            } catch (IOException ignored) {
            }
        }
        try {
            jsonObject = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
        } catch (FileNotFoundException e) {
        }
    }

    public String getString(String path) {
        return jsonObject.has(path) && jsonObject.get(path).isJsonPrimitive() ? ColorFormat.translate(jsonObject.get(path).getAsString().replace("\\n", "\n")) : "Null";
    }

    public void setDefault(String path, String value) {
        if (!jsonObject.has(path)) {
            value = ColorFormat.transfer(value.replace("\n", "\\n"));
            jsonObject.addProperty(path, value);
        }
    }

    public boolean has(String path) {
        return jsonObject.has(path);
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new Gson().toJson(jsonObject));
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    public boolean isFirstOpenAndFileNotExist() {
        return firstOpenAndFileNotExist;
    }
}
