package com.valychbreak.moneytransfer.config;

import com.google.inject.AbstractModule;
import com.valychbreak.moneytransfer.repository.AccountRepository;

public class RepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountRepository.class);
    }
}
