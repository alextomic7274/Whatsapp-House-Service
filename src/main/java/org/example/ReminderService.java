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
        rota = new CircularRota(new ArrayList<>(members.keySet()));
        WhatsappReceiver whatsappReceiver = new WhatsappReceiver(this);

        thread = new Thread(whatsappReceiver);
        thread.start();

        Utils.setMenuString(rota.getRotaAsString());
        broadcastMessage(Utils.getMenuString(), null);
        cleaningScheduler = new CleaningScheduler(this);
        cleaningScheduler.startScheduler(2);
    }

    public void broadcastMessage(String message, String justThisPerson) {
        members.entrySet()
                .stream()
                .filter(entry -> justThisPerson == null || entry.getKey().equals(justThisPerson))
                .forEach(entry -> {
                            String phone = entry.getValue();
                            whatsappSender.sendWhatsapp(phone, message);
                        });
    }

    public synchronized void processMessage(String message, String senderPhone) {
        System.out.println(senderPhone + ": " + message);
        String sender = Utils.searchMapByValue(members, senderPhone.substring(9));
        int option = Utils.getFirstNonNullCharinString(message)-'0';
        switch(option) {
            case 1:
                if (!validateSMSSender(sender)) {
                    whatsappSender.sendWhatsapp(senderPhone.substring(9), "Error: You are not due to clean " + sender);
                } else {
                    completeHouseClean(sender, message);
                }
                break;
            case 2:
                broadcastMessage(rota.getRotaAsString(), sender);
            case 3:
                sendCleanLog(sender);
        }
    }

    private void completeHouseClean(String sender, String message) {
        rota.iterate();
        cleaningScheduler.resetScheduler();
        broadcastMessage("House clean complete!\n"+rota.getRotaAsString(), null);
        rotaEntries.add(Utils.getRotaEntryString(sender, message));
    }

    public void remindMember() {
        whatsappSender.sendWhatsapp(members.get(rota.getDueHousemate()), rota.getDueHousemate()+", You are due to clean the house :)\n When finished, text the number 1 followed by the extra task note");
    }

    private boolean validateSMSSender(String sender) {
        if (sender.equals(rota.getDueHousemate())) return true;
        return false;
    }

    private void sendCleanLog(String sender) {
        StringBuilder sb = new StringBuilder();
        sb.append("Clean Log:\n");
        for (String s : rotaEntries) {
            sb.append(s+"\n");
        }
        broadcastMessage(sb.toString(), sender);
    }
}
