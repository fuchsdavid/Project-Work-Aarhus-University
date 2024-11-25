package org.database.services;

import org.database.Entry;
import org.database.Habit;
import java.time.LocalDate;


import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

public class EntryService extends DataBase {

    public List<Entry> getAllEntriesFromHabit(Habit habit) {
        if (em.find(Habit.class, habit.getId()) == null) {
            throw new EntityNotFoundException("habit "+ habit.getHabitName() +" not found");
        }

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Entry> criteriaQuery = criteriaBuilder.createQuery(Entry.class);
        Root<Entry> root = criteriaQuery.from(Entry.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("habit"), habit));

        return em.createQuery(criteriaQuery).getResultList();
    }

    public Entry addEntryToHabit(Habit habitToAdd, LocalDate date) {
        if(em.find(Habit.class, habitToAdd.getId()) == null) {
            throw new EntityNotFoundException("habit "+ habitToAdd.getHabitName() +" not found");
        }

        Entry entryToAdd = new Entry(habitToAdd);
        entryToAdd.setDate(date);


        em.getTransaction().begin();
        em.persist(entryToAdd);
        em.getTransaction().commit();
        System.out.println("Added entry " + entryToAdd.getId());
        return entryToAdd;
    }

    public Entry getEntryByHabitAndDate(Habit habit, LocalDate date) {
        if(em.find(Habit.class, habit.getId()) == null) {
            throw new EntityNotFoundException("habit "+ habit.getHabitName() +" not found");
        }

        List<Entry> entries = this.getAllEntriesFromHabit(habit);
        for (Entry entry : entries) {
            if (entry.getDate().equals(date)) {
                return entry;
            }
        }

        throw new EntityNotFoundException("entry not found");
    }

    @Transactional
    public void deleteEntry(Entry toDelete) {
        Entry entryInDB = em.find(Entry.class, toDelete.getId());
        if (entryInDB == null) {
            throw new EntityNotFoundException("HabitCategory " + toDelete.getId() + " not found");
        }
        em.getTransaction().begin();
        em.remove(entryInDB);
        em.getTransaction().commit();
        System.out.println("Deleted " + toDelete.getId());
    }

    public void changeEntryValue(Entry entryToChange, boolean value) {
        em.getTransaction().begin();

        Entry entryInDB = em.find(Entry.class, entryToChange.getId());
        if (entryInDB == null) {
            throw new EntityNotFoundException("Entry " + entryToChange.getId() + " not found");
        }

        entryToChange.setValue(value);
        em.getTransaction().commit();
    }
}
