package com.kakaobank.profile.consumer;

import static spark.Spark.*;

public class ProfileConsumer {
    public static void main(String[] args) {
        get("/api/customer/:customerNumber", (req, res) -> {
            return "Hello Customer : " + req.params(":customerNumber");
        });

        get("/api/customer/:customerNumber/account/:accountNumber", (req, res) -> {
            String customerNumber = req.params(":customerNumber");
            String accountNumber = req.params(":accountNumber");

            return "Hello Customer '" + customerNumber + "', Account : '" + accountNumber +"'";
        });
    }
}
