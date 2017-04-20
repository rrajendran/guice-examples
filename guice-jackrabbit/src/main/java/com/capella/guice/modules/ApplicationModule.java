package com.capella.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import java.util.Properties;

import static com.google.inject.name.Names.bindProperties;

/**
 * Copyright 2015 (c) Mastek UK Ltd
 * <p>
 * Created on : 12/31/15
 *
 * @author Ramesh Rajendran
 */
public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new JackrabbitModule());
        binder().bind(Properties.class).toProvider(PropertiesProvider.class).in(Singleton.class);
        bindProperties(binder(), PropertiesProvider.getProperties());
    }
}
