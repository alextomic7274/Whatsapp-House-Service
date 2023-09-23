package org.example;

public class MenuDisplay {
    private boolean running = false;

    public void setStatus(boolean running) {
        this.running = running;
    }

    public void showMain() {
        String mainMenu = "Text Reminder Service - Main Menu\n" +
                "------------------------------------\n" +
                "RUNNING: " + running + "\n" +
                "------------------------------------\n" +
                "1. Register Housemates\n" +
                "2. View Registered Housemates\n" +
                "3. Remove Housemates\n" +
                "4. Start Text Service\n" +
                "------------------------------------\n" +
                "Specify 1 - 4 then press enter: ";

        System.out.println(mainMenu);

    }



}