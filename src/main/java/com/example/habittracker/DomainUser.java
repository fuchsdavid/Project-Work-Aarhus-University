package com.example.habittracker;

import org.database.User;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Objects;

public class DomainUser {
    private String userName;

    public DomainUser(){}

    public DomainUser(String userName) {
        this.userName = userName;
    }

    public String GetUserName() {
        return userName;
    }
}
