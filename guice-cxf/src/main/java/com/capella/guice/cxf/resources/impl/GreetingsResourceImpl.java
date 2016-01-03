
package com.capella.guice.cxf.resources.impl;

import com.capella.guice.cxf.entity.Person;
import com.capella.guice.cxf.entity.PersonBuilder;
import com.capella.guice.cxf.resources.GreetingResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class GreetingsResourceImpl implements GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/greet/xml")
    @Override
    public Response greet() {
        Person person = PersonBuilder.aPerson().firstName("Ramesh").lastName("Rajendran").id(System.currentTimeMillis()).build();
        final GenericEntity<Person> entity = new GenericEntity<Person>(person) {
        };
        return Response.ok().entity(entity).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/greet/json")
    @Override
    public Response greetJson() {
        Person person = PersonBuilder.aPerson().firstName("Ramesh").lastName("Rajendran").id(System.currentTimeMillis()).build();
        final GenericEntity<Person> entity = new GenericEntity<Person>(person) {
        };
        return Response.ok().entity(entity).build();
    }
}