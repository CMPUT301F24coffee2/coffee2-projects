package com.example.test_code;

import java.util.ArrayList;
import java.util.List;

public class EventList {
    private List<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }

    public void add(Event event) {
        events.add(event);
    }

    public void remove(Event event) {
        events.remove(event);
    }

    public List<Event> getEvents() {
        return events;
    }
}
