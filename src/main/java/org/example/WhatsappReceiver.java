package org.example;

import spark.Spark;
import static spark.Spark.*;

public class WhatsappReceiver implements Runnable{
    ReminderService reminderService;

    public WhatsappReceiver(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    public void run() {
        //Spark.port(4040);
            get("/", (req, res) -> "Hello Web");

            post("/sms", (req, res) -> {
                reminderService.processMessage(req.queryParams("Body"), req.queryParams("From"));

                res.status(200);
                return null;
            });

            Spark.awaitInitialization();
    }

}