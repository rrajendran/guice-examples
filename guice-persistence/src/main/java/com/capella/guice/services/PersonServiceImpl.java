package com.capella.guice.services;

import com.capella.guice.dao.PersonDao;
import com.capella.guice.entity.Person;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * Copyright 2015 (c) Mastek UK Ltd
 * <p>
 * Created on : 12/31/15
 *
 * @author Ramesh Rajendran
 */
public class PersonServiceImpl implements PersonService{
    @Inject
    private PersonDao personDao;

    @Transactional
    public Person savePerson(Person person) {
        return personDao.savePerson(person);
    }

    @Transactional
    public Person getPerson(Long id) {
        return personDao.getPerson(id);
    }
}
