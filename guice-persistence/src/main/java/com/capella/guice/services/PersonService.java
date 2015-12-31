package com.capella.guice.services;

import com.capella.guice.entity.Person;
import com.google.inject.persist.Transactional;

/**
 * Copyright 2015 (c) Mastek UK Ltd
 * <p>
 * Created on : 12/31/15
 *
 * @author Ramesh Rajendran
 */
public interface PersonService {

    @Transactional
    public Person savePerson(Person person);

    @Transactional
    public Person getPerson(Long id);
}
