package org.example;

import java.lang.reflect.Array;
import java.util.*;

public class ReminderService {
    private Map<String, String> members = null;
    private ArrayList<CleaningRotaEntry> rotaEntries;
    private CircularRota rota;
    private WhatsappSender whatsappSender;
    private String menuString = null;
    Thread thread = null;
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
        WhatsappReceiver whatsappReceiver = new WhatsappReceiver(this);
        thread = new Thread(whatsappReceiver);
        thread.start();
        sendWhatsappMenu(null);

    }

    public void sendWhatsappMenu(String sender) {
        members.entrySet().stream()
                        .forEach(entry -> {
                            String phone = entry.getValue();
                            whatsappSender.sendWhatsapp(phone, menuString);

                        });
    }

    public void processMessage(String message, String senderPhone) {
        String sender = members.get(senderPhone.substring(9));
        int option = Utils.getFirstNonNullCharinString(message)-'0';
        switch(option) {
            case 1:
                completeHouseClean(sender, message);
                break;
            case 2:
                sendWhatsappMenu(sender);
        }
    }

    private void completeHouseClean(String sender, String message) {
        rota.iterate();
        //TODO
    }

    private void initialiseMenu() {
        menuString = "House SMS Service Started.\n" +
                "\n" +
                "USAGE:\n"+
                "When house clean is complete, text the number 1 followed by the extra task note\n"+
                "EXAMPLE: 1 Cleaned windows\n"+
                "\n"+
                "Current Rota:\n" +
                rota.getRotaAsString() +
                "\n" +
                "Options:\n" +
                "[1] House clean done (If its your turn)\n" +
                "[2] View Updated House Rota\n" +
                "[3] View Updated Bins Rota\n";
    }

    public void remindMember() {
        //TODO
    }
}
