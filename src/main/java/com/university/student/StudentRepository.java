package com.university.student;

import com.university.generics.GenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends GenericRepository<Student> {

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName")
    List<Student> findByFirstName(@Param("firstName") String firstName);

    @Query("SELECT s FROM Student s WHERE s.lastName = :lastName")
    List<Student> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT s FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName")
    List<Student> findByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

//    @Query(nativeQuery = true, value = "SELECT * FROM students s INNER JOIN students_teachers st ON st.student_id = s.id WHERE st.teacher_id = :teacherId")
    @Query("SELECT s FROM Student s INNER JOIN s.teachers t WHERE t.id = :teacherId")
    List<Student> findByTeacherId(@Param("teacherId") Long teacherId);

}
