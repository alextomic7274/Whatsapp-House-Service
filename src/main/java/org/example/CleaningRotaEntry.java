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
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
