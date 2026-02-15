package com.OnlineExamSystem.demo.controller;

import com.OnlineExamSystem.demo.dto.TestDetailResponse;
import com.OnlineExamSystem.demo.dto.TestRequest;
import com.OnlineExamSystem.demo.model.Mark;
import com.OnlineExamSystem.demo.model.TestDetail;
import com.OnlineExamSystem.demo.service.TestService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class TestController {


    private final TestService testService;
    // ==========================================================
    // CREATE TEST
    // ==========================================================
    @PostMapping
    public ResponseEntity<?> createTest(@RequestBody TestRequest request, HttpSession session) {

        Long teacherId = (Long) session.getAttribute("userId");

        if (teacherId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized. Please login.");
        }

        try {
            log.info("Creating test: {}", request);
            testService.saveTest(request, teacherId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Test created successfully.");
        } catch (Exception e) {
            log.error("Error while creating test", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create test: " + e.getMessage());
        }
    }

    // ==========================================================
    // GET ALL TESTS FOR TEACHER
    // ==========================================================
    @GetMapping("/teacher")
    public ResponseEntity<?> getTestsForTeacher(HttpSession session) {

        Long teacherId = (Long) session.getAttribute("userId");

        if (teacherId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized. Please login.");
        }

        List<TestDetail> tests = testService.getAllTestsByTeacher(teacherId);
        return ResponseEntity.ok(tests);
    }

    // ==========================================================
    // GET TEST WITH QUESTIONS
    // ==========================================================
    @GetMapping("/{id}")
    public ResponseEntity<TestDetailResponse> getTestById(@PathVariable Long id) {
        TestDetailResponse response = testService.getTestWithQuestions(id);
        return ResponseEntity.ok(response);
    }

    // ==========================================================
    // UPDATE TEST + QUESTIONS
    // ==========================================================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTest(
            @PathVariable Long id,
            @RequestBody TestDetailResponse request) {

        log.info("Updating test {} with data: {}", id, request);

        testService.updateTest(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // ==========================================================
    // DELETE TEST
    // ==========================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.ok("Test deleted successfully.");
    }

    // ==========================================================
    // GET ALL TESTS (FOR STUDENT)
    // ==========================================================
    @GetMapping
    public ResponseEntity<List<TestDetail>> getAllTests() {
        return ResponseEntity.ok(testService.getAllTests());
    }

    // ==========================================================
    // SUBMIT TEST RESULT
    // ==========================================================
    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitTest(
            @PathVariable Long id,
            @RequestBody Mark mark,
            HttpSession session) {

        Long studentId = (Long) session.getAttribute("userId");

        if (studentId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Unauthorized. Please login.");
        }

        testService.submitTest(id, studentId, mark);
        return ResponseEntity.ok("Test submitted successfully.");
    }
}
