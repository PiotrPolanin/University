package com.university.teacher;

import com.university.configuration.PersistenceTestConfiguration;
import com.university.configuration.TeacherServiceTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {PersistenceTestConfiguration.class, TeacherServiceTestConfiguration.class})
@ActiveProfiles("dev")
public class TeacherServiceTest {

    @Autowired
    private TeacherService service;

    @Test
    public void shouldServiceReturnNotEmptyTeacherListWhenFirstNameExists() {
        //Given
        String uCaseFirstName = "ANN";
        String lCaseFirstName = "ann";
        //When
        List<Teacher> resultsForUCaseFirstName = service.getByFirstName(uCaseFirstName);
        List<Teacher> resultsForLCaseFirstName = service.getByFirstName(lCaseFirstName);
        //Then
        assertEquals(2, resultsForUCaseFirstName.size());
        assertEquals(2, resultsForLCaseFirstName.size());
    }

    @Test
    public void shouldServiceReturnNotEmptyTeacherListWhenLastNameExists() {
        //Given
        String uCaseFirstName = "GREY";
        String lCaseFirstName = "grey";
        //When
        List<Teacher> resultsForUCaseFirstName = service.getByLastName(uCaseFirstName);
        List<Teacher> resultsForLCaseFirstName = service.getByLastName(lCaseFirstName);
        //Then
        assertEquals(2, resultsForUCaseFirstName.size());
        assertEquals(2, resultsForLCaseFirstName.size());
    }

    @Test
    public void shouldServiceReturnNotEmptyListWhenTeacherFirstAndLastNameExist() {
        //Given
        String firstName = "MARY";
        String lastName = "MCDONALD";
        //When
        List<Teacher> results = service.getByFullName(firstName, lastName);
        //Then
        assertEquals(1, results.size());
    }

    @Test
    public void shouldServiceReturnTeachersByStudentId() {
        //Given
        Long studentId_1 = 1L;
        Long studentId_2 = 2L;
        Long studentId_3 = 3L;
        Long studentId_4 = 4L;
        Long studentId_5 = 5L;
        Long studentId_6 = 6L;
        //When
        List<Teacher> teachersByStudentId_1 = service.getByStudentId(studentId_1);
        List<Teacher> teachersByStudentId_2 = service.getByStudentId(studentId_2);
        List<Teacher> teachersByStudentId_3 = service.getByStudentId(studentId_3);
        List<Teacher> teachersByStudentId_4 = service.getByStudentId(studentId_4);
        List<Teacher> teachersByStudentId_5 = service.getByStudentId(studentId_5);
        List<Teacher> teachersByStudentId_6 = service.getByStudentId(studentId_6);
        //Then
        assertEquals(5, teachersByStudentId_1.size());
        teachersByStudentId_1.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(1L, 2L, 3L, 4L, 6L));
        assertEquals(3, teachersByStudentId_2.size());
        teachersByStudentId_2.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(4L, 5L, 6L));
        assertEquals(3, teachersByStudentId_3.size());
        teachersByStudentId_3.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(2L, 3L, 6L));
        assertEquals(3, teachersByStudentId_4.size());
        teachersByStudentId_4.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(1L, 5L, 6L));
        assertEquals(3, teachersByStudentId_5.size());
        teachersByStudentId_5.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(4L, 5L, 6L));
        assertEquals(6, teachersByStudentId_6.size());
        teachersByStudentId_6.stream().map(s -> s.getId()).collect(Collectors.toList()).containsAll(List.of(1L, 2L, 3L, 4L, 5L, 6L));
    }

    //More tests should be done

}
