package com.capella.guice.modules;

import com.capella.guice.entity.Person;
import com.capella.guice.entity.PersonBuilder;
import com.capella.guice.services.PersonService;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static junit.framework.TestCase.assertNotNull;

/**
 * Copyright 2015 (c) Mastek UK Ltd
 * <p>
 * Created on : 12/31/15
 *
 * @author Ramesh Rajendran
 */
public class ApplicationModuleTest {

    @org.junit.Test
    public void testConfigure() throws Exception {
        Injector injector = Guice.createInjector(new ApplicationModule());

        PersonService personService = injector.getInstance(PersonService.class);
        Person person = PersonBuilder.aPerson().firstName("Ramesh").lastName("Rajendran").build();
        person = personService.savePerson(person);

        Person p = personService.getPerson(person.getId());
        System.out.println(p);
        assertNotNull(p);

    }
}