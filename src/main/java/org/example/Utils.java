package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static String menuString = null;

    public static char getFirstNonNullCharinString(String s) {
        if (s == null) {
            return '\0';
        }
        for (char c : s.toCharArray()) {
            if (c != '\0') {
                return c;
            }
        }
        return '\0';
    }

    public static void setMenuString(String rotaString) {
        menuString = "House SMS Service Started.\n" +
                "\n" +
                "USAGE:\n"+
                "When house clean is complete, text the number 1 followed by the extra task note\n"+
                "EXAMPLE: 1 Cleaned windows\n"+
                "\n"+
                "Current Rota:\n" +
                rotaString +
                "\n" +
                "Options:\n" +
                "[1] House clean done (If its your turn)\n" +
                "[2] View Updated House Rota\n" +
                "[3] View Updated Bins Rota\n";
    }

    public static String getMenuString() {
        return menuString;
    }

    public static String getRotaEntryString(String member, String note) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return (currentDate.format(formatter) + " - " + member + " - " + note);
    }

    // Generic method that searches any hashmap for a key according to the value.
    public static <K, V> K searchMapByValue(Map<K, V> map, V targetValue) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(targetValue)) {
                return entry.getKey();
            }
        }
        return null;
    }


}
