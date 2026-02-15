package com.OnlineExamSystem.demo.controller;


import com.OnlineExamSystem.demo.dto.MarkStatusDTO;

import com.OnlineExamSystem.demo.service.MarkService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/mark")
public class MarkController {

    @Autowired
    private MarkService markService;

    @GetMapping("/status/{id}")
    public ResponseEntity<MarkStatusDTO> markStatus(@PathVariable Long id, HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            MarkStatusDTO markStatusDTO = markService.reportByTestId(id, userId);
            System.out.println(markStatusDTO);
            return new ResponseEntity<>(markStatusDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/student")
    public ResponseEntity<List<Map<String, Object>>> studentAllMarks(HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            List<Map<String, Object>> marks = markService.studentAllMarks(userId);
            return new ResponseEntity<>(marks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/teacherReport")
    public ResponseEntity<List<Map<String, Object>>> teacherReport(HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId") ;
            System.out.println("User ID from session: " + userId);
            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            List<Map<String, Object>> studentMarks = markService.teacherReport(userId);
            System.out.println("Test : " + studentMarks);
            return new ResponseEntity<>(studentMarks, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
