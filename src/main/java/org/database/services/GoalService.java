package org.database.services;

import org.database.Goal;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

public class GoalService extends DataBase {

    public Goal getGoal(int goal_id) {
        Goal goal_in_db = em.find(Goal.class, goal_id);
        if (goal_in_db != null) {
            return goal_in_db;
        }
        throw new EntityNotFoundException("Goal " + goal_id + " not found");
    }

    public Goal addGoal(String value, String description) {
        Goal to_add = new Goal(value, description);

        em.getTransaction().begin();
        em.persist(to_add);

        em.getTransaction().commit();
        System.out.println("Added " + to_add.getId());

        return to_add;
    }

    public void changeDescription(Goal g, String newDescription) {
        em.getTransaction().begin();

        Goal goalInDB = em.find(Goal.class, g.getId());
        if (goalInDB == null) {
            throw new EntityNotFoundException("Goal " + g.getId() + " not found");
        }

        g.setDescription(newDescription);
        em.getTransaction().commit();
    }

    public void changeValue(Goal g, String newValue) {
        em.getTransaction().begin();

        Goal goalInDB = em.find(Goal.class, g.getId());
        if (goalInDB == null) {
            throw new EntityNotFoundException("Goal " + g.getId() + " not found");
        }

        g.setValue(newValue);
        em.getTransaction().commit();
    }

    @Transactional
    public void deleteGoal(Goal goalToDelete) {
        Goal goalInDB = em.find(Goal.class, goalToDelete.getId());
        if (goalInDB == null) {
            throw new EntityNotFoundException("HabitCategory " + goalToDelete.getId() + " not found");
        }
        em.getTransaction().begin();
        em.remove(goalInDB);
        em.getTransaction().commit();
        System.out.println("Deleted " + goalToDelete.getId());
    }
}
