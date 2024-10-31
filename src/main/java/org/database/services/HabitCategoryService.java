package org.database.services;

import org.database.HabitCategory;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

public class HabitCategoryService extends DataBase {

    public HabitCategory getHabitCategory(int habit_cat_id) {
        HabitCategory habit_cat_in_db = em.find(HabitCategory.class, habit_cat_id);
        if (habit_cat_in_db == null) {
            throw new EntityNotFoundException("Habit " + habit_cat_id + " not found");
        }
        return habit_cat_in_db;
    }

    public HabitCategory addHabitCategory(String name, String description) {
        HabitCategory to_add = new HabitCategory(name, description);

        HabitCategory habitCategoryInDB = em.find(HabitCategory.class, name);

        if (habitCategoryInDB != null) {
            throw new EntityExistsException("Habit category " + to_add.getName() + " already exists");
        }

        em.getTransaction().begin();
        em.persist(to_add);

        em.getTransaction().commit();
        System.out.println("Added " + to_add.getName());

        return to_add; //with id given by auto-increment
    }

    public void deleteHabitCategory(HabitCategory habit_cat) {
        HabitCategory habit_cat_in_db = em.find(HabitCategory.class, habit_cat.getName());
        if (habit_cat_in_db == null) {
            throw new EntityNotFoundException("HabitCategory " + habit_cat.getName() + " not found");
        }

        em.getTransaction().begin();
        em.remove(habit_cat_in_db);
        em.getTransaction().commit();
        System.out.println("Deleted " + habit_cat);
    }

    public void changeDescription(HabitCategory hc, String newDescription) {
        HabitCategory hcInDB = em.find(HabitCategory.class, hc.getName());
        if (hcInDB == null) {
            throw new EntityNotFoundException("HabitCategory " + hc.getName() + " not found");
        }

        em.getTransaction().begin();
        hc.setDescription(newDescription);
        em.getTransaction().commit();
    }

    public HabitCategory getHabitCategoryByName(String name) {
        HabitCategory habitCategoryInDB = em.find(HabitCategory.class, name);
        if (habitCategoryInDB == null) {
            throw new EntityNotFoundException("HabitCategory " + name + " not found");
        }
        return habitCategoryInDB;
    }


}
