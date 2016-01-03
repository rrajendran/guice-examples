package com.capella.guice.cxf.servlet;

import com.capella.guice.cxf.guice.modules.ApplicationModule;
import com.capella.guice.cxf.guice.modules.GuiceServletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new GuiceServletModule(), new ApplicationModule());
    }
}