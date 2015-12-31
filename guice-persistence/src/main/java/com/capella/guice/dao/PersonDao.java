package com.capella.guice.dao;

import com.capella.guice.entity.Person;

/**
 * Copyright 2015 (c) Mastek UK Ltd
 * <p>
 * Created on : 12/31/15
 *
 * @author Ramesh Rajendran
 */
public interface PersonDao {

    public Person savePerson (Person person);

    public Person getPerson(Long id);
}
