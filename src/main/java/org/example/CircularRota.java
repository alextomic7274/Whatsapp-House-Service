package org.example;


import java.util.ArrayList;

public class CircularRota {
    private ArrayList<String> rota;
    private int current;

    public CircularRota(ArrayList<String> members) {
        rota = members;
        current = 0;
    }

    public void iterate() {
        current = (current + 1) % rota.size();
    }

    public String getDueHousemate() {
        return rota.get(current);
    }

    public String getRotaAsString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rota.size(); i++) {
            if (i == current) {
                sb.append(rota.get(i)+" (NEXT)\n");
            }   else sb.append(rota.get(i)+"\n");
        }
        return sb.toString();
    }
}
