package org.database;

import javax.persistence.*;

@Entity
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Habit_ID", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 64)
    private String name;

    @Column(name = "Description", nullable = false, length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Habit_Cat_Name")
    private HabitCategory habitCat;


    public Habit() {};

    public Habit(String name, String description, HabitCategory habitCat) {
        setName(name);
        setDescription(description);
        setHabitCat(habitCat);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HabitCategory getHabitCat() {
        return habitCat;
    }

    public void setHabitCat(HabitCategory habitCat) {
        this.habitCat = habitCat;
    }

    @Override
    public String toString() {
        return "[HABIT " + name + " id:" + id +
                "]\n" + "\tdescription: " + description + "\n\t" + habitCat.getName();
    }

}