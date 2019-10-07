package com.valychbreak.moneytransfer;

import lombok.extern.java.Log;

import static spark.Spark.port;
import static spark.Spark.post;

@Log
public class Application {
    public static void main(String[] args) {
        port(8080);

        establishRoutes();
    }

    public static void establishRoutes() {
        post("/transfer", (req, res) -> {
            res.type("application/json");
            return "{\"status\": \"Hello, world!\"}";
        });
    }
}
