package org.example;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


public class WhatsappSender {
    private CredentialRetriever credentialRetriever;

    public WhatsappSender() {
        credentialRetriever = new CredentialRetriever();
    }

    public void sendWhatsapp(String phone, String textBody) {
        String whatsappPhone = "whatsapp:".concat(phone);
        Twilio.init(credentialRetriever.getAccountSid(), credentialRetriever.getAuthToken());
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(whatsappPhone),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                textBody)

                        .create();
        System.out.println(message.getSid());
    }
}