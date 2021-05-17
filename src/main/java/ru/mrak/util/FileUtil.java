package ru.mrak.util;

public class FileUtil {

    private FileUtil() {}

    /**
     * Возвращает имя файла без расширения. Если расширения нет, возвращает null
     */
    public static String getName(String file) {
        int dotIndex = file.lastIndexOf(".");
        if (dotIndex == -1) {
            return null;
        }
        return file.substring(0, dotIndex);
    }

    /**
     * Возвразает расширение файла. Если расширения нет, возвращает null
     */
    public static String getExtends(String file) {
        int dotIndex = file.lastIndexOf(".");
        if (dotIndex == -1) {
            return null;
        }
        return file.substring(++dotIndex);
    }
}
