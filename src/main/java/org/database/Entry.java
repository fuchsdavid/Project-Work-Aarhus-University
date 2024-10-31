package org.database;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Entry_ID", nullable = false)
    private Integer id;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "Value", nullable = false, length = 32)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Journal_ID")
    private Journal journal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public Entry() {};

    public Entry(String value, Journal journal) {
        this.date = LocalDate.now();
        this.value = value;
        this.journal = journal;
    }

    @Override
    public String toString() {
        return "[Entry ID: " + id +
                "\n\tDate: " + date + "\n\tValue: " + value + "\n\tJournal: " + journal.getId() + "]";
    }

}