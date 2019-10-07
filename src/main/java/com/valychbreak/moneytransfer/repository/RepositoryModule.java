package com.valychbreak.moneytransfer.repository;

import com.google.inject.AbstractModule;

public class RepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountRepository.class);
    }
}
