package com.valychbreak.moneytransfer.config;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

public class PersistenceModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.install(new JpaPersistModule("db-manager"));
        binder.bind(PersistenceInitializer.class).asEagerSingleton();
    }
}
