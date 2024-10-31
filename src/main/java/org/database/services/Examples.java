package org.database.services;

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

    public void deletingAUser() {
        UserService userService = new UserService();
        DeleterService deleterService = new DeleterService();

        // create a user
        // you can save it to variable to use it later
        User added = userService.addUser("not exists", "", "", 1, "", "");
        // or can just find it
        User found = userService.getUser("not exists");

        System.out.println(found == added); // those are the same

        deleterService.deleteUser(found);


        deleterService.stopConnection();
        userService.stopConnection();
    }


    public void changingEntityFields() {
        // it's pretty much the same for every service
        // you cannot update primary keys

        UserService userService = new UserService();
        DeleterService deleterService = new DeleterService();

        // add a user and see his instance in console
        User userAdded = userService.addUser("some_username", "", "", 1, "", "");
        System.out.println(userAdded);

        userService.updateSurname(userAdded, "some_new_surname");
        userService.updateName(userAdded, "some_new_name");
        userService.updateAge(userAdded, 12);
        userService.updateEmail(userAdded, "some_new_email@email.com");
        userService.updatePassword(userAdded, "new password");

        // see the updates
        System.out.println(userAdded);

        // delete it
        deleterService.deleteUser(userAdded);

        deleterService.stopConnection();
        userService.stopConnection();
    }

    public void gettingAHabitCategoryByName() {
        HabitCategoryService habitCategoryService = new HabitCategoryService();
        HabitCategory habicCategory = habitCategoryService.getHabitCategoryByName("Health");

        System.out.println(habicCategory);

        habitCategoryService.stopConnection();
    }

    public void creatingAHabitCategory() {
        HabitCategoryService habitCategoryService = new HabitCategoryService();
        HabitCategory hc = habitCategoryService.addHabitCategory("UnHealth", "Some description");

        System.out.println(hc);

        // WARNING
        // TODO if we want to delete a habit category, we would have to delete ALL journals related to that category
        boolean dont_do_that = true;
        if (dont_do_that == true) {
            habitCategoryService.deleteHabitCategory(hc);
        }

        habitCategoryService.stopConnection();
    }

    public void gettingUserJournals() {
        UserService userService = new UserService();
        JournalService journalService = new JournalService();

        User johnDoe = userService.getUser("john_doe");
        List<Journal> johnDoeJournals = journalService.getUserJournals(johnDoe);

        for (Journal journal : johnDoeJournals) {
            System.out.println(journal);
        }

        userService.stopConnection();
        journalService.stopConnection();
    }

    public void createJournalForUser() {
        UserService userService = new UserService();
        JournalService journalService = new JournalService();
        EntryService entryService = new EntryService();
        HabitService habitService = new HabitService();
        HabitCategoryService habitCategoryService = new HabitCategoryService();
        GoalService goalService = new GoalService();
        DeleterService deleterService = new DeleterService();

        // new user (to delete later), you can just search for existing with getUser(userName)
        User brian = userService.addUser("brian_the_great", "brian", "rock", 21, "brian123@gmail.com", "password123");

        // brian wants to do sport
        HabitCategory categoryForBrian = habitCategoryService.getHabitCategoryByName("Sport");

        // brian set goal to: run for 10km daily
        Goal goalForBrian = goalService.addGoal("10km", "I want to run for 10km daily");

        // brian need to describe his habit
        // he specified earlier what HabitCategory for him is
        Habit habitForBrian = habitService.addHabit("Run", "I want to run so much", categoryForBrian);

        // we are ready to create a journal for his new habit
        // activity type is a unit to measure
        Journal newJournal = journalService.addJournal(habitForBrian, goalForBrian, brian, "km");

        // let's see the journal
        System.out.println(newJournal);
           /*[JOURNAL FOR brian_the_great]
	            currentStreak: 0
	            startDate: 2024-10-25
	            endDate: null
	            activity type: km
	            habit: Run*/

        // let's add some entries
        Entry entry1 = entryService.addEntry("3km", newJournal);
        Entry entry2 = entryService.addEntry("4km", newJournal);
        Entry entry3 = entryService.addEntry("5km", newJournal);
        Entry entry4 = entryService.addEntry("6km", newJournal);

        // let's see those entries
        List<Entry> brianEntries = journalService.getAllEntriesByJournal(newJournal);
        for (Entry entry : brianEntries) {
            System.out.println(entry);
        }

        /*[Entry ID: 6
	        Date: 2024-10-25
	        Value: 3km
	        Journal: 8]
	      (...)
	    */

        // let's say that brian completed his daily streak TODO need to check if that completes his goal!
        journalService.incrementCurrentStreak(newJournal);

        // brian decided to quit
        journalService.setEndDate(newJournal, LocalDate.now());

        // see the changes
        System.out.println(newJournal);

        /*[JOURNAL FOR brian_the_great]
	        currentStreak: 1
	        startDate: 2024-10-25
	        endDate: 2024-10-25
	        activity type: km
	        habit: Run*/


        // delete journal takes care for deleting it's goal and all entries
        deleterService.deleteJournal(newJournal);

        deleterService.deleteUser(brian);

        deleterService.stopConnection();
        goalService.stopConnection();
        habitCategoryService.stopConnection();
        habitService.stopConnection();
        entryService.stopConnection();
        journalService.stopConnection();
        userService.stopConnection();
    }



    public void ex() {
        //startingAService();

        //gettingSingleUser();

        //creatingAUser();

        //deletingAUser();

        //changingEntityFields();

        //gettingAHabitCategoryByName();

        //creatingAHabitCategory();

        createJournalForUser();

        //gettingUserJournals();
    }

}
