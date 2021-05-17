package ru.mrak.util;

import java.util.Optional;

public class FileUtil {

    private FileUtil() {}

    /**
     * Возвращает имя файла без расширения. Если расширения нет, возвращает null
     */
    public static Optional<String> getName(String file) {
        if (file == null) return Optional.empty();
        int dotIndex = file.lastIndexOf(".");
        if (dotIndex == -1) {
            return Optional.empty();
        }
        return Optional.of(file.substring(0, dotIndex));
    }

    /**
     * Возвразает расширение файла. Если расширения нет, возвращает null
     */
    public static Optional<String> getExtends(String file) {
        if (file == null) return Optional.empty();
        int dotIndex = file.lastIndexOf(".");
        if (dotIndex == -1) {
            return Optional.empty();
        }
        return Optional.of(file.substring(++dotIndex));
    }
}
