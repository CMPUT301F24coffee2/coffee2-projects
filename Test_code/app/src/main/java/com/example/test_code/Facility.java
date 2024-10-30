package com.example.test_code;

import java.util.ArrayList;
import java.util.List;

public class Facility {
    private String name;
    private String location;
    private boolean violatesPolicy;

    public Facility(String name, String location) {
        this.name = name;
        this.location = location;
        this.violatesPolicy = false;
        // Default is no violation
    }

    // Method to mark a facility as violating app policy
    public void markViolation() {
        this.violatesPolicy = true;
        System.out.println("Facility " + name + " marked as violating policy.");
    }

    // Method to clear the violation flag if necessary
    public void clearViolation() {
        this.violatesPolicy = false;
        System.out.println("Facility " + name + " cleared of policy violation.");
    }

    // Method to check if the facility violates policy
    public boolean isViolatingPolicy() {
        return violatesPolicy;
    }

    // Additional getters and setters
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}




