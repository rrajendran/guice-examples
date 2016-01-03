package com.capella.guice.cxf.resources;

import javax.ws.rs.core.Response;

/**
 * @author ramesh
 */
public interface GreetingResource {

    public Response greet();

    public Response greetJson();
}
