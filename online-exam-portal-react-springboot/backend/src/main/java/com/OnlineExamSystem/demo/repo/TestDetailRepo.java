package com.OnlineExamSystem.demo.repo;

import com.OnlineExamSystem.demo.model.TestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDetailRepo extends JpaRepository<TestDetail, Long> {


    @Query("SELECT t FROM TestDetail t WHERE t.teacher.id = :teacherId")
    List<TestDetail> findByTeacherId(@Param("teacherId") Long teacherId);

}
