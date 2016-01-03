package com.capella.guice.cxf.guice.modules;

import com.capella.guice.cxf.resources.GreetingResource;
import com.capella.guice.cxf.resources.impl.GreetingsResourceImpl;
import com.google.inject.AbstractModule;

/**
 * @author ramesh
 */
public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GreetingResource.class).to(GreetingsResourceImpl.class);
    }
}
