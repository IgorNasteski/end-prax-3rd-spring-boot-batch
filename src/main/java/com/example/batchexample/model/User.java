package com.example.batchexample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private int salary;

    //constructor
    public User(){}


    //BUILDER PATTERN
    public static class UserBuilder{
        //fields
        private int id;
        private String name;
        private String email;
        private int salary;

        //constructor
        public UserBuilder(){}
        public static UserBuilder anUser(){
            return new UserBuilder();
        }

        //with
        public UserBuilder withId(int id){
            this.id = id;
            return this;
        }
        public UserBuilder withName(String name){
            this.name = name;
            return this;
        }
        public UserBuilder withEmail(String email){
            this.email = email;
            return this;
        }
        public UserBuilder withSalary(int salary){
            this.salary = salary;
            return this;
        }

        //constructor "build" of main class User
        public User build(){
            User user = new User();
                user.id = this.id;
                user.name = this.name;
                user.email = this.email;
                user.salary = this.salary;
            return user;
        }
    }


    //hashcode and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (salary != user.salary) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + salary;
        return result;
    }

    //setters and getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
