package org.eztools.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonConfiguration {

    private JsonObject jsonObject;

    public JsonConfiguration(String url) {
        try {
            URL url1 = new URL(url);
            InputStream inputStream = url1.openStream();
            char[] cbuf = new char[10000];
            InputStreamReader input = new InputStreamReader(inputStream);
            int len = 0;
            try {
                len = input.read(cbuf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String text = new String(cbuf, 0, len);
            this.jsonObject = new Gson().fromJson(text, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonConfiguration(File file) {
        char[] cbuf = new char[10000];
        InputStreamReader input = null;
        try {
            input = new InputStreamReader(new FileInputStream(file), "UTF-8");
        } catch (UnsupportedEncodingException| FileNotFoundException e) {
            e.printStackTrace();
        }
        int len = 0;
        try {
            len = input.read(cbuf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = new String(cbuf, 0, len);
        this.jsonObject = new Gson().fromJson(text, JsonObject.class);
    }

    public JsonConfiguration(InputStream inputStream) {
        char[] cbuf = new char[10000];
        InputStreamReader input = new InputStreamReader(inputStream);
        int len = 0;
        try {
            len = input.read(cbuf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = new String(cbuf, 0, len);
        this.jsonObject = new Gson().fromJson(text, JsonObject.class);
    }

    JsonConfiguration(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonConfiguration getJsonObject(String key) {
        return new JsonConfiguration(this.jsonObject.getAsJsonObject(key));
    }

    public Object get(String key) {
        return this.jsonObject.get(key);
    }

    public String getString(String key) {
        return this.jsonObject.get(key).getAsString();
    }

    public Boolean getBoolean(String key) {
        return this.jsonObject.get(key).getAsBoolean();
    }

    public Integer getInteger(String key) {
        return this.jsonObject.get(key).getAsInt();
    }

    public Double getDouble(String key) {
        return this.jsonObject.get(key).getAsDouble();
    }

    public Float getFloat(String key) {
        return this.jsonObject.get(key).getAsFloat();
    }

    public Long getLong(String key) {
        return this.jsonObject.get(key).getAsLong();
    }

    public Byte getByte(String key) {
        return this.jsonObject.get(key).getAsByte();
    }

    public Short getShort(String key) {
        return this.jsonObject.get(key).getAsShort();
    }

    public Character getCharacter(String key) {
        return this.jsonObject.get(key).getAsCharacter();
    }

    public List<JsonConfiguration> getJsonObjectsInJsonArray(String key) {
        JsonArray jsonArray = this.jsonObject.get(key).getAsJsonArray();
        List<JsonConfiguration> list = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonConfiguration jsonConfiguration = new JsonConfiguration(jsonObject);
            list.add(jsonConfiguration);
        }
        return list;
    }

    public JsonConfiguration subJson(String key, int witch) {
        return new JsonConfiguration(this.jsonObject.getAsJsonArray(key).get(witch).getAsJsonObject());
    }

    public static List<JsonConfiguration> asJsonArray(String jsonArray) {
        JsonArray jsonArray1 = new Gson().fromJson(jsonArray, JsonArray.class);
        List<JsonConfiguration> list = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray1) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonConfiguration jsonConfiguration = new JsonConfiguration(jsonObject);
            list.add(jsonConfiguration);
        }
        return list;
    }

}
