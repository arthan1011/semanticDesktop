package org.arthan.semantic.desktop.sample.service;

import org.arthan.semantic.desktop.sample.model.FileResourceGuiData;
import org.arthan.semantic.desktop.sample.model.FileTriple;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public interface UserResourceService {
    FileResourceGuiData initGuiData(String filePath);

    String addFile(FileTriple fileTriple);
}