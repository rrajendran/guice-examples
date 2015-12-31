package com.capella.guice.modules;

import com.capella.guice.dao.PersonDao;
import com.capella.guice.dao.PersonDaoImpl;
import com.capella.guice.services.PersonService;
import com.capella.guice.services.PersonServiceImpl;
import com.google.inject.AbstractModule;

/**
 * Copyright 2015 (c) Mastek UK Ltd
 * <p>
 * Created on : 12/31/15
 *
 * @author Ramesh Rajendran
 */
public class ApplicationModule extends AbstractModule{
    @Override
    protected void configure() {
        install(new JpaPersistenceModule());
        bind(PersonDao.class).to(PersonDaoImpl.class);
        bind(PersonService.class).to(PersonServiceImpl.class);
    }
}
