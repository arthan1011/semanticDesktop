package org.arthan.semantic.desktop.sample.service;

import java.io.InputStream;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public interface FileService {
    String extractExtension(String path);

    InputStream ontologyInputStream();

    String toUnixPath(String path);
}
