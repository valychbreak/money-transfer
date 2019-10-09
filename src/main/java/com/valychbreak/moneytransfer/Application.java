package com.valychbreak.moneytransfer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {
    public static void main(String[] args) {
        SparkApplicationLauncher sparkApplicationLauncher = new SparkApplicationLauncher(8080);
        sparkApplicationLauncher.launch();

        // For testing purpose only
        sparkApplicationLauncher.createTestAccounts();
    }
}
