package org.arthan.semantic.desktop.sample;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import org.arthan.semantic.desktop.sample.model.GraphItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 27.05.2015
 */
public class GraphUtils {
    public static List<GraphItem> findPredicatesForType(FILE_TYPE type) {
        List<GraphItem> resultList;

        OntModel ontModel = ModelFactory.createOntologyModel();
        ontModel.read(FileUtils.ontologyInputStream(), null);

        ExtendedIterator<OntProperty> allPropIterator = ontModel.listAllOntProperties();
        OntClass fileClass = ontModel.getOntClass(type.getUri());

        ExtendedIterator<OntProperty> propertyIterator = fileClass.listDeclaredProperties();
        List<OntProperty> ontProperties = Lists.newArrayList(propertyIterator);

        resultList = ontProperties.stream()
                .map(input -> new GraphItem(input.getLabel(null), input.getURI()))
                .collect(Collectors.toList());
        return resultList;
    }
}
