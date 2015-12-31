package com.capella.guice.dao;

import com.capella.guice.entity.Person;
import com.google.inject.Inject;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 * Copyright 2015 (c) Mastek UK Ltd
 * <p>
 * Created on : 12/31/15
 *
 * @author Ramesh Rajendran
 */
@MappedSuperclass
@NamedQueries({
        @NamedQuery(name = "Person.findPersonById", query = "SELECT d from Person d where d.id = :id")
})
public class PersonDaoImpl implements PersonDao {
    @Transient
    @Inject
    private Provider<EntityManager> entityManagerProvider;

    public Person savePerson(Person person) {
        EntityManager entityManager = entityManagerProvider.get();
        entityManager.persist(person);

        return person;
    }

    public Person getPerson(Long id) {
        return entityManagerProvider.get().find(Person.class,id);
    }
}
