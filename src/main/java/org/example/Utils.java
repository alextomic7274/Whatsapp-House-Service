package org.example;

public class Utils {

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

}
