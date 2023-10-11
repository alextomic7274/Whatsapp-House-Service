package org.example;

import java.util.*;

public class ReminderService {
    private Map<String, String> members = null;
    private ArrayList<CleaningRotaEntry> rotaEntries;
    private CircularRota rota;
    private WhatsappSender whatsappSender;
    private String menuString = null;
    Thread thread = null;
    private CleaningScheduler cleaningScheduler = null;
    public ReminderService() {
        members = new HashMap<>();
        whatsappSender = new WhatsappSender();
    }

    public void addHousemate(Housemate housemate) {
        members.put(housemate.getName(), housemate.getPhoneNumber());
    }

    public void removeHousemate(String name) {
        members.remove(name);
    }

    public void printMembers() {
        for (Map.Entry<String, String> entry : members.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Name:" + key + " - Phone:" + value);
        }
    }

    public void startService() {
        ArrayList<String> rotaMembers = new ArrayList<>(members.keySet());
        System.out.println(rotaMembers);
        rota = new CircularRota(rotaMembers);
        WhatsappReceiver whatsappReceiver = new WhatsappReceiver(this);
        thread = new Thread(whatsappReceiver);
        thread.start();
        Utils.setMenuString(rota.getRotaAsString());
        //sendWhatsappMenu(null);
        cleaningScheduler = new CleaningScheduler(this);
        cleaningScheduler.startScheduler(8);
    }

    public void broadcastMessage(String message) {
        members.entrySet().stream()
                        .forEach(entry -> {
                            String phone = entry.getKey();
                            whatsappSender.sendWhatsapp(phone, message);
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
                broadcastMessage(Utils.getMenuString());
        }
    }

    private void completeHouseClean(String sender, String message) {
        rota.iterate();
        cleaningScheduler.resetScheduler();
        //TODO
    }

    public void remindMember() {
        whatsappSender.sendWhatsapp(members.get(rota.getDueHousemate()), rota.getDueHousemate()+", You are due to clean the house :)\n When finished, text the number 1 followed by the extra task note");
    }
}
