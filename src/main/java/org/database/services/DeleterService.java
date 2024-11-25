package org.database.services;


import org.database.Entry;
import org.database.Habit;
import org.database.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class DeleterService extends DataBase {

    public void deleteHabit(Habit habitToDelete) {
        HabitService habitService = new HabitService();
        EntryService entryService = new EntryService();

        Habit habitInDB = em.find(Habit.class, habitToDelete.getId());
        if (habitInDB == null) {
            throw new EntityNotFoundException("Habit " + habitToDelete.getHabitName() + " not found");
        }

        List<Entry> entriesToDelete = entryService.getAllEntriesFromHabit(habitInDB);
        for (Entry entry : entriesToDelete) {
            entryService.deleteEntry(entry);
        }

        habitService.deleteHabit(habitToDelete);

        habitService.stopConnection();
        entryService.stopConnection();
        System.out.println("deleted habit");
    }

    public void deleteUser(User userToDelete) {
        UserService userService = new UserService();
        HabitService habitService = new HabitService();

        User userInDB = em.find(User.class, userToDelete.getUserName());
        if (userInDB == null) {
            throw new EntityNotFoundException("User " + userToDelete.getUserName() + " not found");
        }

        List<Habit> habitsToDelete = habitService.getAllUserHabits(userInDB);

        for(Habit habitToDelete : habitsToDelete) {
            this.deleteHabit(habitToDelete);
        }

        userService.deleteUser(userToDelete);

        userService.stopConnection();
        habitService.stopConnection();
    }



}
