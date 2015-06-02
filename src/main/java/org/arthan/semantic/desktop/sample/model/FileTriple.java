package org.arthan.semantic.desktop.sample.model;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public class FileTriple {
    private String filePath;
    private GraphItem predicate;
    private GraphItem object;

    public FileTriple(String filePath, GraphItem predicate, GraphItem object) {
        this.filePath = filePath;
        this.predicate = predicate;
        this.object = object;
    }

    public String getFilePath() {
        return filePath;
    }

    public GraphItem getPredicate() {
        return predicate;
    }

    public GraphItem getObject() {
        return object;
    }
}