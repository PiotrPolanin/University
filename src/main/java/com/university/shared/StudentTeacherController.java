package com.university.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public final class StudentTeacherController {

    private final StudentTeacherService service;

    @Autowired
    public StudentTeacherController(StudentTeacherService service) {
        this.service = service;
    }

    @PostMapping(value = "/students/{studentId}/teacher/{teacherId}")
    public ResponseEntity<String> joinTeacher(@PathVariable(name = "studentId") Long studentId, @PathVariable(name = "teacherId") Long teacherId) {
        service.join(teacherId, studentId);
        return ResponseEntity.status(HttpStatus.OK).body("Teacher with id " + teacherId + " and student with id " + studentId + " joined group");
    }

    @PostMapping(value = "/teachers/{teacherId}/student/{studentId}")
    public ResponseEntity<String> joinStudent(@PathVariable(name = "teacherId") Long teacherId, @PathVariable(name = "studentId") Long studentId) {
        service.join(teacherId, studentId);
        return ResponseEntity.status(HttpStatus.OK).body("Teacher with id " + teacherId + " and student with id " + studentId + " joined group");
    }
}
