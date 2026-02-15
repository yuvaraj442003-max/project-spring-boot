package com.OnlineExamSystem.demo.service;


import com.OnlineExamSystem.demo.dto.MarkStatusDTO;
import com.OnlineExamSystem.demo.model.Mark;
import com.OnlineExamSystem.demo.model.Register;
import com.OnlineExamSystem.demo.model.TestDetail;

import com.OnlineExamSystem.demo.repo.MarkRepo;
import com.OnlineExamSystem.demo.repo.RegisterRepo;
import com.OnlineExamSystem.demo.repo.TestDetailRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepo markRepo;

    private final TestDetailRepo testDetailRepo;


    private final RegisterRepo registerRepo;

    public MarkStatusDTO reportByTestId(Long id, Long userId) {
        Register register = registerRepo.findById(userId).orElseThrow(() -> new RuntimeException("UserID Not Found"));
        TestDetail testDetail = testDetailRepo.findById(id).orElseThrow(() -> new RuntimeException("TestDetail Not Found"));

        List<Mark> marks = markRepo.findByUserIdAndTestId(register.getId(), testDetail.getId());
        return new MarkStatusDTO(testDetail.getTestname(), marks);

    }

    public List<Map<String, Object>> studentAllMarks(Long userId) {
        List<Object[]> results = markRepo.findMarkStatusByUserIdWithTestDetails(userId);
        List<Map<String, Object>> formattedResults = new ArrayList<>();

        for (Object[] row : results) {
            Integer mark = (Integer) row[0];  // Assuming mark is an Integer
            String testName = (String) row[1];
            Long totalQuestions = (Long) row[2];
            LocalDateTime date = (LocalDateTime) row[3];

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("mark", mark);
            resultMap.put("testName", testName);
            resultMap.put("totalQuestions", totalQuestions);
            resultMap.put("Date", date);


            formattedResults.add(resultMap);
        }

        return formattedResults;
    }

    public List<Map<String, Object>> teacherReport(Long userId) {
        List<Object[]> results = markRepo.findMarkByTeacherId(userId);
        List<Map<String, Object>> formattedResults = new ArrayList<>();
        for (Object[] row : results) {
            Integer mark = (Integer) row[0];
            String studentName = (String) row[1];
            String testName = (String) row[2];
            Long totalQuestions = (Long) row[3];
            LocalDateTime date = (LocalDateTime) row[4];
            Integer cheationgCount = (Integer) row[5];

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("mark", mark);
            resultMap.put("studentName", studentName);
            resultMap.put("testName", testName);
            resultMap.put("totalQuestions", totalQuestions);
            resultMap.put("Date", date);
            resultMap.put("cheatingCount", cheationgCount);

            formattedResults.add(resultMap);
        }
        return formattedResults;
    }


}
