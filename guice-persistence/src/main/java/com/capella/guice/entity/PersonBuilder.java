package com.capella.guice.entity;

/**
 * Copyright 2015 (c) Mastek UK Ltd
 * <p>
 * Created on : 12/31/15
 *
 * @author Ramesh Rajendran
 */
public class PersonBuilder {
    private String firstName;
    private Long id = System.currentTimeMillis();
    private String lastName;

    private PersonBuilder() {
    }

    public static PersonBuilder aPerson() {
        return new PersonBuilder();
    }

    public PersonBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public PersonBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Person build() {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setId(id);
        person.setLastName(lastName);
        return person;
    }
}
