package org.arthan.semantic.desktop.sample;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 27.05.2015
 */
public class OntoTest {
    @Test
    public void testOntology() throws Exception {
        String path = System.getProperty("user.home") + "/semantic/ontologies/client.xml";
        FileInputStream fileInputStream = new FileInputStream(path);

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        model.read(fileInputStream, null);

        OntClass cClass = model.getOntClass("http://artur.lazy-magister.org/types/document");
        System.out.println(cClass.getURI());
        System.out.println(cClass.getSuperClass().getURI());

        ExtendedIterator<OntProperty> allPropertiesIterator = model.listAllOntProperties();
        List<OntProperty> allProperties = Lists.newArrayList(allPropertiesIterator);
        List<OntProperty> filterList = allProperties.stream()
                .filter(input -> {
                    List<OntResource> domains = Lists.newArrayList(input.listDomain());
                    boolean inDomain = domains.stream()
                            .anyMatch(d -> cClass.getURI().equals(d.getURI()));
                    return inDomain;
                }).collect(Collectors.toList());
        for (OntProperty ontProperty : filterList) {
            System.out.println("\\\\\"");
            System.out.println(ontProperty.getURI());
            System.out.println("\\\\\"");
        }


        ExtendedIterator<OntProperty> ontPropertyExtendedIterator = cClass.listDeclaredProperties();
        while (ontPropertyExtendedIterator.hasNext()) {
            OntProperty next = ontPropertyExtendedIterator.next();
            System.out.println("\t" + next.getURI());
            ExtendedIterator<? extends OntResource> listDomain = next.listDomain();
            ExtendedIterator<? extends OntResource> listRange = next.listRange();
            while (listDomain.hasNext()) {
                OntResource ontResource = listDomain.next();
                System.out.println("\t\t" + ontResource.getURI());
            }
            while (listRange.hasNext()) {
                OntResource ontResource = listRange.next();
                System.out.println("\t\t" + ontResource.getURI());
            }
        }

//        OntProperty ontProperty = model.getOntProperty("http://artur.lazy-magister.org/properties#IMAGE");
//        model.write(new FileOutputStream(path));
//        System.out.println(ontProperty.getDomain().getURI());

    }
}
