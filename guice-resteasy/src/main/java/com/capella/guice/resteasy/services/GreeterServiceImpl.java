package com.capella.guice.resteasy.services;


import com.capella.guice.resteasy.entity.Person;

import java.time.LocalDate;

public class GreeterServiceImpl implements GreeterService {
    public Person greet(final String name) {
        Person person = new Person();
        person.setName(name);
        person.setMessage("Hello world !! " + LocalDate.now());
        return person;
    }
}