package org.arthan.semantic.desktop.sample;

import com.google.common.collect.Lists;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import org.arthan.semantic.desktop.sample.model.GraphItem;
import org.arthan.semantic.desktop.sample.model.Props;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by artur.shamsiev on 27.05.2015
 */
public class GraphUtils {

    public static final String FILE_EXTENSION_CLASS_URI = "http://artur.lazy-magister.org/types/meta/fileExtension";
    private static OntModel ontModel;

    static {
        ontModel = ModelFactory.createOntologyModel();
        ontModel.read(FileUtils.ontologyInputStream(), null);
    }

    /**
     * Возвращает список предикатов (свойств), домен которых совпадает с {@code type.uri}
     * @param type класс домена для предикатов
     * @return список предикатов для {@code type}
     */
    public static List<GraphItem> findPredicatesForType(FileType type) {
        List<GraphItem> resultList;

        OntClass fileClass = type.getTargetType();

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

    /**
     * Возвращает uri классов тех ресурсов, которые могут служить объектом в триплете, для которого
     * субъект определяется параметром {@code type}, а предикат - параметром {@code predicateURI}
     * @param type тип файла. Играет роль субъекта
     * @param predicateURI uri предиката
     * @return список uri классов тех ресурсов, которые могут служить в качестве объекта в триплете
     */
    public static List<String> findObjectClassesFor(FileType type, String predicateURI) {
        OntClass fileClass = type.getTargetType();

        List<OntProperty> propsForDomain = findPropertiesForDomain(fileClass);

        return propsForDomain.stream()
                .filter(prop -> predicateURI.equals(prop.getURI()))
                .flatMap(input -> Lists.newArrayList(input.listRange()).stream())
                .map(Resource::getURI)
                .collect(Collectors.toList());
    }

    /**
     * Находит ресурс типа fileExtension соответствующего параметру {@code fileExtension}
     * @param fileExtension расширение файла
     * @return объект {@link FileType}
     */
    public static FileType findFileType(String fileExtension) {
        OntClass extClass = ontModel.getOntClass(FILE_EXTENSION_CLASS_URI);
        ArrayList<OntResource> extensions = Lists.newArrayList(extClass.listInstances(true));
        List<OntResource> foundExtensions = extensions.stream()
                .filter(input ->
                        input.getPropertyValue(Props.fileExtension).toString().equals(fileExtension))
                .collect(Collectors.toList());
        if (foundExtensions.size() != 1) {
            throw new RuntimeException("Не могу найти единственный ресурс для " + fileExtension);
        }

        return resourceToFileType(foundExtensions.get(0));
    }

    private static FileType resourceToFileType(OntResource resource) {
        OntClass ontClass = resource.getPropertyValue(Props.forType).as(OntClass.class);
        return new FileType(
                resource.getPropertyValue(Props.fileExtension).toString(),
                resource.getLabel(null),
                ontClass
        );
    }

}
