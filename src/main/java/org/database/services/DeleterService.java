package org.database.services;


import org.database.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

public class DeleterService extends DataBase {

    public void deleteUser(User userToDelete) {
        UserService userService = new UserService();

        User userInDB = em.find(User.class, userToDelete.getUserName());
        if (userInDB == null) {
            throw new EntityNotFoundException("User " + userToDelete.getUserName() + " not found");
        }

        //search for journals of this user
        JournalService journalService = new JournalService();
        try {
            List<Journal> journalsToDelete = journalService.getUserJournals(userToDelete);

            for (Journal journalToDelete : journalsToDelete) {
                deleteJournal(journalToDelete);
            }
        } catch (Exception e) {
            System.out.println("journal empty");
        }

        userService.deleteUser(userToDelete);

        userService.stopConnection();
    }

    @Transactional
    public void deleteJournal(Journal journalToDelete) {
        Journal journalInDB = em.find(Journal.class, journalToDelete.getId());
        if (journalInDB == null) {
            throw new EntityNotFoundException("Journal " + journalToDelete.getId() + " not found");
        }
        JournalService journalService = new JournalService();
        GoalService goalService = new GoalService();
        EntryService entryService = new EntryService();
        HabitService habitService = new HabitService();

        // get goal to delete
        Goal goalToDelete = journalToDelete.getGoal();
        Habit habitToDelete = journalToDelete.getHabit();

        // get entries to delete
        List<Entry> entries = journalService.getAllEntriesByJournal(journalToDelete);

        // delete all
        for (Entry entry : entries) {
            entryService.deleteEntry(entry);
        }

        journalService.deleteJournal(journalToDelete);
        goalService.deleteGoal(goalToDelete);
        habitService.deleteHabit(habitToDelete);


        habitService.stopConnection();
        journalService.stopConnection();
        goalService.stopConnection();
        entryService.stopConnection();
    }
}
