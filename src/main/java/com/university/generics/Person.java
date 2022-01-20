package com.university.generics;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@MappedSuperclass
public abstract class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "At least two characters are required for first name")
    @Column(nullable = false)
    protected String firstName;
    //I added the extra constraint for last name (out of requirements)
    @NotNull(message = "Last name cannot be null")
    @Size(min = 1, message = "At least one character is required for last name")
    @Column(nullable = false)
    protected String lastName;
    @Min(value = 18, message = "Age must be above 18")
    protected int age;
    @NotNull(message = "Email cannot be null")
    @Email(regexp = ".+@[a-z0-9]+\\.[a-z]+", message = "Not valid email format")
    protected String email;

    public Person(String firstName, String lastName, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
