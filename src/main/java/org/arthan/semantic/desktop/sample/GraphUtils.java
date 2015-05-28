package org.arthan.semantic.desktop.sample;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import org.arthan.semantic.desktop.sample.model.GraphItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 27.05.2015
 */
public class GraphUtils {

    private static OntModel ontModel;

    public static List<GraphItem> findPredicatesForType(FILE_TYPE type) {
        List<GraphItem> resultList;

        ontModel = ModelFactory.createOntologyModel();
        ontModel.read(FileUtils.ontologyInputStream(), null);

        OntClass fileClass = ontModel.getOntClass(type.getUri());

        List<OntProperty> propsForDomain = findPropertiesForDomain(fileClass);

        resultList = propsForDomain.stream()
                .map(input -> new GraphItem(input.getLabel(null), input.getURI()))
                .collect(Collectors.toList());
        return resultList;
    }

    private static List<OntProperty> findPropertiesForDomain(OntClass fileClass) {
        List<OntProperty> allProperties = Lists.newArrayList(ontModel.listAllOntProperties());

        return allProperties.stream()
            .filter(input -> {
                List<OntResource> domains = Lists.newArrayList(input.listDomain());
                return domains.stream()
                        .anyMatch(d -> fileClass.getURI().equals(d.getURI()));
            }).collect(Collectors.toList());
    }

    public static List<String> findObjectClassesFor(FILE_TYPE type, String predicateURI) {
        ontModel = ModelFactory.createOntologyModel();
        ontModel.read(FileUtils.ontologyInputStream(), null);

        OntClass fileClass = ontModel.getOntClass(type.getUri());

        List<OntProperty> propsForDomain = findPropertiesForDomain(fileClass);

        List<String> objectClassesUri = propsForDomain.stream()
                .flatMap(input -> Lists.newArrayList(input.listRange()).stream())
                .map(Resource::getURI)
                .collect(Collectors.toList());
        return objectClassesUri;
    }
}
