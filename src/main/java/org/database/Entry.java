package org.database;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EntryID", nullable = false)
    private Integer id;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "Value")
    private boolean value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HabitID")
    private Habit habit;

    public Integer getId() {return this.id;}
    public void setId(Integer id) {this.id = id;}

    public LocalDate getDate() {return this.date;}
    public void setDate(LocalDate date) {this.date = date;}

    public boolean isValue() {return this.value;}
    public void setValue(boolean value) {this.value = value;}

    public Habit getHabit() {return this.habit;}
    public void setHabit(Habit habit) {this.habit = habit;}

    public Entry() {}

    public Entry(Habit habit) {
        this.habit = habit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry u = (Entry) o;
        return Objects.equals(this.id, u.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(habit.getHabitName(), this.value, this.date, id);
    }

    @Override
    public String toString() {
        return ("[ Entry ID:" + this.id +
                "\n\tbelongs to: " + this.habit.getHabitName() +
                "\n\tdate:" + this.date +
                "\n\tvalue: " + this.value +
                "\n]");
    }
}
