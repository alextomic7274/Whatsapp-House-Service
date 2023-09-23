package org.example;

public class Housemate {
    private String name = null;
    private String phoneNumber = null;

    public Housemate(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = "whatsapp:"+phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

