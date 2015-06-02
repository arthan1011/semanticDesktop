package org.arthan.semantic.desktop.sample.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.hp.hpl.jena.ontology.OntModel;
import org.arthan.semantic.desktop.sample.Controller;
import org.arthan.semantic.desktop.sample.service.FileService;
import org.arthan.semantic.desktop.sample.service.GraphService;
import org.arthan.semantic.desktop.sample.service.HttpService;
import org.arthan.semantic.desktop.sample.service.UserResourceService;
import org.arthan.semantic.desktop.sample.service.impl.GraphServiceImpl;
import org.arthan.semantic.desktop.sample.service.impl.HttpServiceImpl;
import org.arthan.semantic.desktop.sample.service.impl.UserResourceServiceImpl;

/**
 * Created by artur.shamsiev on 02.06.2015
 */
public class DesktopModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(Names.named("test"))
                .toInstance("TEST string");

        bind(GraphService.class)
                .to(GraphServiceImpl.class);

        bind(FileService.class)
                .toProvider(FileServiceProvider.class);

        bind(OntModel.class)
                .toProvider(OntModelProvider.class);

        bind(HttpService.class)
                .to(HttpServiceImpl.class);

        bind(UserResourceService.class)
                .to(UserResourceServiceImpl.class);
    }
}
