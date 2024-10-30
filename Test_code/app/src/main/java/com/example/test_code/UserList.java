package com.example.test_code;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(user);
    }

    public List<User> getUsers() {
        return users;
    }
}

