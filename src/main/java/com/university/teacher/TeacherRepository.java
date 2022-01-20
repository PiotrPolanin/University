package com.university.teacher;

import com.university.generics.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends GenericRepository<Teacher> {

    @Query("SELECT t FROM Teacher t WHERE t.firstName = :firstName")
    List<Teacher> findByFirstName(@Param("firstName") String firstName);

    @Query("SELECT t FROM Teacher t WHERE t.lastName = :lastName")
    List<Teacher> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT t FROM Teacher t WHERE t.firstName = :firstName AND t.lastName = :lastName")
    List<Teacher> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

//    @Query(nativeQuery = true, value = "SELECT * FROM teachers t INNER JOIN students_teachers st ON st.teacher_id = t.id WHERE st.student_id = :studentId")
    @Query("SELECT t FROM Teacher t INNER JOIN t.students s WHERE s.id = :studentId")
    List<Teacher> findByStudentId(@Param("studentId") Long studentId);

}
