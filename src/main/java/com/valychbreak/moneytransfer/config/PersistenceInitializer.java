package com.valychbreak.moneytransfer.config;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersistenceInitializer {
    @Inject
    PersistenceInitializer(PersistService service) {
        service.start();
    }
}
