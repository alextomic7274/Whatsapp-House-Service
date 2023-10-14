package org.example;

import java.util.*;

public class ReminderService {
    private Map<String, String> members = null;
    private CircularRota rota;
    private List<String> rotaEntries;
    private WhatsappSender whatsappSender;
    private Thread thread = null;

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
        whatsappSender.sendWhatsapp("+353871620886", "Test");
        rota = new CircularRota(new ArrayList<>(members.keySet()));
        WhatsappReceiver whatsappReceiver = new WhatsappReceiver(this);
        // TODO Sends text if hardcoded but cant read number from hashmap correctly

        thread = new Thread(whatsappReceiver);
        thread.start();

        Utils.setMenuString(rota.getRotaAsString());
        //broadcastMessage(Utils.getMenuString());
        //cleaningScheduler = new CleaningScheduler(this);
        //cleaningScheduler.startScheduler(3);
    }

    public void broadcastMessage(String message) {
        members.entrySet().stream()
                        .forEach(entry -> {
                            String phone = entry.getValue();
                            whatsappSender.sendWhatsapp(phone, message);
                        });
    }

    public synchronized void processMessage(String message, String senderPhone) {
        System.out.println(message + "\n" + senderPhone);
        String sender = Utils.searchMapByValue(members, senderPhone.substring(9));
        int option = Utils.getFirstNonNullCharinString(message)-'0';
        switch(option) {
            case 1:
                if (!validateSMSSender(sender)) {
                    whatsappSender.sendWhatsapp(senderPhone.substring(9), "Error: You are not due to clean " + sender);
                    break;
                }
                completeHouseClean(sender, message);
                break;
            case 2:
                broadcastMessage(rota.getRotaAsString());
        }
    }

    private void completeHouseClean(String sender, String message) {
        rota.iterate();
        cleaningScheduler.resetScheduler();
        rotaEntries.add(Utils.getRotaEntryString(sender, message));
    }

    public void remindMember() {
        whatsappSender.sendWhatsapp(members.get(rota.getDueHousemate()), rota.getDueHousemate()+", You are due to clean the house :)\n When finished, text the number 1 followed by the extra task note");
    }

    private boolean validateSMSSender(String sender) {
        if (sender.equals(rota.getDueHousemate())) return true;
        return false;
    }
}
