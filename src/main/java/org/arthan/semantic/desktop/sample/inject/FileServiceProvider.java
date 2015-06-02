package org.arthan.semantic.desktop.sample.inject;

import com.google.inject.Provider;
import org.arthan.semantic.desktop.sample.service.FileService;
import org.arthan.semantic.desktop.sample.service.impl.FileServiceImpl;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public class FileServiceProvider implements Provider<FileService> {

    private String USER_HOME = System.getProperty("user.home");
    private Properties props;

    public FileServiceProvider() {
        props = new Properties();
        try {
            props.load(FileServiceImpl.class.getResourceAsStream("/desktop.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileService get() {
        String ontologyPath = USER_HOME + props.getProperty("path.ontology");
        return new FileServiceImpl(ontologyPath);
    }
}
