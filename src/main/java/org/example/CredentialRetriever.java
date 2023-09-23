package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class CredentialRetriever {
    private String accountSid;
    private String authToken;
    private String phoneNumber;

    public CredentialRetriever() {
        Properties properties = new Properties();
        File configFile = new File("/home/alex/Desktop/SMS-Reminder-Service/.idea/twilio.properties");
        try (InputStream input = new FileInputStream(configFile)) {
            properties.load(input);
            accountSid = properties.getProperty("account_sid");
            authToken = properties.getProperty("auth_token");
            phoneNumber = properties.getProperty("phone_number");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}