package org.example;

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


}
