package org.arthan.semantic.desktop.sample;

/**
 * Created by artur.shamsiev on 25.05.2015
 */
public class FileUtils {

    public static FILE_TYPE defineType(String path) {
        String fileExtension = extractExtension(path);
        return FILE_TYPE.forExtension(fileExtension);
    }

    private static String extractExtension(String path) {
        String unixPath = toUnixPath(path);
        int lastSlash = unixPath.lastIndexOf("/");
        int lastDot = unixPath.indexOf(".", lastSlash);
        return unixPath.substring(lastDot + 1);
    }

    public static String toUnixPath(String path) {
        return path.replaceAll("\\\\", "/");
    }
}
