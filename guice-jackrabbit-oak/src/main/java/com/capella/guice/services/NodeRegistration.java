package com.capella.guice.services;

import javax.jcr.RepositoryException;
import java.io.IOException;

/**
 * @author Ramesh Rajendran
 */
public interface NodeRegistration {
    void register() throws RepositoryException, IOException;
}
