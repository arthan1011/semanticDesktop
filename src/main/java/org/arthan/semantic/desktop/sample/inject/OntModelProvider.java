package org.arthan.semantic.desktop.sample.inject;

import com.google.inject.Provider;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import org.arthan.semantic.desktop.sample.service.FileService;

import javax.inject.Inject;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public class OntModelProvider implements Provider<OntModel> {

    private FileService fileService;

    @Inject
    public OntModelProvider(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public OntModel get() {
        OntModel ontModel = ModelFactory.createOntologyModel();
        ontModel.read(fileService.ontologyInputStream(), null);
        return ontModel;
    }
}
