package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private ReminderService reminderService;
    private MenuDisplay menuDisplay = new MenuDisplay();
    private Housemate housemate = null;
    private boolean running = false;

    public Menu() {
        scanner = new Scanner(System.in);
        reminderService = new ReminderService();
    }

    public void startConsoleMenu() {
        menuDisplay.showMain();
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                registerMembers();
                break;
            case 2:
                reminderService.printMembers();
                startConsoleMenu();
                break;
            case 3:
                removeMember();
            case 4:
                reminderService.startService();
                break;
        }


    }

    public void registerMembers() {
        System.out.println("Member Quantity: ");
        int housemateCount = -1;

        while (housemateCount < 0) {
            try {
                housemateCount = scanner.nextInt();
                if (housemateCount < 0) {
                    System.out.println("Invalid Input. Only non negative ints");
                }
            }   catch (InputMismatchException e) {
                System.out.println("Invalid Input. Enter valid number");
                scanner.next();
            }
        }
            for (int i = 1; i <= housemateCount; i++) {
                System.out.println("Member "+i);
                System.out.println("Name: ");
                String name = scanner.next();
                System.out.println("Phone number");
                String phoneNumber = scanner.next();
                reminderService.addHousemate(housemate = new Housemate(name, phoneNumber));
                reminderService.printMembers();
            }

        System.out.println("\nINFO: Housemate registration complete\n");
        startConsoleMenu();
    }

    public void removeMember() {
        System.out.println("Enter member name: ");
        reminderService.removeHousemate(scanner.next());
        startConsoleMenu();
    }

}