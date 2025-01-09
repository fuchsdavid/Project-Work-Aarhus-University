package org.database;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HabitID", nullable = false)
    private Integer id;

    @Column(name = "HabitName", nullable = false, length = 64)
    private String habitName;

    @Column(name = "LongestStreak", nullable = false)
    private Integer longestStreak;

    @Column(name = "CurrentStreak", nullable = false)
    private Integer currentStreak;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserName")
    private User user;

    // Standard-Konstruktor
    public Habit() {}

    // Konstruktor mit habitName und Benutzer
    public Habit(String name, User user) {
        this.habitName = name;
        this.user = user;
        this.currentStreak = 0;
        this.longestStreak = 0;
    }

    // Neuer Konstruktor mit allen Werten
    public Habit(String name, int currentStreak, int longestStreak, User user) {
        this.habitName = name;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.user = user;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLongestStreak() {
        return this.longestStreak;
    }

    public void setLongestStreak(Integer longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Integer getCurrentStreak() {
        return this.currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHabitName() {
        return this.habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit u = (Habit) o;
        return (Objects.equals(habitName, u.habitName) && Objects.equals(user, u.user));
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitName, currentStreak, longestStreak, id);
    }

    @Override
    public String toString() {
        return ("[ Habit: " + this.habitName +
                "\n\tbelongs to: " + (this.user != null ? this.user.getUserName() : "No User") +
                "\n\tlongest streak: " + this.longestStreak +
                "\n\tcurrent streak: " + this.currentStreak +
                "\n\tid: " + this.id +
                "\n]");
    }
}
