package com.dpd.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class FileUtil {
    public static String[] getFiles(String path) {
        File dir = new File(path);
        List<String> files = new ArrayList<>();
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                files.add(file.getAbsolutePath());
            }
        }
        return files.toArray(String[]::new);
    }

    public static Properties getProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(path)));
        return properties;
    }
}
