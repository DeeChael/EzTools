package org.eztools.storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.eztools.api.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class YamlStorage implements Storage {

    private final File file;

    private FileConfiguration fileConfiguration;

    public YamlStorage(File file) {
        this.file = file;
        if (!(file.exists() || file.isFile())) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public boolean has(String key) {
        return fileConfiguration.contains(key.replace(".", "_"));
    }

    @Override
    public String remove(String key) {
        if (fileConfiguration.contains(key.replace(".", "_"))) {
            String value = fileConfiguration.getString(key.replace(".", "_"));
            fileConfiguration.set(key.replace(".", "_"), null);
            try {
                fileConfiguration.save(file);
                return value;
            } catch (IOException ignored) {
            }
        }
        return null;
    }

    @Override
    public void removeAll() {
        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.save(file);
        } catch (IOException ignored) {
        }
    }

    @Override
    public String get(String key) {
        if (fileConfiguration.contains(key.replace(".", "_"))) {
            return fileConfiguration.getString(key.replace(".", "_"));
        }
        return null;
    }

    @Override
    public void set(String key, String value) {
        this.fileConfiguration.set(key.replace(".", "_"), value);
        try {
            fileConfiguration.save(file);
        } catch (IOException ignored) {
        }
    }

    @Override
    public List<String> keys() {
        return new ArrayList<>(fileConfiguration.getKeys(false));
    }

    @Override
    public List<String> values() {
        List<String> values = new ArrayList<>();
        for (String key : this.keys()) {
            if (fileConfiguration.isString(key)) {
                values.add(fileConfiguration.getString(key));
            }
        }
        return values;
    }
}
