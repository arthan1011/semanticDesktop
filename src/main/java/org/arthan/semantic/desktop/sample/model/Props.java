package org.arthan.semantic.desktop.sample.model;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;

/**
 * Created by artur.shamsiev on 01.06.2015
 */
public class Props {
    private static Model m = ModelFactory.createDefaultModel();
    public static final String URI = "http://artur.lazy-magister.org/properties#";
    public static final Property fileExtension = m.createProperty(URI + "fileExtension");
    public static final Property forType = m.createProperty(URI + "forType");
}
