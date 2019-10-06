package com.valychbreak.moneytransfer.repository;

import com.google.inject.AbstractModule;

public class AccountRepositoryBindingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountRepository.class);
    }
}
