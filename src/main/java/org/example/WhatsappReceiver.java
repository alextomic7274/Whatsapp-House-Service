package org.example;

import spark.Spark;

import java.util.ArrayList;

import static spark.Spark.*;

public class WhatsappReceiver implements Runnable{
    private WhatsappSender whatsappSender;
    private ArrayList<String> commands = new ArrayList<>();
    ReminderService reminderService;

    public WhatsappReceiver(WhatsappSender whatsappSender, ReminderService reminderService) {
        this.whatsappSender = whatsappSender;
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

    private void print() {
        System.out.println(commands);
    }
}