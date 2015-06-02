package org.arthan.semantic.desktop.sample.model;

import org.arthan.semantic.desktop.sample.FileType;

import java.util.List;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public class FileResourceGuiData {

    private FileTriple triple;

    private FileType fileType;

    private List<GraphItem> predicates;
    private List<GraphItem> objects;

    public FileTriple getTriple() {
        return triple;
    }

    public FileType getFileType() {
        return fileType;
    }

    public List<GraphItem> getPredicates() {
        return predicates;
    }

    public List<GraphItem> getObjects() {
        return objects;
    }

    private FileResourceGuiData() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private FileTriple triple;

        private FileType fileType;

        private List<GraphItem> predicates;
        private List<GraphItem> objects;

        public Builder setTriple(FileTriple triple) {
            this.triple = triple;
            return this;
        }

        public Builder setFileType(FileType fileType) {
            this.fileType = fileType;
            return this;
        }

        public Builder setPredicates(List<GraphItem> predicates) {
            this.predicates = predicates;
            return this;
        }

        public Builder setObjects(List<GraphItem> objects) {
            this.objects = objects;
            return this;
        }

        public FileResourceGuiData build() {
            FileResourceGuiData guiData = new FileResourceGuiData();
            guiData.fileType = this.fileType;
            guiData.triple = this.triple;
            guiData.predicates = this.predicates;
            guiData.objects = this.objects;

            return guiData;
        }
    }
}
