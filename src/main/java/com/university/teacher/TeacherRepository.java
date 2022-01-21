package com.university.teacher;

import com.university.generics.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends GenericRepository<Teacher> {

    @Query("SELECT t FROM Teacher t WHERE lower(t.firstName) = lower(:firstName)")
    List<Teacher> findByFirstName(@Param("firstName") String firstName);

    @Query("SELECT t FROM Teacher t WHERE lower(t.lastName) = lower(:lastName)")
    List<Teacher> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT t FROM Teacher t WHERE lower(t.firstName) = lower(:firstName) AND lower(t.lastName) = lower(:lastName)")
    List<Teacher> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

//    @Query(nativeQuery = true, value = "SELECT * FROM teachers t INNER JOIN students_teachers st ON st.teacher_id = t.id WHERE st.student_id = :studentId")
    @Query("SELECT t FROM Teacher t INNER JOIN t.students s WHERE s.id = :studentId")
    List<Teacher> findByStudentId(@Param("studentId") Long studentId);

}
