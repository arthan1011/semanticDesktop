package org.arthan.semantic.desktop.sample.service;

import org.arthan.semantic.desktop.sample.FileType;
import org.arthan.semantic.desktop.sample.model.GraphItem;

import java.util.List;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public interface GraphService {
    List<GraphItem> findPredicatesForType(FileType type);

    List<String> findObjectClassesFor(FileType type, String predicateURI);

    FileType findFileType(String fileExtension);
}
