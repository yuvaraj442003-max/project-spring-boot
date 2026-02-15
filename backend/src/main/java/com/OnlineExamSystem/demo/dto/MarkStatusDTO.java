package com.OnlineExamSystem.demo.dto;

import com.OnlineExamSystem.demo.model.Mark;
import java.util.List;

public record MarkStatusDTO(
        String testName,
        List<Mark> marks
) {}
