package com.valychbreak.moneytransfer;

import com.google.inject.AbstractModule;
import com.valychbreak.moneytransfer.config.PersistenceModule;
import com.valychbreak.moneytransfer.controller.ControllerModule;
import com.valychbreak.moneytransfer.repository.RepositoryModule;

public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new PersistenceModule());
        install(new RepositoryModule());
        install(new ControllerModule());
    }
}
