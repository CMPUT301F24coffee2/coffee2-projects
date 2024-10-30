package com.example.test_code;

import java.util.ArrayList;
import java.util.List;

public class FacilityList {
    private List<Facility> facilities;

    public FacilityList() {
        facilities = new ArrayList<>();
    }

    public void add(Facility facility) {
        facilities.add(facility);
    }

    public void remove(Facility facility) {
        facilities.remove(facility);
    }
}