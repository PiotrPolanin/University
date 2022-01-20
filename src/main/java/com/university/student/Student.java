package com.university.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.generics.Person;
import com.university.teacher.Teacher;
import com.university.generics.UpdateEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Student")
@Table(name = "students")
public class
Student extends Person implements UpdateEntity<Student> {

    @NotNull(message = "Study field cannot be null")
    @Column(nullable = false)
    private String studyField;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "students_teachers",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "teacher_id"),
            foreignKey = @ForeignKey(name = "fk_student_teacher"), inverseForeignKey = @ForeignKey(name = "fk_teacher_student"))
    private Set<Teacher> teachers = new HashSet<>();

    public Student(String firstName, String lastName, int age, String email, String studyField) {
        super(firstName, lastName, age, email);
        this.studyField = studyField;
    }

    public Student() {
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public String getStudyField() {
        return studyField;
    }

    public void setStudyField(String studyField) {
        this.studyField = studyField;
    }

    public Set<Teacher> getTeachers() {
        return teachers.stream().collect(Collectors.toSet());
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Student first name: " + firstName + "; last name: " + lastName + "; age: " + age + "; email: " + email + "; study field: " + studyField;
    }

    @Override
    public void update(Student updated) {
        this.firstName = updated.getFirstName();
        this.lastName = updated.getLastName();
        this.age = updated.getAge();
        this.email = updated.getEmail();
        this.studyField = updated.getStudyField();
    }
}
