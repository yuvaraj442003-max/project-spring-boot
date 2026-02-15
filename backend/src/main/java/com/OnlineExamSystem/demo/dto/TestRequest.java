package com.OnlineExamSystem.demo.dto;

import java.util.List;

public record TestRequest(
        String testname,
        List<QuestionRequest> questions
) {}
