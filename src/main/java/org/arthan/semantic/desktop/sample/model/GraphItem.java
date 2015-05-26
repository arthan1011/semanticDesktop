package org.arthan.semantic.desktop.sample.model;

/**
 * Created by artur.shamsiev on 26.05.2015
 */
public class GraphItem {
    private String label;
    private String uri;

    @Override
    public String toString() {
        return label;
    }

    public GraphItem(String label, String uri) {
        this.label = label;
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public String getUri() {
        return uri;
    }
}
