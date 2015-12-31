package com.capella.guice.modules;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;

/**
 * @author DeepakB
 */
public class JpaInitializer {

    @Inject
    public JpaInitializer(final PersistService service) {
        service.start();
    }
}