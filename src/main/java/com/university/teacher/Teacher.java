package com.university.teacher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.generics.Person;
import com.university.generics.UpdateEntity;
import com.university.student.Student;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Teacher")
@Table(name = "teachers")
public class Teacher extends Person implements UpdateEntity<Teacher> {

    @NotNull(message = "Subject cannot be null")
    @Column(nullable = false)
    private String subject;
    @JsonIgnore
    @ManyToMany(mappedBy = "teachers")
    private Set<Student> students = new HashSet<>();

    public Teacher(String firstName, String lastName, int age, String email, String subject) {
        super(firstName, lastName, age, email);
        this.subject = subject;
    }

    public Teacher() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<Student> getStudents() {
        return students.stream().collect(Collectors.toSet());
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Student first name: " + firstName + "; last name: " + lastName + "; age: " + age + "; email: " + email + "; subject: " + subject;
    }

    @Override
    public void update(Teacher updated) {
        this.firstName = updated.getFirstName();
        this.lastName = updated.getLastName();
        this.age = updated.getAge();
        this.email = updated.getEmail();
        this.subject = updated.getSubject();
    }
}
