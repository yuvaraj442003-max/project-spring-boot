package com.OnlineExamSystem.demo.service;

import com.OnlineExamSystem.demo.dto.TestDetailResponse;
import com.OnlineExamSystem.demo.dto.TestRequest;
import com.OnlineExamSystem.demo.model.Mark;
import com.OnlineExamSystem.demo.model.Register;
import com.OnlineExamSystem.demo.model.Test;
import com.OnlineExamSystem.demo.model.TestDetail;
import com.OnlineExamSystem.demo.repo.MarkRepo;
import com.OnlineExamSystem.demo.repo.RegisterRepo;
import com.OnlineExamSystem.demo.repo.TestDetailRepo;
import com.OnlineExamSystem.demo.repo.TestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final RegisterRepo registerRepo;
    private final TestDetailRepo testDetailRepo;
    private final TestRepo testRepo;
    private final MarkRepo markRepo;

    // ========================================================================
    // CREATE TEST
    // ========================================================================
    public void saveTest(TestRequest request, Long teacherId) {

        Register teacher = registerRepo.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found: " + teacherId));

        TestDetail testDetail = new TestDetail();
        testDetail.setTestname(request.testname());
        testDetail.setTeacher(teacher);

        TestDetail savedTestDetail = testDetailRepo.save(testDetail);

        List<Test> questions = request.questions().stream()
                .map(q -> {
                    Test t = new Test();
                    t.setQuestion(q.question());
                    t.setOption1(q.option1());
                    t.setOption2(q.option2());
                    t.setOption3(q.option3());
                    t.setOption4(q.option4());
                    t.setAnswer(q.answer());
                    t.setLevel(q.level());
                    t.setTestDetail(savedTestDetail);
                    return t;
                })
                .collect(Collectors.toList());

        testRepo.saveAll(questions);
    }

    // ========================================================================
    // FETCH ALL TESTS FOR SPECIFIC TEACHER
    // ========================================================================
    public List<TestDetail> getAllTestsByTeacher(Long teacherId) {
        return testDetailRepo.findByTeacherId(teacherId);
    }

    // ========================================================================
    // FETCH TEST WITH QUESTIONS
    // ========================================================================
    public TestDetailResponse getTestWithQuestions(Long testId) {

        TestDetail testDetail = testDetailRepo.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test not found: " + testId));

        List<Test> questions = testRepo.findByTestDetail(testDetail);

        return new TestDetailResponse(
                testDetail.getId(),
                testDetail.getTestname(),
                questions
        );
    }

    // ========================================================================
    // UPDATE TEST + QUESTIONS
    // ========================================================================
    public void updateTest(Long id, TestDetailResponse dto) {

        TestDetail testDetail = testDetailRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Test not found: " + id));

        testDetail.setTestname(dto.testname());
        testDetailRepo.save(testDetail);

        if (dto.tests() == null || dto.tests().isEmpty()) {
            return;
        }

        List<Test> updatedTests = dto.tests().stream()
                .map(q -> {
                    Test existing = testRepo.findById(q.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Question not found: " + q.getId()));

                    existing.setQuestion(q.getQuestion());
                    existing.setOption1(q.getOption1());
                    existing.setOption2(q.getOption2());
                    existing.setOption3(q.getOption3());
                    existing.setOption4(q.getOption4());
                    existing.setAnswer(q.getAnswer());
                    existing.setLevel(q.getLevel());

                    return existing;
                })
                .collect(Collectors.toList());

        testRepo.saveAll(updatedTests);
    }

    // ========================================================================
    // DELETE TEST
    // ========================================================================
    public void deleteTest(Long id) {
        TestDetail testDetail = testDetailRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Test not found: " + id));

        testDetailRepo.delete(testDetail);
    }

    // ========================================================================
    // FETCH ALL TESTS
    // ========================================================================
    public List<TestDetail> getAllTests() {
        return testDetailRepo.findAll();
    }

    // ========================================================================
    // SUBMIT TEST RESULTS
    // ========================================================================
    public void submitTest(Long testId, Long userId, Mark request) {

        Register student = registerRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        TestDetail testDetail = testDetailRepo.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test not found: " + testId));

        Mark mark = new Mark();
        mark.setUserid(student);
        mark.setTestDetail(testDetail);
        mark.setMark(request.getMark());
        mark.setCheatingCount(request.getCheatingCount());
        mark.setDate(request.getDate());

        markRepo.save(mark);
    }
}
