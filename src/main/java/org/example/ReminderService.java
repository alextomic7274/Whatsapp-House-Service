package org.example;

import java.lang.reflect.Array;
import java.util.*;

public class ReminderService {
    private Map<String, String> members = null;
    private ArrayList<CleaningRotaEntry> rotaEntries;
    private CircularRota rota;
    private WhatsappSender whatsappSender;
    public ReminderService() {
        members = new HashMap<>();
        whatsappSender = new WhatsappSender();
    }

    public void addHousemate(Housemate housemate) {
        members.put(housemate.getPhoneNumber(), housemate.getName());
    }

    public void removeHousemate(String name) {
        members.remove(name);
    }

    public void printMembers() {
        for (Map.Entry<String, String> entry : members.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Name:" + value + " - Phone:" + key);
        }
    }

    public void startService() {
        ArrayList<String> rotaMembers = new ArrayList<>(members.keySet());
        System.out.println(rotaMembers);
        rota = new CircularRota(rotaMembers);
        WhatsappReceiver whatsappReceiver = new WhatsappReceiver(whatsappSender, this);
        Thread thread = new Thread(whatsappReceiver);
        thread.start();
        rota.iterate();
        rota.iterate();
        rota.iterate();
        System.out.println("House SMS Service Started.\n" +
                "\n" +
                "This tool will help housemates manage cleaning duties\n" +
                "\n" +
                "Current House Clean Rota:\n" +
                rota.getRotaAsString() +
                "\n" +
                "Options:\n" +
                "[1] House clean done (If its your turn)\n" +
                "[2] Bins taken out (Will rotate through separate list)\n" +
                "[4] View Updated House Rota\n" +
                "[5] View Updated Bins Rota\n" +
                "[6] Help\n");
    }

    public void stopService() {


    }

    public void sendWhatsappMenu() {
        members.entrySet().stream()
                        .forEach(entry -> {
                            String phone = entry.getValue();
                            whatsappSender.sendWhatsapp(phone, "House SMS Service Started.\n" +
                                    "\n" +
                                    "This tool will help housemates manage cleaning duties\n" +
                                    "\n" +
                                    "Current House Clean Rota:\n" +
                                    rota.getRotaAsString() +
                                    "\n" +
                                    "Options:\n" +
                                    "[1] House clean done (If its your turn)\n" +
                                    "[2] Bins taken out (Will rotate through separate list)\n" +
                                    "[4] View Updated House Rota\n" +
                                    "[5] View Updated Bins Rota\n" +
                                    "[6] Help\n");

                        });
    }

    public void processMessage(String message, String senderPhone) {
        String sender = members.get(senderPhone.substring(9));
        System.out.println("Message: "+message+"\n"+"Sender: "+sender);
    }
}
