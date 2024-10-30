package com.example.test_code;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String location;
    private QRCode qrCode;
    private List<User> entrants;

    public Event(String name, String location) {
        this.name = name;
        this.location = location;
        this.entrants = new ArrayList<>();
    }

    public void addEntrant(User user) {
        entrants.add(user);
    }

    public void removeEntrant(User user) {
        entrants.remove(user);
    }
}
