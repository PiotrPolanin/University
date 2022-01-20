package com.university.teacher;

import com.university.generics.GenericController;
import com.university.teacher.Teacher;
import com.university.generics.GenericService;
import com.university.teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/teachers")
public class TeacherController extends GenericController<Teacher> {

    private final TeacherService service;

    @Autowired
    public TeacherController(GenericService<Teacher> genericService, TeacherService service) {
        super(genericService);
        this.service = service;
    }

    @GetMapping(value = "/byFirstName/{firstName}")
    public ResponseEntity<List<Teacher>> getByFirstName(@PathVariable(name = "firstName") String firstName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByFirstName(firstName));
    }

    @GetMapping(value = "/byLastName/{lastName}")
    public ResponseEntity<List<Teacher>> getByLastName(@PathVariable(name = "lastName") String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByLastName(lastName));
    }

    @GetMapping(value = "/byFullName")
    public ResponseEntity<List<Teacher>> getByFullName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByFullName(firstName, lastName));
    }

    @GetMapping(value = "/student/{id}")
    public ResponseEntity<List<Teacher>> getByStudentId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByStudentId(id));
    }

}
