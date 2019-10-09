package com.valychbreak.moneytransfer.config;

import com.google.inject.AbstractModule;
import com.valychbreak.moneytransfer.controller.AssetTransferController;

public class ControllerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AssetTransferController.class);
    }
}
