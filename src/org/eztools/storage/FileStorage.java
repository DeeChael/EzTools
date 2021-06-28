package org.eztools.storage;

import org.eztools.api.storage.Storage;

import java.io.File;
import java.io.IOException;

public abstract class FileStorage implements Storage {

    private final File file;

    private boolean isFirstTime = false;

    public FileStorage(File file) {
        this.file = file;
        if (!(file.exists() || file.isFile())) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
                this.isFirstTime = true;
            } catch (IOException ignored) {
            }
        }
    }

    public File getFile() {
        return this.file;
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

}
