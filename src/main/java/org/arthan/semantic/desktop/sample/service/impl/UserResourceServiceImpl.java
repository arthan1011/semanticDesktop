package org.arthan.semantic.desktop.sample.service.impl;

import org.arthan.semantic.desktop.sample.FileType;
import org.arthan.semantic.desktop.sample.model.FileResourceGuiData;
import org.arthan.semantic.desktop.sample.model.FileTriple;
import org.arthan.semantic.desktop.sample.model.GraphItem;
import org.arthan.semantic.desktop.sample.service.FileService;
import org.arthan.semantic.desktop.sample.service.GraphService;
import org.arthan.semantic.desktop.sample.service.HttpService;
import org.arthan.semantic.desktop.sample.service.UserResourceService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public class UserResourceServiceImpl implements UserResourceService {

    GraphService graphService;
    FileService fileService;
    HttpService httpService;

    @Inject
    public UserResourceServiceImpl(GraphService graphService, FileService fileService, HttpService httpService) {
        this.graphService = graphService;
        this.fileService = fileService;
        this.httpService = httpService;
    }

    @Override
    public FileResourceGuiData initGuiData(String filePath) {
        FileType fileType = graphService.findFileType(fileService.extractExtension(filePath));

        List<GraphItem> predicates = graphService.findPredicatesForType(fileType);
        GraphItem defaultPredicate = predicates.get(0);

        List<String> objectsUri = graphService.findObjectClassesFor(fileType, defaultPredicate.getUri());
        List<GraphItem> objects = httpService.resourcesForClasses(objectsUri);
        GraphItem defaultObject = objects.get(0);

        FileTriple defaultTriple = new FileTriple(filePath, defaultPredicate, defaultObject);

        FileResourceGuiData guiData = FileResourceGuiData.builder()
                .setTriple(defaultTriple)
                .setFileType(fileType)
                .setPredicates(predicates)
                .setObjects(objects)
                .build();

        return guiData;
    }

    @Override
    public String addFile(FileTriple fileTriple) {
        return httpService.addFile(
                fileTriple.getFilePath(),
                fileTriple.getPredicate().getUri(),
                fileTriple.getObject().getUri()
        );
    }
}
