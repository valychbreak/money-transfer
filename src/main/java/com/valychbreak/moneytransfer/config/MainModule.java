package com.valychbreak.moneytransfer.config;

import com.google.inject.AbstractModule;

public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new PersistenceModule());
        install(new RepositoryModule());
        install(new ControllerModule());
    }
}
