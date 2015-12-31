package com.capella.guice.resteasy.services;


import com.capella.guice.resteasy.entity.Person;

public interface GreeterService {
    public Person greet(final String name);
}