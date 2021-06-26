package org.eztools.api.storage;

import java.util.List;

public interface Storage {

    boolean has(String key);

    String remove(String key);

    void removeAll();

    String get(String key);

    void set(String key, String value);

    List<String> keys();

    List<String> values();

}
