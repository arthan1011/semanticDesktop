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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphItem graphItem = (GraphItem) o;

        if (!label.equals(graphItem.label)) return false;
        if (!uri.equals(graphItem.uri)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + uri.hashCode();
        return result;
    }
}
