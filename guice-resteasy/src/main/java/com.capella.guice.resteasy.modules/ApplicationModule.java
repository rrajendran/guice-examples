package com.capella.guice.resteasy.modules;

import com.capella.guice.resteasy.resources.HelloResource;
import com.capella.guice.resteasy.services.GreeterServiceImpl;
import com.capella.guice.resteasy.services.GreeterService;
import com.google.inject.Binder;
import com.google.inject.Module;

public class ApplicationModule implements Module {
    public void configure(final Binder binder) {
        binder.bind(HelloResource.class);
        binder.bind(GreeterService.class).to(GreeterServiceImpl.class);
    }
}