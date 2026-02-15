package com.OnlineExamSystem.demo.dto;

import com.OnlineExamSystem.demo.model.Test;
import java.util.List;

public record TestDetailResponse(
        Long testId,
        String testname,
        List<Test> tests
) {}
