package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CleaningRotaEntry {
    private String name;

    private Date date;

    public CleaningRotaEntry(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return formattedDate;
    }
}
