package com.university.student;

import com.university.configuration.PersistenceTestConfiguration;
import com.university.configuration.StudentServiceTestConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {PersistenceTestConfiguration.class, StudentServiceTestConfiguration.class})
@ActiveProfiles("dev")
public class StudentServiceTest {

    @Autowired
    private StudentService service;
    private static final List<Student> students = new ArrayList<>();
    private static Validator validator;

    @BeforeAll()
    public static void storeStudents() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        long cnt = 0;
        students.add(new Student("Ann", "Smith", 18, "a.smith@gmail.com", "Economics"));
        students.add(new Student("Mark", "Washington", 19, "mark_washington@yahoo.com", "Engineering"));
        students.add(new Student("Josh", "Bowl", 22, "joshb@gmail.com", "Statistics"));
        students.add(new Student("Cathy", "Summer", 21, "csummer@yahoo.com", "Pharmacy"));
        students.add(new Student("Mark", "Stuart", 18, "mark.stuard@yahoo.com", "Anthropology"));
        students.add(new Student("Lisa", "Smith", 18, "smithl@gmail.com", "Chemistry"));
        for (Student s : students) {
            s.setId(++cnt);
        }
    }

    @Test
    public void shouldServiceGetStudentWhenIdExists() {
        //Given
        Student refAnnaSmith = students.get(0);
        //When
        Student dbAnnSmith = service.getById(refAnnaSmith.getId());
        //Then
        assertEquals(refAnnaSmith.getFirstName(), dbAnnSmith.getFirstName());
        assertEquals(refAnnaSmith.getLastName(), dbAnnSmith.getLastName());
        assertEquals(refAnnaSmith.getAge(), dbAnnSmith.getAge());
        assertEquals(refAnnaSmith.getEmail(), dbAnnSmith.getEmail());
        assertEquals(refAnnaSmith.getStudyField(), dbAnnSmith.getStudyField());
    }

    @Test
    public void shouldServiceThrowEntityNotFoundExceptionWhenIdNotExists() {
        assertThrows(EntityNotFoundException.class, () -> service.getById(10L));
    }

    @Test
    public void shouldServiceThrowConstraintViolationExceptionWhenStudentFieldsAreNullAndAgeUnder18() {
        //Given
        Student invalidStudent = new Student(null, null, 14, null, null);
        Set<ConstraintViolation<Student>> violations = validator.validate(invalidStudent);
        String errorMessage = violations.stream().map(cv -> cv.getMessage()).collect(Collectors.joining(";"));
        //Then
        assertEquals(violations.size(), 5);
        assertTrue(errorMessage.contains("First name cannot be null"));
        assertTrue(errorMessage.contains("Last name cannot be null"));
        assertTrue(errorMessage.contains("Age must be above 18"));
        assertTrue(errorMessage.contains("Email cannot be null"));
        assertTrue(errorMessage.contains("Study field cannot be null"));
        assertThrows(ConstraintViolationException.class, () -> service.save(invalidStudent));
    }

    @Test
    public void shouldServiceThrowConstraintViolationExceptionWhenStudentFieldsAreInvalid() {
        //Given
        Student invalidStudent = new Student("A", "", 13, "", "");
        //Then
        Set<ConstraintViolation<Student>> violations = validator.validate(invalidStudent);
        String errorMessage = violations.stream().map(cv -> cv.getMessage()).collect(Collectors.joining(";"));
        assertEquals(violations.size(), 4);
        assertTrue(errorMessage.contains("At least two characters are required for first name"));
        assertTrue(errorMessage.contains("At least one character is required for last name"));
        assertTrue(errorMessage.contains("Age must be above 18"));
        assertTrue(errorMessage.contains("Not valid email format"));
        assertThrows(ConstraintViolationException.class, () -> service.save(invalidStudent));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldServiceSaveStudentWhenFieldsAreValid() {
        //Given
        Student student = new Student("Ann", "Forest", 29, "ann.forest@gmail.com", "Chemistry");
        //When
        service.save(student);
        //Then
        List<Student> dbStudents = service.getAll(0, 10, "id", "ASC");
        assertEquals(7, dbStudents.size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldServiceUpdateStudentWhenFieldsAreValid() {
        //Given
        Student dStudent = students.get(5);
        dStudent.setAge(20);
        dStudent.setStudyField("Mechatronic");
        //When
        Student uStudent = service.update(dStudent.getId(), dStudent);
        //Then
        assertEquals(dStudent.getId(), uStudent.getId());
        assertEquals(dStudent.getFirstName(), uStudent.getFirstName());
        assertEquals(dStudent.getLastName(), uStudent.getLastName());
        assertEquals(dStudent.getAge(), uStudent.getAge());
        assertEquals(dStudent.getEmail(), uStudent.getEmail());
        assertEquals(dStudent.getStudyField(), uStudent.getStudyField());
    }

    @Test
    @Disabled("Should be checked in next iteration")
    public void shouldServiceThrowConstraintViolationExceptionWhenUpdatedFieldsAreNullOrInvalid() {
        //Given
        Long id = 6L;
        Student student = new Student(null, null, 12, null, null);
        //Then
        assertThrows(ConstraintViolationException.class, () -> service.update(id, student));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldServiceRemoveStudentWhenIdExists() {
        //Given
        Long id = 3L;
        //When
        service.removeById(id);
        //Then
        List<Student> dbStudents = service.getAll(0, 10, "id", "asc");
        assertFalse(dbStudents.stream().map(s -> s.getId()).collect(Collectors.toList()).contains(id));
        assertEquals(5, dbStudents.size());
    }

    @Test
    public void shouldServiceReturnEmptyStudentListWhenFirstNameIsEmptyOrNullOrIncorrect() {
        //Given
        String emptyFirstName = "";
        String incorrectFirstName = ".Ann";
        //When
        List<Student> resultsForEmptyFirstName = service.getByFirstName(emptyFirstName);
        List<Student> resultsForNull = service.getByFirstName(null);
        List<Student> resultsForIncorrectFirstName = service.getByFirstName(incorrectFirstName);
        //Then
        assertEquals(0, resultsForEmptyFirstName.size());
        assertEquals(0, resultsForNull.size());
        assertEquals(0, resultsForIncorrectFirstName.size());
    }

    @Test
    public void shouldServiceReturnNotEmptyStudentListWhenFirstNameExists() {
        //Given
        String uCaseFirstName = "ANN";
        String lCaseFirstName = "ann";
        //When
        List<Student> resultsForUCaseFirstName = service.getByFirstName(uCaseFirstName);
        List<Student> resultsForLCaseFirstName = service.getByFirstName(lCaseFirstName);
        //Then
        assertEquals(1, resultsForUCaseFirstName.size());
        assertEquals(1, resultsForLCaseFirstName.size());
    }

    @Test
    public void shouldServiceReturnNotEmptyStudentListWhenLastNameExists() {
        //Given
        String uCaseFirstName = "SMITH";
        String lCaseFirstName = "smith";
        //When
        List<Student> resultsForUCaseFirstName = service.getByLastName(uCaseFirstName);
        List<Student> resultsForLCaseFirstName = service.getByLastName(lCaseFirstName);
        //Then
        assertEquals(2, resultsForUCaseFirstName.size());
        assertEquals(2, resultsForLCaseFirstName.size());
    }

    @Test
    public void shouldServiceReturnNotEmptyListWhenStudentFirstAndLastNameExist() {
        //Given
        String firstName = "CATHY";
        String lastName = "SUMMER";
        //When
        List<Student> results = service.getByFullName(firstName, lastName);
        //Then
        assertEquals(1, results.size());
    }

    @Test
    public void shouldServiceReturnStudentsGroupByTeacherId() {
        //Given
        Long teacherId_1 = 1L;
        Long teacherId_2 = 2L;
        Long teacherId_3 = 3L;
        Long teacherId_4 = 4L;
        Long teacherId_5 = 5L;
        Long teacherId_6 = 6L;
        //When
        List<Student> studentsByTeacherId_1 = service.getByTeacherId(teacherId_1);
        List<Student> studentsByTeacherId_2 = service.getByTeacherId(teacherId_2);
        List<Student> studentsByTeacherId_3 = service.getByTeacherId(teacherId_3);
        List<Student> studentsByTeacherId_4 = service.getByTeacherId(teacherId_4);
        List<Student> studentsByTeacherId_5 = service.getByTeacherId(teacherId_5);
        List<Student> studentsByTeacherId_6 = service.getByTeacherId(teacherId_6);
        //Then
        assertEquals(3, studentsByTeacherId_1.size());
        studentsByTeacherId_1.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(1L, 4L, 6L));
        assertEquals(3, studentsByTeacherId_2.size());
        studentsByTeacherId_2.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(1L, 3L, 6L));
        assertEquals(3, studentsByTeacherId_3.size());
        studentsByTeacherId_3.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(1L, 3L, 6L));
        assertEquals(4, studentsByTeacherId_4.size());
        studentsByTeacherId_4.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(1L, 2L, 5L, 6L));
        assertEquals(4, studentsByTeacherId_5.size());
        studentsByTeacherId_5.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(2L, 4L, 5L, 6L));
        assertEquals(6, studentsByTeacherId_6.size());
        studentsByTeacherId_6.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(1L, 2L, 3L, 4L, 5L, 6L));
    }

    //More tests should be done

}
