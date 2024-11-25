package org.database.services;


import org.database.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

public class HabitService extends DataBase {


    public Habit addHabitToUser(String name, User user) {

        if (em.find(User.class, user.getUserName()) == null) {
            throw new EntityNotFoundException("User "+ user.getUserName() +" not found");
        }

        Habit habitToAdd = new Habit(name, user);

        List<Habit> habits = this.getAllHabits();
        for (Habit h : habits) {
            if(Objects.equals(h, habitToAdd)) {
                throw new EntityExistsException("habit " + name + " already exists");
            }
        }

        em.getTransaction().begin();
        em.persist(habitToAdd);
        em.getTransaction().commit();
        System.out.println("Added " + habitToAdd.getHabitName());
        return habitToAdd;
    }

    public List<Habit> getAllHabits() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Habit> criteriaQuery = criteriaBuilder.createQuery(Habit.class);
        Root<Habit> root = criteriaQuery.from(Habit.class);

        criteriaQuery.select(root);

        return em.createQuery(criteriaQuery).getResultList();
    }

    public List<Habit> getAllUserHabits(User user) {
        if (em.find(User.class, user.getUserName()) == null) {
            throw new EntityNotFoundException("User "+ user.getUserName() +" not found");
        }

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Habit> criteriaQuery = criteriaBuilder.createQuery(Habit.class);
        Root<Habit> root = criteriaQuery.from(Habit.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), user));

        return em.createQuery(criteriaQuery).getResultList();
    }

    public Habit getUserHabitByName(User user, String habitName) {
        if (em.find(User.class, user.getUserName()) == null) {
            throw new EntityNotFoundException("User "+ user.getUserName() +" not found");
        }

        List<Habit> userHabits = this.getAllUserHabits(user);

        for (Habit h : userHabits) {
            if (h.getHabitName().equals(habitName)) {
                return h;
            }
        }
        throw new EntityNotFoundException("Habit "+ habitName + " not found for " + user.getUserName());
    }

    public void setCurrentStreak(Habit habit, int value) {
        em.getTransaction().begin();

        Habit habitInDb = em.find(Habit.class, habit.getId());
        if (habitInDb == null) {
            throw new EntityNotFoundException("Habit " + habit.getHabitName() + " not found");
        }

        habitInDb.setCurrentStreak(value);
        em.getTransaction().commit();
    }

    public void updateLongestStreak(Habit habit) {
        em.getTransaction().begin();

        Habit habitInDb = em.find(Habit.class, habit.getId());
        if (habitInDb == null) {
            throw new EntityNotFoundException("Habit " + habit.getHabitName() + " not found");
        }

        if (habitInDb.getCurrentStreak() > habitInDb.getLongestStreak()) {
            habitInDb.setLongestStreak(habitInDb.getCurrentStreak());
            em.getTransaction().commit();
        }
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
