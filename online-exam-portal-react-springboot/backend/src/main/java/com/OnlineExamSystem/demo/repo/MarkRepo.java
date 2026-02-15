package com.OnlineExamSystem.demo.repo;

import com.OnlineExamSystem.demo.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkRepo extends JpaRepository<Mark, Long> {
    @Query("SELECT m FROM Mark m WHERE m.userid.id = :userId AND m.testDetail.id = :testId")
    List<Mark> findByUserIdAndTestId(@Param("userId") Long userId, @Param("testId") Long testId);

    @Query("SELECT m.mark, td.testname, COUNT(t.id), m.date " +
            "FROM Mark m " +
            "JOIN TestDetail td ON m.testDetail.id = td.id " +
            "JOIN Test t ON t.testDetail.id = td.id " +
            "WHERE m.userid.id = :userId " +
            "GROUP BY m.mark, td.testname, m.date " +
            "ORDER BY m.date DESC")
    List<Object[]> findMarkStatusByUserIdWithTestDetails(@Param("userId") Long userId);


    @Query("SELECT m.mark, s.name, td.testname, COUNT(t.id), m.date ,m.cheatingCount " +
            "FROM Mark m " +
            "JOIN TestDetail td ON m.testDetail.id = td.id " +
            "JOIN Test t ON t.testDetail.id = td.id " +
            "JOIN Register s ON m.userid.id = s.id " +
            "WHERE td.teacher.id = :teacherId " +
            "GROUP BY m.mark, td.testname, m.date, s.name ,td.id , m.cheatingCount " +
            "ORDER BY m.date DESC ")
    List<Object[]> findMarkByTeacherId(@Param("teacherId") Long teacherId);


}
