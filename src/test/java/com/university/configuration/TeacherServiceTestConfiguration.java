package com.university.configuration;

import com.university.generics.GenericRepository;
import com.university.teacher.Teacher;
import com.university.teacher.TeacherRepository;
import com.university.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.university.teacher")
public class TeacherServiceTestConfiguration {

    @Autowired
    private GenericRepository<Teacher> teacherGenericRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Bean
    public TeacherService teacherService() {
        return new TeacherService(teacherGenericRepository, teacherRepository);
    }

}


