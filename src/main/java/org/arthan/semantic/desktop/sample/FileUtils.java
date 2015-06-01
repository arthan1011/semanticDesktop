package org.arthan.semantic.desktop.sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by artur.shamsiev on 25.05.2015
 */
public class FileUtils {

    public static final String USER_HOME = System.getProperty("user.home");
    public static final Properties props;

    static {
        props = new Properties();
        try {
            props.load(FileUtils.class.getResourceAsStream("/desktop.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extractExtension(String path) {
        String unixPath = toUnixPath(path);
        int lastSlash = unixPath.lastIndexOf("/");
        String fileName = unixPath.substring(lastSlash + 1);
        int lastDot = fileName.lastIndexOf(".");
        return fileName.substring(lastDot + 1);
    }

    public static InputStream ontologyInputStream() {
        String path = USER_HOME + props.getProperty("path.ontology");
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toUnixPath(String path) {
        return path.replaceAll("\\\\", "/");
    }
}
