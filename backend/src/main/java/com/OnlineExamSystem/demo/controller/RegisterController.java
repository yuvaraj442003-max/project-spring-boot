package com.OnlineExamSystem.demo.controller;

import com.OnlineExamSystem.demo.model.Register;
import com.OnlineExamSystem.demo.service.RegisterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @GetMapping("/test")
    public String testing() {
        return "Working Gopinath !!";
    }

    @PostMapping("/Signup")
    public ResponseEntity<?> userSignup(@RequestPart Register register, @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        System.out.println("Gopinath :" + register);
        try {
            Register register1 = registerService.userSignup(register, imageFile);
            return new ResponseEntity<>(register1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> userLogin(@RequestBody Register register, HttpSession session) {
        try {
            Map<String, Object> response = registerService.userLogin(register, session);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logoutUser(HttpSession session) {
        session.invalidate();
        Map<String, Object> response = new HashMap<>();
        response.put("logout", "Logout Successfull");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> userProfile(HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
            }
            Register register = registerService.userProfile(userId);
            if (register == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(register, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getUserImageById(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Register profileImage = registerService.userProfile(userId);
        byte[] imageFile = profileImage.getImage();
        String contentType = detectImageType(imageFile);
        return ResponseEntity.ok().contentType(MediaType.valueOf(contentType)).body(imageFile);
    }

    private String detectImageType(byte[] imageBytes) {
        if (imageBytes.length > 3 && imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8) {
            return "image/jpeg";
        } else if (imageBytes.length > 4 && imageBytes[0] == (byte) 0x89 && imageBytes[1] == (byte) 0x50) {
            return "image/png";
        } else if (imageBytes.length > 2 && imageBytes[0] == (byte) 0x47 && imageBytes[1] == (byte) 0x49) {
            return "image/gif";
        } else {
            return "application/octet-stream";
        }
    }
}