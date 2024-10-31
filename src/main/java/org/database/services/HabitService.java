package org.database.services;

import org.database.Habit;
import org.database.HabitCategory;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

public class HabitService extends DataBase {
    public Habit addHabit(String name, String description, HabitCategory habit_cat) {
        Habit to_add = new Habit(name, description, habit_cat);

        em.getTransaction().begin();
        em.persist(to_add);

        em.getTransaction().commit();
        System.out.println("Added " + to_add.getId());

        return to_add;
    }

    public void changeName(Habit h, String newName) {
        em.getTransaction().begin();

        Habit hInDB = em.find(Habit.class, h.getId());
        if (hInDB == null) {
            throw new EntityNotFoundException("Habit " + h.getId() + " not found");
        }

        h.setName(newName);
        em.getTransaction().commit();
    }

    public void changeDescription(Habit h, String newDescription) {
        em.getTransaction().begin();

        Habit hInDB = em.find(Habit.class, h.getId());
        if (hInDB == null) {
            throw new EntityNotFoundException("Habit " + h.getId() + " not found");
        }


        h.setDescription(newDescription);
        em.getTransaction().commit();
    }

    @Transactional
    public void deleteHabit(Habit h) {
        Habit habitInDB = em.find(Habit.class, h.getId());
        if (habitInDB == null) {
            throw new EntityNotFoundException("HabitCategory " + h.getId() + " not found");
        }

        em.getTransaction().begin();
        em.remove(habitInDB);
        em.getTransaction().commit();
        System.out.println("Deleted " + h);
    }


}
