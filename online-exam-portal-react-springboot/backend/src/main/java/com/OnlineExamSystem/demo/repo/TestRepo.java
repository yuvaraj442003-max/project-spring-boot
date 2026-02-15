package com.OnlineExamSystem.demo.repo;

import com.OnlineExamSystem.demo.model.Test;
import com.OnlineExamSystem.demo.model.TestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {

    List<Test> findByTestDetail(TestDetail testDetail);
}
