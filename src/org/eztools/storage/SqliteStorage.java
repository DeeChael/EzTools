package org.eztools.storage;

import org.eztools.api.storage.Storage;

import java.util.List;

public final class SqliteStorage implements Storage {


    @Override
    public boolean has(String key) {
        return false;
    }

    @Override
    public String remove(String key) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public List<String> keys() {
        return null;
    }

    @Override
    public List<String> values() {
        return null;
    }
}
