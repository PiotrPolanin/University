package com.university.student;

import com.university.generics.GenericController;
import com.university.student.Student;
import com.university.generics.GenericService;
import com.university.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/students")
public class StudentController extends GenericController<Student> {

    private final StudentService service;

    @Autowired
    public StudentController(GenericService<Student> genericService, StudentService service) {
        super(genericService);
        this.service = service;
    }

    @GetMapping(value = "/byFirstName/{firstName}")
    public ResponseEntity<List<Student>> getByFirstName(@PathVariable(name = "firstName") String firstName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByFirstName(firstName));
    }

    @GetMapping(value = "/byLastName/{lastName}")
    public ResponseEntity<List<Student>> getByLastName(@PathVariable(name = "lastName") String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByLastName(lastName));
    }

    @GetMapping(value = "/byFullName")
    public ResponseEntity<List<Student>> getByFullName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByFullName(firstName, lastName));
    }

    @GetMapping(value = "/teacher/{id}")
    public ResponseEntity<List<Student>> getByTeacherId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByTeacherId(id));
    }

}
