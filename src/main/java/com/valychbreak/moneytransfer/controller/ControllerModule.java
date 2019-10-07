package com.valychbreak.moneytransfer.controller;

import com.google.inject.AbstractModule;

public class ControllerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AssetTransferController.class);
    }
}
