package org.database;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Journal_ID", nullable = false)
    private Integer id;

    @Column(name = "Current_Streak", nullable = false)
    private Integer currentStreak;

    @Column(name = "Start_Date", nullable = false)
    private LocalDate startDate;

    @Column(name = "End_Date")
    private LocalDate endDate;

    @Column(name = "Activity_Type", nullable = false, length = 32)
    private String activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Habit_ID")
    private Habit habit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserName", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Goal_ID", nullable = false)
    private Goal goal;

    public Journal() {};

    //CurrentStreak, StartDate, (String) ActivityType, Habit, User, Goal
    public Journal(Habit habit, Goal goal, User user, String activityType) {
        this.currentStreak = 0;
        this.habit = habit;
        this.goal = goal;
        this.user = user;
        this.activityType = activityType;
        this.startDate = LocalDate.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {

        this.goal = goal;
    }

    @Override
    public String toString() {
        return ("[JOURNAL FOR " + user.getUserName() + "]\n" +
                "\tcurrentStreak: " + currentStreak + "\n\tstartDate: " + startDate + "\n\tendDate: "
                + endDate + "\n\tactivity type: " + activityType + "\n\thabit: " + habit.getName());
    }

}

