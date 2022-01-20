package com.university.shared;

import com.university.student.Student;
import com.university.student.StudentService;
import com.university.teacher.Teacher;
import com.university.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentTeacherService {

    private final StudentService studentService;
    private final TeacherService teacherService;

    @Autowired
    public StudentTeacherService(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @Transactional
    public void join(Long teacherId, Long studentId) {
        Teacher teacher = teacherService.getById(teacherId);
        Student student = studentService.getById(studentId);
        student.addTeacher(teacher);
        studentService.save(student);
    }
}
