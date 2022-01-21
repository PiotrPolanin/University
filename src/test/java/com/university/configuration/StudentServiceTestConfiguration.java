package com.university.configuration;

import com.university.generics.GenericRepository;
import com.university.student.Student;
import com.university.student.StudentRepository;
import com.university.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.university.student")
public class StudentServiceTestConfiguration {

    @Autowired
    private GenericRepository<Student> studentGenericRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Bean
    public StudentService studentService() {
        return new StudentService(studentGenericRepository, studentRepository);
    }

}


