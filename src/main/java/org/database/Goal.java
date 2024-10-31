package org.database;

import javax.persistence.*;


@Entity
public class Goal {
    @Id
    @Column(name = "Goal_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Description", length = 1024)
    private String description;

    @Column(name = "Value", nullable = false, length = 32)
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Goal() {}

    public Goal(String description, String value) {
        this.description = description;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[Goal ID: " + this.id.toString() + "\n\tdescription:\n\t" + this.description + "\n\tvalue: " + this.value + "]";
    }

}