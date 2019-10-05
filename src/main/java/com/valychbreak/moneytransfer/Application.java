package com.valychbreak.moneytransfer;

import lombok.extern.java.Log;

import static spark.Spark.get;
import static spark.Spark.port;

@Log
public class Application {
    public static void main(String[] args) {
        port(8080);

        get("/hello", (req, res) -> "Hello, world!");
    }
}
