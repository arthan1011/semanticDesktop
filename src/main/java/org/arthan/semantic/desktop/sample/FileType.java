package org.arthan.semantic.desktop.sample;

import com.hp.hpl.jena.ontology.OntClass;

/**
 * Created by artur.shamsiev on 01.06.2015
 */
public class FileType {
    private String extension;
    private String title;
    private OntClass targetType;

    public FileType(String extension, String title, OntClass targetType) {
        this.extension = extension;
        this.title = title;
        this.targetType = targetType;
    }

    public String getExtension() {
        return extension;
    }

    public String getTitle() {
        return title;
    }

    public OntClass getTargetType() {
        return targetType;
    }
}
