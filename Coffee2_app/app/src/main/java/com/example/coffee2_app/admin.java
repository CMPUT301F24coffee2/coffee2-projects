package com.example.coffee2_app;

import java.util.ArrayList;
public class Admin extents User{
    // Attributes
    private UserList userList;             // Stores list of all users (Entrant, Organizer, Admin)
    private EventList eventList;           // Stores list of all events
    private FacilityList facilityList;     // Stores list of all facilities
    private PictureList pictureList;       // Stores images for profiles and events
    
    // Constructor
    public Admin(UserList userList, EventList eventList, FacilityList facilityList, PictureList pictureList) {
        this.userList = userList;
        this.eventList = eventList;
        this.facilityList = facilityList;
        this.pictureList = pictureList;
    }

    // Methods

    // Removes an event by event ID
    public void removeEvent(int eventId) {
        eventList.removeEvent(eventId);
    }

    // Removes a user profile by user ID
    public void removeProfile(int userId) {
        userList.removeUser(userId);
    }

    // Removes an image by image ID
    public void removeImage(int imageId) {
        pictureList.removeImage(imageId);
    }

    // Removes a QR code entry by code ID
    public void removeQRCode(int codeId) {
        // Assume each QR code is associated with an event in eventList
        eventList.removeQRCode(codeId);
    }

    // Browse events
    public void browseEvents() {
        eventList.displayAllEvents();
    }

    // Browse user profiles
    public void browseProfiles() {
        userList.displayAllUsers();
    }

    // Browse images
    public void browseImages() {
        pictureList.displayAllImages();
    }

    // Remove facility by facility ID
    public void removeFacility(int facilityId) {
        facilityList.removeFacility(facilityId);
    }
}
