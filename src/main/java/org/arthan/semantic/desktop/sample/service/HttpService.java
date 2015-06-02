package org.arthan.semantic.desktop.sample.service;

import org.arthan.semantic.desktop.sample.model.GraphItem;

import java.util.List;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public interface HttpService {
    List<GraphItem> resourcesForClasses(List<String> objectClassesUri);

    String addFile(String fileName, String predicateUri, String objectUri);
}
