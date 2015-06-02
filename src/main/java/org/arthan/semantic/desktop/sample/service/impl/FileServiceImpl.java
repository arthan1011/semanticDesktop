package org.arthan.semantic.desktop.sample.service.impl;

import com.google.inject.Scopes;
import org.arthan.semantic.desktop.sample.service.FileService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public class FileServiceImpl implements FileService {
    private String ontologyPath;

    public FileServiceImpl(String ontologyPath) {
        this.ontologyPath = ontologyPath;
    }

    @Override
    public String extractExtension(String path) {
        String unixPath = toUnixPath(path);
        int lastSlash = unixPath.lastIndexOf("/");
        String fileName = unixPath.substring(lastSlash + 1);
        int lastDot = fileName.lastIndexOf(".");
        return fileName.substring(lastDot + 1);
    }

    @Override
    public InputStream ontologyInputStream() {
        try {
            return new FileInputStream(ontologyPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toUnixPath(String path) {
        return path.replaceAll("\\\\", "/");
    }
}
