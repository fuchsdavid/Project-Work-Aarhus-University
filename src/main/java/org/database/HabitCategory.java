package org.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class HabitCategory {

    @Id
    @Column(name = "Habit_Cat_Name", nullable = false)
    private String name;

    @Column(name = "Description", length = 1024)
    private String description;

    public HabitCategory() {};

    public HabitCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "[HabitCategory\n" + "\tname: " + name +
                "\n\tDescription: " + description + "]";
    }


}