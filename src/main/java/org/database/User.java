package org.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "UserName", nullable = false, length = 64)
    private String userName;

    @Column(name = "Name", nullable = false, length = 64)
    private String name;

    @Column(name = "Surname", nullable = false, length = 64)
    private String surname;

    @Column(name = "Age", nullable = false)
    private Integer age;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "ImageID", nullable = true)
    private String imageID;

    public User(){}

    public User(String userName, String name, String surname, Integer age, String email, String password) {
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.imageID = null;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageID(){
        return imageID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User u = (User) o;
        return (Objects.equals(userName, u.userName) || Objects.equals(email, u.email));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, userName, age, email, password);
    }

    @Override
    public String toString() {
        return ("[User: " + this.userName + "\n\tName:" + this.name + "\n\tSurname:" + this.surname + "\n\tAge:" + this.age
                + "\n\tEmail: " + email + "\n\tPassword: " + password + "]");
    }

}