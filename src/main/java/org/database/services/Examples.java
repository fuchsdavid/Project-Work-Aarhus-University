package org.database.services;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.database.*;

import java.time.LocalDate;
import java.util.List;


public class Examples {

    public void startingAService() {
        // for every service it works the same
        // start a service at the begining, stop at the end
        DataBase db = new DataBase(); // start a connection

        try {
            db.testConnection();
        } catch (Exception e){
            System.out.println("connection failed");
        }

        db.stopConnection(); // stop the connection
    }


    public void gettingSingleUser() {
        UserService userService = new UserService();

        try {
            User johnDoe = userService.getUser("john_doe"); // get a user by it's username
            System.out.println(johnDoe);
        } catch (Exception e) {
            System.out.println("user not found");
        }

        userService.stopConnection();
    }



    public void creatingAUser() {
        // user has unique userName and email
        UserService userService = new UserService();

        try {
            userService.addUser("john_doe", "notJohn", "notDoe", 12, "email@gmail.com", "1234");
        } catch (Exception e) {
            System.out.println("user creation failed, john_doe exists");
        }

        try {
            userService.addUser("not_john_doe", "notJohn", "notDoe", 12, "john_doe@gmail.com", "1234");
        } catch (Exception e) {
            System.out.println("user creation failed, john_doe@gmail.com used");

        }

        userService.stopConnection();
    }

    public void AddingAHabit() {
        HabitService habitService = new HabitService(); // create a service
        UserService userService = new UserService();

        User user = userService.getUser("john_doe"); // existing user
        habitService.addHabitToUser("swimming", user); // non existing habit

        habitService.stopConnection();
        userService.stopConnection();
        // successfully added


        // skip this
        DeleterService deleterService = new DeleterService();
        deleterService.deleteUser(user);
        deleterService.stopConnection();
    }

    public void GettingAUserHabitByName() {
        HabitService habitService = new HabitService(); // start a service
        UserService userService = new UserService();
        User user = userService.getUser("john_doe"); // existing user


        Habit habitFoundInDB = habitService.getUserHabitByName(user, "Running"); // existing habit for john doe


        habitService.stopConnection();
        userService.stopConnection();
    }

    public void GettingAllUserHabits() {
        HabitService habitService = new HabitService(); // start a service
        UserService userService = new UserService();
        User user = userService.getUser("john_doe"); // existing user


        List<Habit> habitsForJohnDoe = habitService.getAllUserHabits(user);


        habitService.stopConnection();
        userService.stopConnection();
    }

    public void AddingAnEntry() {
        EntryService entryService = new EntryService(); // start a service
        HabitService habitService = new HabitService(); // remember that habit has a reference to a user
        UserService userService = new UserService();

        User user = userService.getUser("john_doe"); // get an existing user
        Habit hereYouWillAddAnEntry = habitService.getUserHabitByName(user, "Running"); // existing habit
        LocalDate someDate = LocalDate.now();


        Entry entryAdded = entryService.addEntryToHabit(hereYouWillAddAnEntry, someDate);
        // now you can set parameters
        // ... habitService.setCurrentStreak(...
        // ... habitService.updateLongestStreak(...
        // look at -> SettingStreakParameters()

        entryService.stopConnection();
        userService.stopConnection();
        userService.stopConnection();

        //skip this
        EntryService es = new EntryService();
        es.deleteEntry(entryAdded);
        es.stopConnection();
    }

    public void SettingStreakParameters() {
        HabitService habitService = new HabitService(); // start a service
        UserService userService = new UserService();

        User user = userService.getUser("john_doe"); // existing user
        Habit habit = habitService.getUserHabitByName(user, "Running"); // existing habit

        // 1. oh, no... user forgot to complete their streak
        habitService.setCurrentStreak(habit,0);
        // ! !!!!!!!!!!!!! !
        // ! HABIT SERVICE !
        // ! !!!!!!!!!!!!! !

        // 2. user performs!!!
        habitService.setCurrentStreak(habit,habit.getCurrentStreak() + 1);
        habitService.updateLongestStreak(habit); // it might be longer than the longest streak now


        habitService.stopConnection();
        userService.stopConnection();
    }

    public void GettingAllEntries() {
        HabitService habitService = new HabitService();
        EntryService entryService = new EntryService();
        UserService userService = new UserService();
        User user = userService.getUser("john_doe"); // existing user
        Habit habit = habitService.getUserHabitByName(user, "Running"); // existing habit


        List<Entry> entriesForThisHabit = entryService.getAllEntriesFromHabit(habit);


        habitService.stopConnection();
        userService.stopConnection();
        entryService.stopConnection();
    }

    public void gettingSpecificEntryByDateAndHabit() {
        HabitService habitService = new HabitService();
        EntryService entryService = new EntryService();
        UserService userService = new UserService();
        User user = userService.getUser("john_doe"); // existing user
        Habit habit = habitService.getUserHabitByName(user, "Running"); // existing habit

        // ok so you got a user and a habit
        // let's say that you want to get an entry specifically from 01.02.2024

        LocalDate date = LocalDate.of(2024, 2, 1);
        try {
            Entry entry01022024 = entryService.getEntryByHabitAndDate(habit, date);
        } catch (Exception e) {
            System.out.println("done something");
        }

        habitService.stopConnection();
        userService.stopConnection();
        entryService.stopConnection();
    }

    public void settingEntryOnAndOff() {
        HabitService habitService = new HabitService();
        EntryService entryService = new EntryService();
        UserService userService = new UserService();
        User user = userService.getUser("john_doe"); // existing user
        Habit habit = habitService.getUserHabitByName(user, "Running"); // existing habit

        // let's say you got your entry. Either by getEntryByHabitAndDate(...) or by something else like clicking
        List<Entry> entries = entryService.getAllEntriesFromHabit(habit);
        Entry toChange = entries.getFirst();


        entryService.changeEntryValue(toChange, false);


        entryService.stopConnection();
        userService.stopConnection();
        habitService.stopConnection();
    }


    public void deleting() {
        DeleterService deleterService = new DeleterService();


        UserService userService = new UserService();
        User toDelete = userService.getUser("john_doe"); // existing user



        deleterService.deleteUser(toDelete);
        // ! !!!!!!!!!!!!!!! !
        // ! DELETER SERVICE !
        // ! !!!!!!!!!!!!!!! !

        deleterService.stopConnection();


        // similar for habit and an entry
    }




}
